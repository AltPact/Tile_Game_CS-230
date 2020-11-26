import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
/**
 * File Name: LoadGameController.java Created: 07/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class LoadGameController extends GameWindow {
	
	private static Parent root;
	private static StackPane homeContainer;
	private static Scene currentScene;
	//Again some FXML bindings
	@FXML
	public BorderPane BP;
	@FXML
	public Button Save1;
	@FXML
	public Button Save2;
	@FXML
	public Button Save3;
	@FXML
	public Button Save4;
	@FXML
	public Button Save5;
	@FXML
	public Button Back;
	@FXML
	public Button Select;
	
	/**
	 * This method is called when the save 1 button is click
	 * it will change the color of the button
	 */
	@FXML
	public void saveOnAction1() {
		Save1.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	
	/**
	 * This method is called when the save 2 button is click
	 * it will change the color of the button
	 */
	@FXML
	public void saveOnAction2() {
		Save2.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	
	/**
	 * This method is called when the save 3 button is click
	 * it will change the color of the button
	 */
	@FXML
	public void saveOnAction3() {
		Save3.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	
	/**
	 * This method is called when the save 4 button is click
	 * it will change the color of the button
	 */
	@FXML
	public void saveOnAction4() {
		Save4.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	
	/**
	 * This method is called when the save 5 button is click
	 * it will change the color of the button
	 */
	@FXML
	public void saveOnAction5() {
		Save5.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
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
