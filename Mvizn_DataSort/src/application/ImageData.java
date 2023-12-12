package application;

import java.sql.Blob;

public class ImageData {
	private Blob image;
	private String imageName;
	private String category;
	
	//Constructors and Setters
	public ImageData(String name, String cat, Blob image) {
		this.imageName = name;
		this.category = cat;
		this.image = image;
	}
	
	public void setImage(Blob image) {
		this.image = image;
	}
	
	public Blob getImage() {
		return this.image;
	}
	
	public void setImageName(String name) {
		this.imageName = name;
	}
	
	public String getImageName() {
		return this.imageName;
	}
	
	public void setCategory(String cat) {
		this.category = cat;
	}
	
	public String getCategory() {
		return this.category;
	}
}
