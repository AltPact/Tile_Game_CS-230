import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class CreatePlayerController extends GameWindow implements Initializable  {

	@FXML
	private BorderPane bPane;
	@FXML
	private Button backButton;
	@FXML
	private Button createButton;
	@FXML
	private Circle avatar;
	@FXML
	private TextField inputBox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image defaultImage = new Image("/img/defaultIcon.png");
		avatar.setFill(new ImagePattern(defaultImage));
		
	}
	
	@FXML
	public void backbuttonOnAction(ActionEvent event) throws IOException {
		switchPane("/fxml/NewGamePane.fxml",bPane, "back");
	}
	@FXML
	public void createbuttonOnAction(ActionEvent event) throws IOException {
		createPlayer();
		switchPane("/fxml/NewGamePane.fxml",bPane, "back");
	}
	private void createPlayer() {
		//default
		String iconPath = "/img/defaultIcon.png";
		String playerName = inputBox.getText();
		//do something here
		
	}

}