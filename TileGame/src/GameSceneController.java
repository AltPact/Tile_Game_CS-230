import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * File Name: GameSceneController.java Created: 07/11/2020 Modified: 10/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class GameSceneController extends GameWindow implements Initializable{

	
	private static Stage stage;
	private static Parent root;
	@FXML
	public BorderPane GB;
	@FXML
    public Button BackHomeButton;
	@FXML
    public Button QuitButton;
	
	/**
	 * This method initialize this page
	 * @param url The location of root object
	 * @param resources The resources to localize the root object
	 */
	@Override
    public void initialize(URL url, ResourceBundle resources) {
		
	}
	
	/**
	 * This method is called when the back button is click
	 * it will call the switchPane() method which switch to HomePagePane
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml",GB);
	}
	
	/**
	 * This method is called when the quit button is click
	 * it will close the application window
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionQ(ActionEvent event) throws IOException {
		ApplicationController.closeWindow();
	}
	
	/**
	 * This method is called when mouse is on the back button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnB() {
		BackHomeButton.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	
	/**
	 * This method is called when mouse is off the back button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFB() {
		BackHomeButton.setStyle("-fx-background-color: GREEN; -fx-background-radius: 5em;");
	}
	
	/**
	 * This method is called when mouse is on the quit button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnQ() {
		QuitButton.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	
	/**
	 * This method is called when mouse is off the quit button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFQ() {
		QuitButton.setStyle("-fx-background-color: RED; -fx-background-radius: 5em;");
	}
	
}
