import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LeaderBoardController extends GameWindow implements Initializable {
	@FXML
	private BorderPane LB;
	@FXML
	private VBox leaderBoard;

	@FXML
	public void closeOnClick(ActionEvent event) throws IOException {
		// load NewGameScene
		switchPane("/fxml/HomePagePane.fxml", LB, "forward");

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        
		makeLeaderBoard();
	}

	public ArrayList<PlayerData> getPlayerArray(){
		ArrayList<PlayerData> playerdataArray = new ArrayList<PlayerData>();
		File[] contentsOfDir = new File("data/playerdata").listFiles();
		for(File file:contentsOfDir) {
			
			playerdataArray.add(PlayerDataFileReader.readFile(file));
			
		}
		
		return sortPlayerWin(playerdataArray);
	}
	
	public ArrayList<PlayerData> sortPlayerWin(ArrayList<PlayerData> unsortedArray){
		return Collections.sort((List<T>) unsortedArray);
	}
	
	
	public void makeLeaderBoard() {
		int rank=1;
		
		ArrayList<PlayerData> playerdataArray = getPlayerArray();
		
		for(PlayerData p : playerdataArray) {
		HBox playerRow = createUserBox(p,rank);
		leaderBoard.getChildren().add(playerRow);
		rank++;
		}
	}

	public HBox createUserBox(PlayerData playerData, int rank) {
		String style="";
		HBox hbox = new HBox();
		hbox.setPrefHeight(25);
		hbox.setPrefWidth(420);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(70);
		if(rank==1) {
			style="-fx-background-color: rgba(255,223,0, 0.5);";
		}else if(rank==2) {
			style="-fx-background-color: rgba(192,192,192, 0.5);";
		}else if(rank==3){
			style="-fx-background-color: rgba(222,184,135, 0.5);";
		}else {
			style="-fx-background-color: transparent;";
		}
		hbox.setStyle(style);
		hbox.getChildren().addAll(userInfo(playerData.getName()), 
				userInfo(Integer.toString(playerData.getWins())));
		return hbox;
	}
	
	public Label userInfo(String content) {
		Label label = new Label(content);
		label.setTextFill(Color.WHITE);
		return label;
	}
}
