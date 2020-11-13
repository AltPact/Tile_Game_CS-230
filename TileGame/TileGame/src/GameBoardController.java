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

public class GameBoardController implements Initializable{

	
	private static Stage stage;
	private static Parent root;
	@FXML
	public BorderPane GB;
	@FXML
    public Button BackHomeButton;
	@FXML
    public Button QuitButton;
	//Made this method for future use
	@Override
    public void initialize(URL url, ResourceBundle resources) {
		
	}
	//switch Scene
	public void switchScene(String fxmlPath) throws IOException {
		root = FXMLLoader.load(getClass().getResource(fxmlPath));
		Scene currentScene = BackHomeButton.getScene();
		root.translateXProperty().set(currentScene.getWidth());
		StackPane homeContainer = (StackPane)currentScene.getRoot();
		//remove currentScene
		homeContainer.getChildren().remove(GB);
		root.translateXProperty().set(currentScene.getWidth());
		homeContainer.getChildren().add(root);
		
		Timeline tl = new Timeline();
		KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
		
		tl.getKeyFrames().add(kf);
		tl.play();
	}
	//"back" button action method
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchScene("/fxml/HomePagePane.fxml");
	}
	//"quit" button action method
	@FXML
	public void buttonOnActionQ(ActionEvent event) throws IOException {
		stage = (Stage) BackHomeButton.getScene().getWindow();
	    stage.close();
	}
	//A pile of mouse on effect methods
	@FXML
	public void mouseOnB() {
		BackHomeButton.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	@FXML
	public void mouseOFFB() {
		BackHomeButton.setStyle("-fx-background-color: GREEN; -fx-background-radius: 5em;");
	}
	@FXML
	public void mouseOnQ() {
		QuitButton.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}
	@FXML
	public void mouseOFFQ() {
		QuitButton.setStyle("-fx-background-color: RED; -fx-background-radius: 5em;");
	}
}
