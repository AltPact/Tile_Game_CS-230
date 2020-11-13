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

public class NewGameController {
	
	private static Parent root;
	private static StackPane homeContainer;
	private static Scene currentScene;
	//FXML bindings
	@FXML
    public BorderPane BP;
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
	    //switch Scene
		public void switchScene(String fxmlPath) throws IOException {
			root = FXMLLoader.load(getClass().getResource(fxmlPath));
			currentScene = BackButton.getScene();
			homeContainer = (StackPane)currentScene.getRoot();
			//remove currentScene
			homeContainer.getChildren().remove(BP);
			root.translateXProperty().set(currentScene.getWidth());
			homeContainer.getChildren().add(root);
			
			Timeline tl = new Timeline();
			KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
			KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
			
			tl.getKeyFrames().add(kf);
			tl.play();
		}
	//"back button" action
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchScene("/fxml/HomePagePane.fxml");
	}
	//"start" button action
	@FXML
	public void buttonOnActionS(ActionEvent event) throws IOException {
		switchScene("/fxml/GameBoardPane.fxml");
		
	}
	
	//mouse on effect
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
