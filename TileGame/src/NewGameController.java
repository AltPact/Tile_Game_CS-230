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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class NewGameController extends GameWindow {
	
	/*private static Parent root;
	private static StackPane homeContainer;
	private static Scene currentScene;*/
	//FXML bindings
	
	@FXML
    public BorderPane BP;
	@FXML
	public TextField TestField;
	@FXML
    public Button AddButton;
	@FXML
	public Button DeleteButton1;
	@FXML
	public Button DeleteButton2;
	@FXML
	public Button DeleteButton3;
	@FXML
    public Button Button1;
	@FXML
    public Button Button2;
	@FXML
    public Button Button3;
	@FXML
    public Button Button4;
	@FXML
    public Button BackButton;
	@FXML
    public Button StartButton;
	//"back button" action
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml",BP);
	}
	//"start" button action
	@FXML
	public void buttonOnActionS(ActionEvent event) throws IOException {
		switchPane("/fxml/GameBoardPane.fxml",BP);
		
	}
	
	//mouse on effect
	@FXML
	public void mouseOnTF() {
		TestField.setStyle("-fx-background-color: rgba(211, 211, 211, 0.5); -fx-border-color: transparent transparent lightsalmon transparent;");
		
	}
	@FXML
	public void mouseOFFTF() {
		TestField.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-border-color: transparent transparent lightsalmon transparent;");
		
	}
	@FXML
	public void mouseOnA() {
		AddButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 1em;");
		
	}
	@FXML
	public void mouseOFFA() {
		AddButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOnD1() {
		DeleteButton1.setStyle("-fx-background-color: rgba(211, 211, 211, 0.5);");
		
	}
	@FXML
	public void mouseOFFD1() {
		DeleteButton1.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		
	}
	@FXML
	public void mouseOnD2() {
		DeleteButton2.setStyle("-fx-background-color: rgba(211, 211, 211, 0.5);");
		
	}
	@FXML
	public void mouseOFFD2() {
		DeleteButton2.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		
	}
	@FXML
	public void mouseOnD3() {
		DeleteButton3.setStyle("-fx-background-color: rgba(211, 211, 211, 0.5);");
		
	}
	@FXML
	public void mouseOFFD3() {
		DeleteButton3.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		
	}
	@FXML
	public void mouseOnB() {
		BackButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFB() {
		BackButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOnS() {
		StartButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFS() {
		StartButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	//button click on effect
	@FXML
	public void buttonOnAction1() {
		Button1.setStyle("-fx-background-color: WHITE; -fx-border-color: BLACK; -fx-border-width: 2.5px; -fx-text-fill: black");
	}
	@FXML
	public void buttonOnAction2() {
		Button2.setStyle("-fx-background-color: WHITE; -fx-border-color: BLACK; -fx-border-width: 2.5px; -fx-text-fill: black");
	}
	@FXML
	public void buttonOnAction3() {
		Button3.setStyle("-fx-background-color: WHITE; -fx-border-color: BLACK; -fx-border-width: 2.5px; -fx-text-fill: black");
	}
	@FXML
	public void buttonOnAction4() {
		Button4.setStyle("-fx-background-color: WHITE; -fx-border-color: BLACK; -fx-border-width: 2.5px; -fx-text-fill: black");
	}
}
