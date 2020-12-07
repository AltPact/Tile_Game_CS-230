


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

/**
 * This class is designed to show all of the players in the game's 
 * saved data files.
 * @author Wan Fai Tong (1909787)
 * @version 1
 */

public class PlayerInfoController extends GameWindow implements Initializable {
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
	
	/**
	 * Initialize's the PlayerInfo Controller
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getPlayers();
		printPlayer();
	}
	
	/**
	 * Switches panes back to the HomePage
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void backButtonClick(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", bPane, "back");
	}
	
	/**
	 * Gets the players from the PlayerDataPriorityQueue
	 */
	private void getPlayers() {
		for(PlayerData p : playerDataQueue) {
			playerList.add(p);
		}
	}
	
	/**
	 * Prints the player data to the screen 
	 */
	private void printPlayer() {
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
	/**
	 * Constructs columns for table containing player data
	 * @param player
	 * @return playerBox
	 */
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
	/**
	 * Constructs rows for table containing player data
	 * @param playerName
	 * @param win
	 * @param lose
	 * @return infoBox
	 */
	public VBox makeVBox(String playerName, int win, int lose) {
		VBox infoBox = new VBox();
		Label name = new Label(playerName);
		Label winLose = new Label("Wins: "+win+"\nLosses: "+lose);
		infoBox.getChildren().addAll(name,winLose);
		return infoBox;
	}

}
