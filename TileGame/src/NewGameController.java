import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Controls the functionality of the New Game Window
 * 
 * @author Wan Fai Tong (1909787),Sam Steadman (1910177),Morgan Firkins(852264)
 * @version: 1.2(Implemented playerDataQueue from Game Window)
 */
public class NewGameController extends GameWindow implements Initializable {

	/*
	 * private static Parent root; private static StackPane homeContainer; private
	 * static Scene currentScene;
	 */
	// FXML bindings

	@FXML
	public BorderPane BP;
	@FXML
	public TextField TestField;
	@FXML
	public Button AddButton;
	@FXML
	public Button BackButton;
	@FXML
	public Button StartButton;
	@FXML
	public Button createButton;
	@FXML
	public VBox playerBox;
	@FXML
	public ComboBox searchBox;
	@FXML
	private HBox boardHBox;
	private int playerNumber = 0;
	private ArrayList<RadioButton> boardSelection = new ArrayList<RadioButton>();
	private ToggleGroup boardGroup;

	private ArrayList<PlayerData> playerQueue = new ArrayList<PlayerData>();
	private ArrayList<HBox> playerRows = new ArrayList<HBox>();

	@Override
	/**
	 * This method initialize's the new game controller. 
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		setComboBox("");
		makeBoardRadio();

	}
	
	/**
	 * This method sets the Listener for the comboBox
	 */
	private void setListenerComboBox() {
		searchBox.getEditor().textProperty().addListener((v, oldV, newV) -> {
			// setComboBox();
		});
	}
	
	/**
	 * Set's the comboBox to allow a person 
	 * to search for a string.
	 * @param searchName
	 */
	private void setComboBox(String searchName) {

		searchBox.getItems().clear();
		if (searchName.equals("")) {
			for (PlayerData player : playerDataQueue) {
				if (!checkIfInQueue(player)) {
					searchBox.getItems().add(player.getName());
				}
			}
		} else {
			for (PlayerData player : playerDataQueue) {
				if (!checkIfInQueue(player)) {
					if (player.getName().equals(searchName)) {
						searchBox.getItems().add(player.getName());
					}
				}
			}
		}
	}
	
	/**
	 * Makes the board radio button, allows a user 
	 * to choose the board they want to play. 
	 */
	private void makeBoardRadio() {
		boardGroup = new ToggleGroup();
		RadioButton boardChoose1 = new RadioButton();
		boardChoose1.setText("Board1");
		boardChoose1.setTextFill(Color.WHITE);
		boardChoose1.setMnemonicParsing(false);
		boardChoose1.setToggleGroup(boardGroup);
		boardChoose1.setSelected(true);
		boardSelection.add(boardChoose1);
		RadioButton boardChoose2 = new RadioButton();
		boardChoose2.setText("Board2");
		boardChoose2.setTextFill(Color.WHITE);
		boardChoose2.setMnemonicParsing(false);
		boardChoose2.setToggleGroup(boardGroup);
		boardSelection.add(boardChoose2);
		boardHBox.getChildren().addAll(boardChoose1, boardChoose2);
	}
	
	/**
	 * Adds a player to the queue of players that can play.
	 * @param playerToAdd the player to add to the queue.
	 */
	private void addPlayerInQueue(PlayerData playerToAdd) {
		playerQueue.add(playerToAdd);
		playerBox.getChildren().add(makePlayerRows(playerToAdd));

	}
	
	/**
	 * Makes the rows for the players in the drop-down box.
	 * @param playerInfo The player 
	 * @return the row.
	 */
	private HBox makePlayerRows(PlayerData playerInfo) {
		HBox row = new HBox();
		row.setPrefSize(496, 22);
		row.getChildren().addAll(makeLabel(playerInfo.getName()), makeButton(row, playerInfo));
		playerRows.add(row);
		return row;
	}
	
	/**
	 * Makes the buttons for each player to be added to a particular game.
	 * @param row the row the player has clicked on. 
	 * @param player The player data that has been selected. 
	 * @return the new button.
	 */
	private Button makeButton(HBox row, PlayerData player) {
		Button removeButton = new Button();
		removeButton.setText("remove");
		removeButton.setPrefSize(138, 25);
		removeButton.setTextFill(Color.WHITE);
		removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		removeButton.setOnAction(e -> {
			playerBox.getChildren().remove(row);
			playerQueue.remove(player);
			searchBox.getItems().add(player.getName());
		});
		removeButton.setOnMouseEntered(e -> {
			removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		});
		removeButton.setOnMouseExited(e -> {
			removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		});
		return removeButton;
	}
	
	/**
	 * Makes the label for the player name in the drop down box.
	 * @param name the name to be added.
	 * @return the label that has been created. 
	 */
	private Label makeLabel(String name) {
		Label playerName = new Label();
		playerName.setText(name);
		playerName.setAlignment(Pos.CENTER);
		playerName.setPrefSize(426, 25);
		playerName.setTextFill(Color.WHITE);
		playerName.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		return playerName;
	}
	
