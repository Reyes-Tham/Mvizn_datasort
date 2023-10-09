package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Controller_HNCDS {

    @FXML
    private ImageView back;

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
            // Simulate a list of file paths (replace with your actual list)
            String[] filePaths = {
                "File1.txt",
                "File2.txt",
                "Folder1/File3.txt"
            };
            
            String selectedDirectoryPath = selectedDirectory.getAbsolutePath();

            System.out.println("Selected Directory: " + selectedDirectoryPath);

         	// List the children (files and subdirectories) of the selected directory
            File[] children = selectedDirectory.listFiles();

            listAllChildren(selectedDirectory);
            
            // Use the utility method from TreeViewUtils to populate the TreeView
            TreeViewUtils.populateTreeView(treeView, filePaths);
        }
    }
    
    private void listAllChildren(File directory) {
        // List the children (files and subdirectories) of the current directory
        File[] children = directory.listFiles();

        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    System.out.println("Subdirectory: " + child.getName());
                    // Recursively call the function for subdirectories
                    listAllChildren(child);
                } else {
                    System.out.println("File: " + child.getName());
                }
            }
        }
    }

    @FXML
    void onForward(MouseEvent event) {

    }
    
    
   
}


