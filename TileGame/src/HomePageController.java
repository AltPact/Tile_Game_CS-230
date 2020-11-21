import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
/**
 * File Name: HomePageController.java Created: 07/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class HomePageController extends GameWindow implements Initializable{
	//FXML bindings

	@FXML
	public BorderPane bPane;
	@FXML
    public Button NewGameButton;
	@FXML
    public Button LoadGameButton;
	@FXML
    public Button TutorialButton;
	
	/**
	 * This method initialize this page
	 * @param url The location of root object
	 * @param resources The resources to localize the root object
	 */
	@Override
    public void initialize(URL url, ResourceBundle resources) {
		//for future use
    } 
	
	/**
	 * This method is called when the new button is click
	 * it will call the switchPane() method which switch to NewGamePane
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionN(ActionEvent event) throws IOException {
		//load NewGameScene
		switchPane("/fxml/NewGamePane.fxml", bPane);
		
	}
	
	/**
	 * This method is called when the load button is click
	 * it will call the switchPane() method which switch to LoadGamePane
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionL(ActionEvent event) throws IOException {
			switchPane("/fxml/LoadGamePane.fxml", bPane);
	}
	
	/**
	 * This method is called when the tutorial button is click
	 * it will call the switchPane() method which switch to TutorialPane
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionT(ActionEvent event) throws IOException {
		//load NewGameScene
		switchPane("/fxml/TutorialPane.fxml", bPane);
	}
	
	/**
	 * This method is called when mouse is on the new button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnN() {
		NewGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is off the new button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFN() {
		NewGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is on the load button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnL() {
		LoadGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is off the load button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFL() {
		LoadGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is on the tutorial button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnT() {
		TutorialButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is off the tutorial button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFT() {
		TutorialButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
}
