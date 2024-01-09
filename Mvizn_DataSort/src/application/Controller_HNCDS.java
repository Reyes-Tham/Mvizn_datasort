package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import application.TreeViewUtils.ImageDisplay;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Controller_HNCDS implements ImageDisplay{
	//-------------------------------------------------------------------------------------------------------------------------------------
	//Database interface
	DatabaseConnection connection;
	
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	//TreeViewUtils Variables
	private File[] imageFiles; 
    private int currentImageIndex;
    private String imageDirectoryName;
    private String rootDirectoryName;
    private File selectedDirectoryName;
    
    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //Magnification Variables
    private double mouseX, mouseY;
    private double scaleFactor = 1.0;
    private boolean isMagnifying = false;
    private ImageView magnifiedView = new ImageView();

    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //Initializing Controller
    public Controller_HNCDS() {
    	
	}
    
    public void initialize() {
        HNCDSpane.requestFocus();
        connection = new DatabaseConnection();
        connect();
        
        //Init tooltip
        for (Toggle toggle : category.getToggles()) {
        	if(toggle instanceof ToggleButton) {
        		ToggleButton toggleButton = (ToggleButton) toggle;
        		attachHoverListener(toggleButton);
        	}   	
        }
    }
    
    private Map<String, ToolTipData> initializeToolTipData() {
        Map<String, ToolTipData> contentMap = new HashMap<>();

        // Add content for "tpos" category
        Image tposImage = new Image("file:src/Images/HNCDS/tpos.jpeg");
        String tposDescription = "When the system detects any person(s) outside of a vehicle even if partially, i.e., arm \r\n"
        		+ "or head sticking out.";
        contentMap.put("tpos", new ToolTipData(tposImage, tposDescription));

        // Add content for "container" category
        Image containerImage = new Image("file:src/Images/HNCDS/container.jpeg");
        String containerDescription = "occurs when the detected object is any part of the container";
        contentMap.put("container", new ToolTipData(containerImage, containerDescription));

        // Add content for "crane" category
        Image craneImage = new Image("file:src/Images/HNCDS/crane.jpeg");
        String craneDescription = "occurs when the detected object is any part of the crane";
        contentMap.put("crane", new ToolTipData(craneImage, craneDescription));

        // Add content for "driver_in_cabin" category
        Image driverInCabinImage = new Image("file:src/Images/HNCDS/driver_in_cabin.jpeg");
        String driverInCabinDescription = "occurs when the system detects the driver inside the cabin with no body \r\n"
        		+ "part sticking out (no hand or head and no opened doors or windows)";
        contentMap.put("driver_in_cabin", new ToolTipData(driverInCabinImage, driverInCabinDescription));
        
        // Add content for "fence" category
        Image fenceImage = new Image("file:src/Images/HNCDS/fence.jpeg");
        String fenceDescription = "occurs when the detected object is specifically the yellow and black striped fencing \r\n"
        		+ "pole at the sides of the road, usually seen beside the vehicle";
        contentMap.put("fence", new ToolTipData(fenceImage, fenceDescription));

        // Add content for "foreign_objects" category
        Image foreignObjectsImage = new Image("file:src/Images/HNCDS/foreign_objects.jpeg");
        String foreignObjectsDescription = "occurs when the detected object is of other things like plastic bags, birds, \r\n"
        		+ "road curbs, random helmet on the road, etc that do not fall into other categories except for \r\n"
        		+ "other_fpos\r\n"
        		+ "";
        contentMap.put("foreign_objects", new ToolTipData(foreignObjectsImage, foreignObjectsDescription));

        // Add content for "opp_crane" category
        Image oppCraneImage = new Image("file:src/Images/HNCDS/opp_crane.jpeg");
        String oppCraneDescription = "occurs when the detected object belongs to the other side of the road, relative \r\n"
        		+ "to the point of view from the camera on the working crane";
        contentMap.put("opp_crane", new ToolTipData(oppCraneImage, oppCraneDescription));

        // Add content for "other_fpos" category
        Image otherFposImage = new Image("file:src/Images/HNCDS/other_fpos.jpeg");
        String otherFposDescription = "occurs when the detected object does not belong to any of the other categories \r\n"
        		+ "stated here";
        contentMap.put("other_fpos", new ToolTipData(otherFposImage, otherFposDescription));

        // Add content for "reflection" category
        Image reflectionImage = new Image("file:src/Images/HNCDS/reflection.jpeg");
        String reflectionDescription = " occurs when the detected object is specifically a patch of oil/water on the road";
        contentMap.put("reflection", new ToolTipData(reflectionImage, reflectionDescription));

        // Add content for "vehicle" category
        Image vehicleImage = new Image("file:src/Images/HNCDS/vehicle.jpeg");
        String vehicleDescription = "occurs when the detected object is any part of the vehicle";
        contentMap.put("vehicle", new ToolTipData(vehicleImage, vehicleDescription));


        return contentMap;
    }
    
    public void connect() {
    	if(connection == null) {
    		connection = new DatabaseConnection();
    	}
    	connection.connect();
    }
    
    public void disconnect() {
        if (connection != null) {
        	connection.deleteAllData("hncds_leaf");
            connection.disconnect();
        }
    }
    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //FXML
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
    	//TODO Check if leaf-Dir exists, if exists edit current row with new image/new category
    	Toggle selectedToggle = category.getSelectedToggle();

        if (selectedToggle != null && selectedToggle instanceof ToggleButton) {
            ToggleButton selectedButton = (ToggleButton) selectedToggle;
            String toggleName = selectedButton.getText();
            insertData("hncds_leaf", toggleName);
            
            //Checklist feature, updates Tree Display whenever onConfirm
            TreeViewUtils.updateTreeDisplay(treeView, getAllLeafDirs(),rootDirectoryName,this);
            
            //Update the Toggle Buttons
            resetToggleText();
            changeToggleText(toggleName);
            
        } else {
            System.out.println("No toggle button selected.");
        }
    }

    @FXML
    void onExport(MouseEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Export Root Folder");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            // Fetch imageDataList from the database
            List<ImageData> imageDataList = connection.fetchImageData("hncds_leaf");

            // Prepare the hncds subfolder
            File hncdsFolder = new File(selectedDirectory, "hncds");
            if (!hncdsFolder.exists()) {
                hncdsFolder.mkdir();
            }

            // Export images into respective category folders
            exportImagesToCategories(hncdsFolder, imageDataList);
            
            // Alert Success
            String successString = "Export Successfully to: "+selectedDirectory.toString();
            showAlert("Export Success", successString);
            
            //Delete data from database
            connection.deleteAllData("hncds_leaf");
        }
    }
    
    @FXML
    void onFileSelect(MouseEvent event) {
    	//TODO Do error Message
    	
        // Create a DirectoryChooser to select the root folder
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Root Folder");
        
        //Delete data from database
        connection.deleteAllData("hncds_leaf");
        
        // Show the directory chooser dialog
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        java.io.File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {   
        	//Get absolute root directory
        	String rootDirectoryPath = selectedDirectory.getAbsolutePath();
            // Use the utility method from TreeViewUtils to populate the TreeView
            TreeViewUtils.displayFoldersInTree(selectedDirectory, treeView, "hncds", this, rootDirectoryPath);
            setRootDirectoryName(rootDirectoryPath);
        }
    }
    
    @FXML
    void onKeyPressed(KeyEvent event) {
    	KeyCode code = event.getCode();
    	//System.out.println(code);	
    	
    	if(this.imageFiles!=null) {
    		if (code == KeyCode.Q) {
				backwards();
			} else if (code == KeyCode.E) {
				forwards();
			} else if (code == KeyCode.Z) {
				System.out.println("Zooming Tool");
				toggleMagnification();
			}
    	}
         	
    }
    
    @FXML
    void onScroll(ScrollEvent event) {
    	//TODO Zoom size
    }

    
    @FXML
    void onClicked(MouseEvent event) {
       //not used
    }

    
    @FXML
    void onMouseMoved(MouseEvent event) {
    	mouseX = event.getX();
        mouseY = event.getY();

        captureSnapshot(mouseX, mouseY);
    }
    
    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
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
    
    private Blob convertImageToBlob(Image image) {
        try {
            // Convert JavaFX Image to BufferedImage
            javafx.scene.image.Image tempImage = imageScreen.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(tempImage, null);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            byte[] imageData = byteArrayOutputStream.toByteArray();

            return new SerialBlob(imageData);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void writeBlobToFile(Blob blob, File file) {
        try (InputStream in = blob.getBinaryStream();
             OutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    
    private void insertData(String table,String cat) {
    	Blob image = convertImageToBlob(imageScreen.getImage());
    	
    	//TODO query if imageDirectoryName exists, if exists replace instead of insert.
    	if(!connection.valueExists(table, "leafDir", imageDirectoryName)) {
    		Object[] data = new Object[] {rootDirectoryName,imageName.getText(),image,imageDirectoryName,cat};
        	connection.insertData(table, new String[]{"rootDir","imageName","image","leafDir","category"}, data);
    	}else {
    		Object[] data = new Object[] {rootDirectoryName, imageName.getText(), image, cat};
    		connection.updateData(table, new String[]{"rootDir", "imageName", "image", "category"}, data, "leafDir", imageDirectoryName);
    	}
    }
    
    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //TreeViewsUtils -- Utility Methods (Sets Images, DirectoryName, etc.)
    public void setImageList(File[] imageFiles) {
    	this.imageFiles = imageFiles;
    }
    
    public void resetCurrentIndex() {
    	this.currentImageIndex = 0;
    }
    
    public void setImageDirectoryName(String name) {
    	this.imageDirectoryName = name;
    }
    
    public void setRootDirectoryName(String name) {
    	this.rootDirectoryName = name;
    }
    
    public void setSelectedDirectoryPath(File file) {
    	this.selectedDirectoryName = file;
    }
    
    public void checkSelectedDirectory() {
    	if(connection.valueExists("hncds_leaf", "leafDir", imageDirectoryName)) {
    		String cat = (String) connection.getDataByColumn("hncds_leaf", "category", "leafDir", imageDirectoryName);
    		changeToggleText(cat);
    	}else {
    		resetToggleText();
    	}
    }
    
    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //Magnification Function -- Magnify Image (Using screenshot and increasing size of screenshots)
    private void toggleMagnification() {
        if (isMagnifying) {
            // Remove the magnified view
            HNCDSpane.getChildren().remove(magnifiedView);
            isMagnifying = false;
        } else {
        	// Add the magnified view
        	if (!HNCDSpane.getChildren().contains(magnifiedView)) {
                HNCDSpane.getChildren().add(magnifiedView);
            }
            isMagnifying = true;	
        }
    }
    
    private void captureSnapshot(double mouseX, double mouseY) {	
        if (imageScreen.getImage() == null) {
            return;
        }
        
        // Define the size of the area to capture
        double captureSize = 100;  // Adjust as needed

        // Calculate the starting point of the area to capture
        double startX = Math.max(0, mouseX - captureSize*2.5);
        double startY = Math.max(0, mouseY - captureSize);

        // Create a new writable image to capture the specified area
        WritableImage snapshot = new WritableImage((int) captureSize, (int) captureSize);

        SnapshotParameters params = new SnapshotParameters();
        params.setViewport(new Rectangle2D(startX, startY, captureSize, captureSize));

        // Capture the specified area from the image
        imageScreen.snapshot(params, snapshot);

        // Display the captured image in another ImageView or the magnifying glass
        magnifiedView.setImage(snapshot);
        magnifiedView.setFitWidth(captureSize*2);  
        magnifiedView.setFitHeight(captureSize*2);

        //position the magnifiedView near the cursor
        magnifiedView.setX(mouseX-captureSize);
        magnifiedView.setY(mouseY-captureSize);
    }
    
    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //Other methods
    private List<String> getAllLeafDirs(){
    	List<String> leafDirs = connection.getAllDataByColumn("hncds_leaf", "leafDir");
    	return leafDirs;
    }
    
    private void exportImagesToCategories(File hncdsFolder, List<ImageData> imageDataList) {
        for (ImageData data : imageDataList) {
            File categoryFolder = new File(hncdsFolder, data.getCategory());
            if (!categoryFolder.exists()) {
                categoryFolder.mkdirs();
            }

            File outputFile = new File(categoryFolder, data.getImageName());
            writeBlobToFile(data.getImage(), outputFile);
        }
    }

    private void changeToggleText(String cat) {
    	for(Toggle toggle:category.getToggles()) {
			if(toggle instanceof ToggleButton) {
				ToggleButton toggleButton = (ToggleButton) toggle;
				if(toggleButton.getText().equals(cat)) {
					toggleButton.setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-font-size: 20;");
				}
			}
		}
    }
    
    private void resetToggleText() {
    	for(Toggle toggle:category.getToggles()) {
			if(toggle instanceof ToggleButton) {
				ToggleButton toggleButton = (ToggleButton) toggle;
				toggleButton.setStyle("-fx-text-fill: black; -fx-font-weight: normal; -fx-font-size: 12;");
			}
		}
    }
    
    
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //Alert Method
    private void showAlert(String title, String name) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(name);
    	
    	alert.showAndWait();
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------
    //Tool tip Methods
    private void attachHoverListener(ToggleButton toggleButton) {
        Map<String, ToolTipData> tooltipContentMap = initializeToolTipData();

        toggleButton.setOnMouseEntered(event -> {
            String category = toggleButton.getText();
            ToolTipData content = tooltipContentMap.get(category);

            if (content != null) {
                Tooltip tooltip = new Tooltip(content.getDescription());

                // Setting a custom graphic (image) in the tooltip
                ImageView imageView = new ImageView(content.getImage());
                imageView.setFitHeight(250); // Set preferred size
                imageView.setFitWidth(250);
                tooltip.setGraphic(imageView);

                // Set the tooltip text style
                tooltip.setStyle("-fx-font-size: 16px;");
                
                // Assign the tooltip to the ToggleButton
                toggleButton.setTooltip(tooltip);
                tooltip.setShowDelay(Duration.millis(500)); 

            }
        });

        toggleButton.setOnMouseExited(event -> {
            toggleButton.setTooltip(null);
        });
    }

}


