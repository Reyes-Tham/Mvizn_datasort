package application;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Controller_more {

    @FXML
    private Pane HNCDSpane;
    
    @FXML
    private Hyperlink hyperlink;

    @FXML
    void onKeyPressed(KeyEvent event) {

    }

    @FXML
    void onMouseMoved(MouseEvent event) {

    }
    
    @FXML
    void openLink(ActionEvent event) {
    	try {
			Desktop.getDesktop().browse(new URI("https://seanbriannarciso.wixsite.com/mvizninternguide"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onCopy(MouseEvent event) {
    	Clipboard clipboard = Clipboard.getSystemClipboard();
    	ClipboardContent content = new ClipboardContent();
    	content.putString("Mvizn123");
    	clipboard.setContent(content);
    }



}



