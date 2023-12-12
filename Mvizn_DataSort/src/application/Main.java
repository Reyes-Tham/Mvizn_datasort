package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
    private MainController mainController;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            AnchorPane root = loader.load();
            mainController = loader.getController();

            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Mvizn Data Sorting Application");
            primaryStage.setResizable(false);

            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> onClose());
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onClose() {
        if (mainController != null) {
            mainController.switchScreen(); // Disconnects database
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
