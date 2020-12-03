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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.shape.HLineTo;
import javafx.scene.input.MouseEvent; 
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * File Name: HomePageController.java Created: 07/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class HomePageController extends GameWindow implements Initializable {
	// FXML bindings
	@FXML
	private BorderPane bPane;
	@FXML
	private Label messageOfTheDay;
	@FXML
	private Button NewGameButton;
	@FXML
	private Button LoadGameButton;
	@FXML
	private Button TutorialButton;

	/**
	 * This method initialize this page
	 * 
	 * @param url       The location of root object
	 * @param resources The resources to localize the root object
	 */
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		// for future use
		// Play bg music
		if (homepane != null) {
			if (!homepane.getChildren().contains(bgImg)) {
				homepane.getChildren().add(bgImg);
			}
		}
		try {
		    messageOfTheDay.setText(MessageOfTheDay.getResult());
		    System.out.println(MessageOfTheDay.getResult());
		} catch (IOException e) {
			System.out.println("Cannot get message of the day");
		}
		if (background == null) {
			File bgmF = new File("src/soundtracks/bgm.mp3");
			Media bgm = new Media(bgmF.toURI().toString());
			background = new MediaPlayer(bgm);
			background.setVolume(0.1);
			//background.play();
		} else if (!background.getStatus().equals(Status.PLAYING)) {
			//background.play();
		}
		
	}

	@FXML
	public void playerIconOnClick(ActionEvent event) throws IOException {
		// load NewGameScene
		switchPane("/fxml/PlayerInfoPane.fxml", bPane, "forward");

	}
	@FXML
	public void LeaderBoardOnClick(ActionEvent event) throws IOException {
		// load NewGameScene
		switchPane("/fxml/LeaderBoard.fxml", bPane, "forward");

	}
	
	/**
	 * This method is called when the new button is click it will call the
	 * switchPane() method which switch to NewGamePane
	 * 
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionN(ActionEvent event) throws IOException {
		// load NewGameScene
		switchPane("/fxml/NewGamePane.fxml", bPane, "forward");

	}

	/**
	 * This method is called when the load button is click it will call the
	 * switchPane() method which switch to LoadGamePane
	 * 
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionL(ActionEvent event) throws IOException {
		switchPane("/fxml/LoadGamePane.fxml", bPane, "forward");
	}

	/**
	 * This method is called when the tutorial button is click it will call the
	 * switchPane() method which switch to TutorialPane
	 * 
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionT(ActionEvent event) throws IOException {
		// load NewGameScene
		switchPane("/fxml/TutorialPane.fxml", bPane, "back");
	}

	/**
	 * This method is called when mouse is on the new button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnN() {
		NewGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is off the new button it will change the
	 * color of the button back
	 */
	@FXML
	public void mouseOFFN() {
		NewGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is on the load button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnL() {
		LoadGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is off the load button it will change the
	 * color of the button back
	 */
	@FXML
	public void mouseOFFL() {
		LoadGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is on the tutorial button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnT() {
		TutorialButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is off the tutorial button it will change
	 * the color of the button back
	 */
	@FXML
	public void mouseOFFT() {
		TutorialButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}
}
