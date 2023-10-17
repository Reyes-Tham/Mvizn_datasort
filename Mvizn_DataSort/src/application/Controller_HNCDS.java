package application;

import java.io.File;
import java.net.MalformedURLException;

import application.TreeViewUtils.ImageDisplay;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Controller_HNCDS implements ImageDisplay{
	
	private File[] imageFiles;  // List of image files in the current directory
    private int currentImageIndex;  // Index of the currently displayed image
    
    //Constructor
    public Controller_HNCDS() {
    	//HNCDSpane.requestFocus();
	}
    
    
    @FXML
    private Pane HNCDSpane;
    
    @FXML
    private ImageView back;
    
    @FXML
    private ImageView imageScreen;

    @FXML
    private ToggleGroup category;

    @FXML
    private Button confirm;

    @FXML
    private ToggleButton container;

    @FXML
    private ToggleButton crane;

    @FXML
    private ToggleButton driver_in_cabin;

    @FXML
    private ToggleButton fence;

    @FXML
    private ToggleButton foreign_objects;

    @FXML
    private ImageView forward;

    @FXML
    private Label imageName;

    @FXML
    private Label imageNo;

    @FXML
    private ToggleButton opp_crane;

    @FXML
    private ToggleButton other_fpos;

    @FXML
    private ToggleButton reflection;

    @FXML
    private ToggleButton tpos;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private ToggleButton vehicle;

    @FXML
    void onBack(MouseEvent event) {
        backwards();
    }

    @FXML
    void onForward(MouseEvent event) {
        forwards();
    }
    
    @FXML
    void onConfirm(MouseEvent event) {

    }

    @FXML
    void onExport(MouseEvent event) {

    }
    
    @FXML
    void onFileSelect(MouseEvent event) {
        // Create a DirectoryChooser to select the root folder
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Root Folder");

        // Show the directory chooser dialog
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        java.io.File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {   
        	//Get absolute root directory
        	String rootDirectoryPath = selectedDirectory.getAbsolutePath();
            // Use the utility method from TreeViewUtils to populate the TreeView
            TreeViewUtils.displayFoldersInTree(selectedDirectory, treeView, "hncds", this, rootDirectoryPath);
        }
    }
    
    @FXML
    void onKeyPressed(KeyEvent event) {
    	KeyCode code = event.getCode();

        if (code == KeyCode.Q) {
        	backwards();
        	System.out.println("b");
        } else if (code == KeyCode.E) {
        	forwards();
        	System.out.println("f");
        }
    }
    
    //Methods
    private void backwards() {
    	if (currentImageIndex > 0) {
            currentImageIndex--;
            displayCurrentImage();
        }
    }
    
    private void forwards() {
    	if (currentImageIndex < imageFiles.length - 1) {
            currentImageIndex++;
            displayCurrentImage();
        }
    }
    public void displayCurrentImage() {
        if (currentImageIndex >= 0 && currentImageIndex < imageFiles.length) {
            File currentImage = imageFiles[currentImageIndex];
            loadAndDisplayImage(currentImage);
            
            imageName.setText(currentImage.getName());
            
            imageNo.setText((currentImageIndex + 1) + " / " + imageFiles.length);
        }
    }
    
    @Override
    public void loadAndDisplayImage(File imageFile) {
        if (imageFile != null && imageFile.length() > 0) {
            // Load and display the image in the ImageView
            try {
                String imageUrl = imageFile.toURI().toURL().toExternalForm();
                imageScreen.setImage(new Image(imageUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setImageList(File[] imageFiles) {
    	this.imageFiles = imageFiles;
    }
    
    public void resetCurrentIndex() {
    	this.currentImageIndex = 0;
    }

}


