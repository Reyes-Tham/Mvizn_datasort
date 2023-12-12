package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainController {
	private Controller_HNCDS currentHNCDSController;
	
	private Controller_TCDS currentTCDSController;
	
	private Controller_CLPS currentCLPSController;
	
    @FXML
    private Button CLPS;

    @FXML
    private Button HNCDS;

    @FXML
    private Button PMNRS;

    @FXML
    private Button TCDS;

    @FXML
    private Pane screenBg;

    @FXML
    void onHNCDS(MouseEvent event) {
        try {
        	switchScreen();
        	
            // If already on HNCDS screen, return
            if (currentHNCDSController != null) return;

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen_HNCDS.fxml")); 
            Pane loadedPane = loader.load();

            // Get the controller
            currentHNCDSController = loader.getController();

            // Set the loadedPane as a child of screenBg
            screenBg.getChildren().setAll(loadedPane);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void onTCDS(MouseEvent event) {
    	try {
        	switchScreen();
        	
            // If already on TCDS screen, return
            if (currentTCDSController != null) return;

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen_TCDS.fxml")); 
            Pane loadedPane = loader.load();

            // Get the controller
            currentTCDSController = loader.getController();

            // Set the loadedPane as a child of screenBg
            screenBg.getChildren().setAll(loadedPane);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void onCLPS(MouseEvent event) {
    	try {
        	switchScreen();
        	
            // If already on CLPS screen, return
            if (currentCLPSController != null) return;

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen_CLPS.fxml")); 
            Pane loadedPane = loader.load();

            // Get the controller
            currentCLPSController = loader.getController();

            // Set the loadedPane as a child of screenBg
            screenBg.getChildren().setAll(loadedPane);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void switchScreen() {
    	//Disconnect from all controllers
        if (currentHNCDSController != null) {
            currentHNCDSController.disconnect();
            currentHNCDSController = null;
        }
        
        if (currentTCDSController != null) {
            currentTCDSController.disconnect();
            currentTCDSController = null;
        }
        
        if (currentCLPSController != null) {
            currentCLPSController.disconnect();
            currentCLPSController = null;
        }
    }

}
