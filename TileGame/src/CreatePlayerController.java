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
/**
 * Controls the CreatePlayer Scene in the application
 * @author Wan Fai Tong (1909787), Sam Steadman (1910177)
 * @version 1.0
 */
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
	@FXML
	private Button upload;
	
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
		boolean createSucc=createPlayer();
		if(createSucc) {
		    switchPane("/fxml/NewGamePane.fxml",bPane, "back");
		}else {
			wrongInputAnimation("Player name already existed");
		}
	}
	private boolean createPlayer() {
		//default
		String iconPath = "/img/defaultIcon.png";
		String playerName = inputBox.getText();
		
		System.out.println(playerName + "player is created");
		for(PlayerData player:playerDataQueue) {
			if(player.getName().equals(playerName)) {
				return false;
			}
		}
		playerDataQueue.add(new PlayerData(playerName,0,0,iconPath));
		return true;
	}
	
	@FXML
	public void uploadClick(ActionEvent event) throws IOException {
		//load image
	}

}