	/**
	 * In the event a user clicks the add player button. 
	 * @param event the event that has occurred. 
	 * @throws IOException If a illegal user input
	 */
	@FXML
	public void addButtonClick(ActionEvent event) throws IOException {
		String playerName = (String) searchBox.getValue();
		if (playerName == null) {
			wrongInputAnimation("Incorrect user input");
		} else if (playerQueue.size() == 4) {
			wrongInputAnimation("Reach the maximum players");
		} else {
			PlayerData playerToAdd = getExistPlayer(playerName);
			if (playerToAdd != null) {
				addPlayerInQueue(playerToAdd);
				setComboBox("");
			} else {
				wrongInputAnimation("No such player/Player Already in Queue");
			}
		}
		System.out.println(playerQueue);
	}
	
	/**
	 * Checks to see if a player has already been added to the 
	 * player data queue.
	 * @param playerToCheck
	 * @return true if the have been, false if they haven't. 
	 */
	private boolean checkIfInQueue(PlayerData playerToCheck) {
		for (PlayerData playerInQueue : playerQueue) {
			if (playerToCheck == playerInQueue) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the player's data from the name on the screen
	 * (Links the screen's data to the backend's data).
	 * @param name the name of the player
	 * @return the player's data.
	 */
	private PlayerData getExistPlayer(String name) {
		for (PlayerData playerInQueue : playerQueue) {
			if (playerInQueue.getName().equals(name)) {
				return null;
			}
		}
		for (PlayerData player : playerDataQueue) {
			if (player.getName().equals(name)) {
				return player;
			}
		}

		return null;
	}
	
	/**
	 * If a person clicks to create a new player. 
	 * @param event the event that has been triggered
	 * @throws IOException
	 */
	@FXML
	public void createButtonClick(ActionEvent event) throws IOException {
		switchPane("/fxml/CreatePlayerPane.fxml", BP, "back");
	}
	
	/**
	 * Changes the colour of the create newPlayer button to white.
	 */
	@FXML
	public void mouseOnCreate() {
		createButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	/**
	 * Changes the colour of the create newPlayer button to light salmon.
	 */
	@FXML
	public void mouseOffCreate() {
		createButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}
	
	/**
	 * Checks the ammount of players in the queue.
	 * @return
	 */
	private boolean checkPlayerAmount() {
		// System.out.println(playerQueue.size());
		if (playerQueue.size() < 2) {
			return false;
		}
		return true;
	}

	/**
	 * This method is called when the back button is click it will call the
	 * switchPane() method which switch to HomePagePane
	 * 
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", BP, "back");
	}

	/**
	 * This method is called when the start button is click it will call the
	 * switchPane() method which switch to GameBoardPane
	 * 
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionS(ActionEvent event) throws IOException {
		if (checkPlayerAmount() == true) {
			int boardSelected = 0;
			int index = 0;
			RadioButton selected = (RadioButton) boardGroup.getSelectedToggle();
			for (RadioButton eachButton : boardSelection) {
				if (selected == eachButton) {
					boardSelected = index;
				}
				index++;
			}
			initGame(boardSelected);
			switchPane("/fxml/GameBoardPane.fxml", BP, "forward");
		} else {
			wrongInputAnimation("Not enough player(Minimun: 2)");
		}
	}

	/**
	 * This method creates a new game and sets the board to the correct board.
	 * 
	 * @param boardNumber the board to be read
	 */
	private void initGame(int boardNumber) {
		if (boardNumber == 0) {
			PlayerPiece[] players = new PlayerPiece[playerQueue.size()];
			for (int i = 0; i < playerQueue.size(); i++) {
				players[i] = new PlayerPiece(0, 0, "Blue", false, playerQueue.get(i));
			}
			File f = new File("./data/gameboard/" + boardNumber + ".txt");
			currentGame = GameFileReader.readBoardFile(f, players);
		} else if (boardNumber == 1) {
			PlayerPiece[] players = new PlayerPiece[playerQueue.size()];
			for (int i = 0; i < playerQueue.size(); i++) {
				players[i] = new PlayerPiece(0, 0, "Blue", false, playerQueue.get(i));
			}
			File f = new File("./data/gameboard/" + boardNumber + ".txt");
			currentGame = GameFileReader.readBoardFile(f, players);
		}
	}

	/**
	 * This method is called when mouse is on the textfield it will change the color
	 * of the textfield
	 */
	@FXML
	public void mouseOnTF() {
		TestField.setStyle(
				"-fx-background-color: rgba(211, 211, 211, 0.5); -fx-border-color: transparent transparent lightsalmon transparent;");

	}

	/**
	 * This method is called when mouse is off the textfield it will change the
	 * color of the textfield back
	 */
	@FXML
	public void mouseOFFTF() {
		TestField.setStyle(
				"-fx-background-color: rgba(128, 128, 128, 0.5); -fx-border-color: transparent transparent lightsalmon transparent;");

	}

	/**
	 * This method is called when mouse is on the add button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnA() {
		AddButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is off the add button it will change the
	 * color of the button back
	 */
	@FXML
	public void mouseOFFA() {
		AddButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is on the back button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnB() {
		BackButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is off the back button it will change the
	 * color of the button back
	 */
	@FXML
	public void mouseOFFB() {
		BackButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is on the start button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnS() {
		StartButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is off the start button it will change the
	 * color of the button back
	 */
	@FXML
	public void mouseOFFS() {
		StartButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

}
