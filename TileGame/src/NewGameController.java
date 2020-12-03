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
 * File Name: NewGameController.java Created: 07/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTestArray();
		
		setComboBox("");
		makeBoardRadio();
		System.out.println("Length of list" + testPlayerList.size());
		
	}
	
	private void setListenerComboBox() {
		searchBox.getEditor().textProperty().addListener((v,oldV,newV)->{
			//setComboBox();
		});
	} 

	private void setComboBox(String searchName) {
		searchBox.getItems().clear();
		if (searchName.equals("")) {
			for (PlayerData player : testPlayerList) {
				if (!checkIfInQueue(player)) {
					searchBox.getItems().add(player.getName());
				}
			}
		} else {
			for (PlayerData player : testPlayerList) {
				if (!checkIfInQueue(player)) {
					if(player.getName().equals(searchName)) {
					   searchBox.getItems().add(player.getName());
					}
				}
			}
		}
	}

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

	private void addPlayerInQueue(PlayerData playerToAdd) {
		playerQueue.add(playerToAdd);
		playerBox.getChildren().add(makePlayerRows(playerToAdd));

	}

	private HBox makePlayerRows(PlayerData playerInfo) {
		HBox row = new HBox();
		row.setPrefSize(496, 22);

		row.getChildren().addAll(makeLabel(playerInfo.getName()), makeButton(row, playerInfo));
		playerRows.add(row);
		return row;
	}

	private Button makeButton(HBox row, PlayerData player) {
		Button removeButton = new Button();
		removeButton.setText("remove");
		removeButton.setPrefSize(138, 25);
		removeButton.setTextFill(Color.WHITE);
		removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		removeButton.setOnAction(e -> {
			playerQueue.remove(player);
			playerBox.getChildren().remove(row);
		});
		removeButton.setOnMouseEntered(e -> {
			removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		});
		removeButton.setOnMouseExited(e -> {
			removeButton.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
		});
		return removeButton;
	}

	private Label makeLabel(String name) {
		Label playerName = new Label();
		playerName.setText(name);
		playerName.setAlignment(Pos.CENTER);
		playerName.setPrefSize(426, 25);
		playerName.setTextFill(Color.WHITE);
		playerName.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		return playerName;
	}

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

	private boolean checkIfInQueue(PlayerData playerToCheck) {
		for (PlayerData playerInQueue : playerQueue) {
			if (playerToCheck == playerInQueue) {
				return true;
			}
		}
		return false;
	}

	private PlayerData getExistPlayer(String name) {
		for (PlayerData playerInQueue : playerQueue) {
			if (playerInQueue.getName().equals(name)) {
				return null;
			}
		}
		for (PlayerData player : testPlayerList) {
			if (player.getName().equals(name)) {
				return player;
			}
		}

		return null;
	}

	/*
	 * private boolean createGameSucc() {
	 * 
	 * }
	 */
	@FXML
	public void createButtonClick(ActionEvent event) throws IOException {
		switchPane("/fxml/CreatePlayerPane.fxml", BP, "back");
	}

	@FXML
	public void mouseOnCreate() {
		createButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");

	}

	@FXML
	public void mouseOffCreate() {
		createButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

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
			currentGame = initGame(boardSelected);
			switchPane("/fxml/GameBoardPane.fxml", BP, "forward");
		} else {
			wrongInputAnimation("Not enough player(Minimun: 2)");
		}
	}

	private Game initGame(int boardNumber) {
		// working in progress
		if (boardNumber == 0) {
			// GameFileReader.readBoardFile(filename, 4);
		}
		return null;
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
