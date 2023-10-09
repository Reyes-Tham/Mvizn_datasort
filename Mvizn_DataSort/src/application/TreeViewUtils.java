package application;
	

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
	
public class TreeViewUtils {
	public static TreeItem<String> createTreeItem(String fileName) {
		try {
			FXMLLoader loader = new FXMLLoader(TreeViewUtils.class.getResource("itemList.fxml"));
	        VBox itemBox = loader.load();
	        TreeItem<String> treeItem = new TreeItem<>(fileName);
	        treeItem.setGraphic(itemBox);
	
	        Controller_itemList controller = loader.getController();
	        controller.setFileName(fileName);
	
	        return treeItem;
	       	} catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	public static void populateTreeView(TreeView<String> treeView, String[] filePaths) {
    	// Check if the root is null and initialize it if necessary
        if (treeView.getRoot() == null) {
            treeView.setRoot(new TreeItem<>()); // Initialize the root
        }
        
        // Clear existing TreeView content
        treeView.getRoot().getChildren().clear();

        // Populate the TreeView with file paths
        for (String filePath : filePaths) {
            TreeItem<String> treeItem = createTreeItem(filePath);
            treeView.getRoot().getChildren().add(treeItem);
        }
    }
	  
	
	    
}
