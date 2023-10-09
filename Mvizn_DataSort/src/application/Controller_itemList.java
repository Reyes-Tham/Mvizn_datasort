package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Controller_itemList {

	@FXML
    private ImageView checklist;

    @FXML
    private Label fileName;

    
    public void setFileName(String fileName) {
    	this.fileName.setText(fileName);
    }
}



