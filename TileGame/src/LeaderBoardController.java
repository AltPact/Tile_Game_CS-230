import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
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
/**
 * File: LeaderBoardController.java
 * Purpose: Implements a leaderboard
 * Modified: 03/12/2020
 * @author Wan Fai Tong (1909787), Sam Steadman (1910177), Morgan Firkins (852264)
 * @version: 1.3(Removed directoryIteration and added it to GameWindow)
 *
 */
public class LeaderBoardController extends GameWindow implements Initializable {
	@FXML
	private BorderPane LB;
	@FXML
	private VBox leaderBoard;
	

	@FXML
	/**
	 * Purpose: To make sure window closes when clicked
	 * @param event
	 * @throws IOException
	 */
	public void closeOnClick(ActionEvent event) throws IOException {
		// load NewGameScene
		switchPane("/fxml/HomePagePane.fxml", LB, "forward");

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		makeLeaderBoard();
	}
	
	/**
	 * Purpose: Creates leaderboard by using the playerDataQueue in GameWindow()
	 */
	private void makeLeaderBoard() {
		int rank=1;
		for(PlayerData p : playerDataQueue) {
			System.out.println(p.getName());
			System.out.println(p.getWins());
			HBox playerRow = createUserBox(p,rank);
			leaderBoard.getChildren().add(playerRow);
			rank++;
		}
	}
	/**
	 * Purpose: Creates a box on the leaderboard for the player information
	 * to be contained in
	 * @param playerData: PlayerData object to be stored in the box
	 * @param rank: The rank which the player will hold in the leaderboard
	 * @return HBox: hbox
	 */
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
	/**
	 * Purpose: Generates a label from the playerData attribute given
	 * @param content: playerData attribute converted to a String
	 * @return Label: label
	 */
	public Label userInfo(String content) {
		Label label = new Label(content);
		label.setTextFill(Color.BLACK);
		return label;
	}
}
