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
	//again mouse on effects
	@FXML
	public void saveOnAction1() {
		Save1.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	@FXML
	public void saveOnAction2() {
		Save2.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	@FXML
	public void saveOnAction3() {
		Save3.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	@FXML
	public void saveOnAction4() {
		Save4.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	@FXML
	public void saveOnAction5() {
		Save5.setStyle("-fx-background-color: WHITE; -fx-text-fill: black");
	}
	@FXML
	public void mouseOnB() {
		Back.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	@FXML
	public void mouseOFFB() {
		Back.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOnS() {
		Select.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	@FXML
	public void mouseOFFS() {
		Select.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void backOnAction(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", BP);	
	}
	//"select" button action(switch scene)
	@FXML
	public void selectOnAction(ActionEvent event) throws IOException {
		switchPane("/fxml/GameBoardPane.fxml",BP);
	}
}
