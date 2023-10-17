package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,1280,720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// Set the stage's title
	        primaryStage.setTitle("Mvizn Data Sorting Application");

	        // Make the window non-resizable
	        primaryStage.setResizable(false);
	        
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
