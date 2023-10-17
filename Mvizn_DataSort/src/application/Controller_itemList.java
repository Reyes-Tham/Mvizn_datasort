package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Controller_itemList {
	private String Filename;
	
	public void setFilename(String filename) {
		this.Filename = filename;
		System.out.println(filename);
	}
	
	public String getFilename() {
		return this.Filename;
	}
    @FXML
    private ImageView checklist;

}



