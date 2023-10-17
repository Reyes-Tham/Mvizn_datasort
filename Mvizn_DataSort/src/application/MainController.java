package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainController {

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
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen_HNCDS.fxml")); 
            Pane loadedPane = loader.load();

            // Set the loadedPane as a child of screenBg
            screenBg.getChildren().setAll(loadedPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
