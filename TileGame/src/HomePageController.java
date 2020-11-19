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
	//Method runs when this class is created
	@Override
    public void initialize(URL url, ResourceBundle resources) {
		//for future use
    } 
    //"New game" button action
	@FXML
	public void buttonOnActionN(ActionEvent event) throws IOException {
		//load NewGameScene
		switchPane("/fxml/NewGamePane.fxml", bPane);
		
	}
	//"Load game" button action
	@FXML
	public void buttonOnActionL(ActionEvent event) throws IOException {
			switchPane("/fxml/LoadGamePane.fxml", bPane);
	}
	
	
	//"Tutorial" button action
	@FXML
	public void buttonOnActionT(ActionEvent event) throws IOException {
		//load NewGameScene
		switchPane("/fxml/TutorialPane.fxml", bPane);
	}
	//mouse on effect
	@FXML
	public void mouseOnN() {
		NewGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFN() {
		NewGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOnL() {
		LoadGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFL() {
		LoadGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOnT() {
		TutorialButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFT() {
		TutorialButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
}
