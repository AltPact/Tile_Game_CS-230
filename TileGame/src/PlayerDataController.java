import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class PlayerDataController extends GameWindow implements Initializable {
	@FXML
	private BorderPane bPane;
	@FXML
	private TextField searchField;
	@FXML
	private Button searchButton;
	@FXML
	private GridPane playerGrid;
	@FXML
	private Button backButton;
	
	private ArrayList<PlayerData> playerList = new ArrayList<PlayerData>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//extract PlayerData ArrayList to playerList
		printPLayer();
	}
	
	@FXML
	public void backButtonClick(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", bPane, "back");
	}
	
	public void printPLayer() {
		int index=0;
		for(int i=0;i<(playerList.size()/2);i++){
			int max =2;
			if(((playerList.size()/2)%2)>0) {
			if((i+1)==(playerList.size()/2)) {
				max =1;
			}
			}
			for(int q=0;q<max;q++){
		       playerGrid.add(makePlayerBox(playerList.get(index)),i,q);
		       index++;
			}
		}
	}
	
	public HBox makePlayerBox(PlayerData player) {
		HBox playerBox = new HBox();
		playerBox.setAlignment(Pos.CENTER);
		playerBox.setPrefSize(150, 100);
		Circle playerIcon = new Circle();
		playerIcon.setRadius(33);
		playerIcon.setStrokeType(StrokeType.INSIDE);
		Image defaultImage = new Image("/img/defaultIcon.png");
		playerIcon.setFill(new ImagePattern(defaultImage));
		playerBox.getChildren().addAll(playerIcon,makeVBox(player.getName(),player.getWins(),player.getLosses()));
		return playerBox;
	}
	public VBox makeVBox(String playerName, int win, int lose) {
		VBox infoBox = new VBox();
		Label name = new Label(playerName);
		Label winLose = new Label("Win: "+win+"    Lose: "+lose);
		infoBox.getChildren().addAll(name,winLose);
		return infoBox;
	}

}
