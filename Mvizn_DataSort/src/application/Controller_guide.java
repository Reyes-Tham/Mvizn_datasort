package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.io.File;

public class Controller_guide {

    @FXML
    private Pane HNCDSpane;

    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider videoSlider;

    @FXML
    private Button pauseButton;

    @FXML
    void onKeyPressed(KeyEvent event) {

    }

    @FXML
    void onMouseMoved(MouseEvent event) {

    }
    
    @FXML
    void onPause(MouseEvent event) {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                pauseButton.setText("Resume");
            } else {
                mediaPlayer.play();
                pauseButton.setText("Pause");
            }
        }
    }
    
    private MediaPlayer mediaPlayer;
    
    public void initialize() {
        try {
            File file = new File("src/Videos/ApplicationGuide.mp4");
            String videoSource = file.toURI().toString();
            Media media = new Media(videoSource);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            // Initialize slider
            videoSlider.setMin(0);
            mediaPlayer.setOnReady(() -> {
                videoSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            });

            // Add listener to slider
            videoSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (!isChanging) {
                    mediaPlayer.seek(Duration.seconds(videoSlider.getValue()));
                }
            });

            // Update slider as video plays
            mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!videoSlider.isValueChanging()) {
                    videoSlider.setValue(newTime.toSeconds());
                }
            });

            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to stop the video
    public void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
    


}




