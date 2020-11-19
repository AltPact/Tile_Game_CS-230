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

public class GameBoardController extends GameWindow implements Initializable{

	
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
	//"back" button action method
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml",GB);
	}
	//"quit" button action method
	@FXML
	public void buttonOnActionQ(ActionEvent event) throws IOException {
		Homepage.closeWindow();
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
