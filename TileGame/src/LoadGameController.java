import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
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
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Controls the functionality of the Load Game Window
 * 
 * @author Wan Fai Tong (1909787), Sam Steadman (1910177), Milan Tiji (980334),
 *         Morgan Firkins (852264)
 * @version: 1.1(Implemented getGames())
 */
public class LoadGameController extends GameWindow implements Initializable {

	private static Parent root;
	private static StackPane homeContainer;
	private static Scene currentScene;
	// Again some FXML bindings
	@FXML
	public BorderPane BP;
	@FXML
	public Button Back;
	@FXML
	public Button Select;
	@FXML
	public ListView gameSaves;

	private ArrayList<File> gameSave = new ArrayList<File>();

	@Override
	/**
	 * Initializes and sets up the window
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		getGames();
		// Lists files in the table on screen.
		for (File file : gameSave) {
			String temp = file.getName();
			String fileName = temp.substring(0, temp.length() - 4);
			Date date = new Date(file.lastModified());
			fileName += "    -    " + date.toString();
			gameSaves.getItems().add(fileName);
		}

	}

	/**
	 * Gets all available games from directory
	 */
	public void getGames() {
		File[] contentsOfDir = new File("./data/savedgame").listFiles();
		Arrays.sort(contentsOfDir, (Comparator.comparingLong(File::lastModified).reversed()));
		for (File file : contentsOfDir) {
			gameSave.add(file);
		}
	}

	/**
	 * This method is called when mouse is on the back button it will change the
	 * colour of the button
	 */
	@FXML
	public void mouseOnB() {
		Back.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}

	/**
	 * This method is called when mouse is off the back button it will change the
	 * colour of the button back
	 */
	@FXML
	public void mouseOFFB() {
		Back.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when mouse is on the start button it will change the
	 * colour of the button
	 */
	@FXML
	public void mouseOnS() {
		Select.setStyle("-fx-background-color: WHITE; -fx-text-fill: black; -fx-background-radius: 5em;");
	}

	/**
	 * This method is called when mouse is off the start button it will change the
	 * colour of the button back
	 */
	@FXML
	public void mouseOFFS() {
		Select.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");

	}

	/**
	 * This method is called when the back button is click it will call the
	 * switchPane() method which switch to HomePanePane
	 * 
	 * @param event The event that triggers the method
	 */
	@FXML
	public void backOnAction(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", BP, "back");
	}

	/**
	 * This method is called when the select button is click it will call the
	 * switchPane() method which switch to GameBoardPane
	 * 
	 * @param event The event that triggers the method
	 */
	@FXML
	public void selectOnAction(ActionEvent event) throws IOException {
		super.setCurrentGame(makeGame((String) gameSaves.getSelectionModel().getSelectedItem()));
		switchPane("/fxml/GameBoardPane.fxml", BP, "forward");
	}

	/**
	 * This method will create a game object from a string file. It looks through
	 * the files in the directory to find the correct file. It then uses
	 * gameFileReader to read the file.
	 * 
	 * @param fileNameFromList name of the requested file.
	 * @return a game object.
	 * @throws FileNotFoundException
	 */
	private Game makeGame(String fileNameFromList) throws FileNotFoundException {
		Game g = null;
		for (File file : gameSave) {
			String fileNameFromFile = file.getName().substring(0, file.getName().length() - 4);
			Date date = new Date(file.lastModified());
			fileNameFromFile += "    -    " + date.toString();
			if (fileNameFromList.equals(fileNameFromFile)) {
				g = GameFileReader.readGameFile(file);
				return g;
			}
		}
		throw new FileNotFoundException();
	}

}
