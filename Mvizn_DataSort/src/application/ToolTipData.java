package application;

import javafx.scene.image.Image;

public class ToolTipData {
	private Image image;
	private String description;
	
	public ToolTipData(Image image, String description) {
		this.image = image;
		this.description = description;
	}
	
	public Image getImage() {
		return image;
	}
	
	public String getDescription() {
		return description;
	}
}
