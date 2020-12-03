import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
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
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
/**
 * File Name: LoadGameController.java Created: 07/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787), Sam Steadman (1910177), Milan Tiji (980334), Morgan Firkins (852264) 
 * @version: 1.1(Implemented getGames())
 */
public class LoadGameController extends GameWindow implements Initializable {
	
	private static Parent root;
	private static StackPane homeContainer;
	private static Scene currentScene;
	//Again some FXML bindings
	@FXML
	public BorderPane BP;
	@FXML
	public Button Back;
	@FXML
	public Button Select;
	@FXML
	public ListView gameSaves;
	
	private  ArrayList<File> gameSave = new ArrayList<File>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getGames();
	}
	/**
	 * Gets all available games from directory
	 * 
	 */
	public void getGames() {
		File [] contentsOfDir = new File("./data/savedgame").listFiles();
		Arrays.sort(contentsOfDir, (Comparator.comparingLong(File::lastModified).reversed()));
			for(File file : contentsOfDir) {
				gameSave.add(file);
		}
	}
	
	
	/**
	 * This method is called when mouse is on the back button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnB() {
		Back.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	
	/**
	 * This method is called when mouse is off the back button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFB() {
		Back.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when mouse is on the start button
	 * it will change the color of the button
	 */
	@FXML
	public void mouseOnS() {
		Select.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	
	/**
	 * This method is called when mouse is off the start button
	 * it will change the color of the button back
	 */
	@FXML
	public void mouseOFFS() {
		Select.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	
	/**
	 * This method is called when the back button is click
	 * it will call the switchPane() method which switch to HomePanePane
	 * @param event The action event
	 */
	@FXML
	public void backOnAction(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", BP, "back");	
	}
	
	/**
	 * This method is called when the select button is click
	 * it will call the switchPane() method which switch to GameBoardPane
	 * @param event The action event
	 */
	@FXML
	public void selectOnAction(ActionEvent event) throws IOException {
		switchPane("/fxml/GameBoardPane.fxml",BP, "forward");
	}


}
