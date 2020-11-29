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
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
/**
 * File Name: LoadGameController.java Created: 07/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
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
	
	private Game gameSave[];
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addGameSave();
	}
	
	public void addGameSave() {
		gameSaves.getItems().add("Game Save");
		
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
