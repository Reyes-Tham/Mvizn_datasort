package application;

import java.io.File;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;

import javafx.util.Callback;

public class TreeViewUtils {
	public interface ImageDisplay {
	    void loadAndDisplayImage(File imageFile);
	    
	    void setImageList(File[] imageFiles);
	    
	    void resetCurrentIndex();
	    
	    void displayCurrentImage();
	    
	    void setImageDirectoryName(String name);

	}
	
    public static void displayFoldersInTree(File rootDirectory, TreeView<String> treeView, String catType, ImageDisplay controller, String rootDirectoryPath) {
        TreeItem<String> rootNode = new TreeItem<>(rootDirectory.getName());
        File[] subdirectories = rootDirectory.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File category : subdirectories) {
                if (catType.equals(category.getName())) {
                    TreeItem<String> categoryNode = new TreeItem<>(category.getName());
                    buildTree(category, categoryNode);
                    rootNode.getChildren().add(categoryNode);
                }
            }
        }

        Callback<TreeView<String>, TreeCell<String>> cellFactory = param -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	setStyle("-fx-background-color: lightgreen;");
                        setText(item);
                    }
                }
            };

            cell.setOnMouseClicked(event -> {
                TreeItem<String> treeItem = cell.getTreeItem();
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && treeItem.isLeaf()) {
                    // Handle the double-click event for most sub-folders (non-leaf items)
                    String selectedItem = cell.getItem();

                    // Construct the full directory path by traversing the tree item hierarchy
                    String fullPath = rootDirectoryPath + File.separator;
                    String lastComponent = new File(rootDirectoryPath).getName();
                    String subPath= "";
                    
                    TreeItem<String> parent = treeItem.getParent();
                    
                    while (parent != null) {
                    	if(!parent.getValue().equals(lastComponent)) {                       
                    		subPath=  parent.getValue() + File.separator + subPath;    
                    		//System.out.println(subPath);
                    	}
                    	parent = parent.getParent();                    
                    }               
                    fullPath = fullPath + subPath+ selectedItem;

                    File directory = new File(fullPath);
                    File[] imageFiles = directory.listFiles(TreeViewUtils::isImageFile);
                    
                    controller.setImageList(imageFiles);
                    controller.resetCurrentIndex();
                    controller.setImageDirectoryName(selectedItem);
                   
                    if (imageFiles != null) {
                        for (File imageFile : imageFiles) {
                            System.out.println(imageFile.getAbsolutePath());
                        }

                        if (imageFiles.length > 0) {
                            // Load and display the first image in the directory
                            //System.out.println("Displayed");
                            //File firstImage = imageFiles[0];
                            controller.displayCurrentImage();
                        }
                    } else {
                        System.out.println("No image files found in the directory.");
                    }
                }
            });
            return cell;
        };

        treeView.setCellFactory(cellFactory);
        treeView.setRoot(rootNode);
    }

    public static void buildTree(File directory, TreeItem<String> node) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    TreeItem<String> childNode = createTreeItem(file.getName());
                    buildTree(file, childNode);
                    node.getChildren().add(childNode);
                } else if (!isImageFile(file)) {
                    TreeItem<String> childNode = createTreeItem(file.getName());
                    node.getChildren().add(childNode);
                }
            }
        }
    }

    private static TreeItem<String> createTreeItem(String fileName) {
        TreeItem<String> treeItem = new TreeItem<>(fileName);

        return treeItem;
    }

    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }
}
