import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * File Name: ApplicationController.java Created: 07/11/2020 Modified:
 * 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Adem Arik (850904), Morgan Firkins(852264), Josh Sinderberry (851800) Version: 1.0
 */
public class ApplicationController extends Application {
	private static Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method starts the application window
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Scene run
		GameWindow gameWin = new GameWindow();
		StackPane home = gameWin.initStackpane();
		BorderPane homePane = FXMLLoader.load(getClass().getResource("/fxml/HomePagePane.fxml"));
		home.getChildren().add(homePane);
		Parent root = home;
		Scene scene = new Scene(root, 600, 400);
		gameWin.init(root, scene, home);
		primaryStage.setScene(scene);
		stage = primaryStage;
		primaryStage.setOnCloseRequest(e -> {
			closeWindow(gameWin);
		});
		primaryStage.show();

	}

	/**
	 * This method will save game data
	 */
	public static void closeWindow(GameWindow gameWin) {
		if (gameWin.getCurrentGame() != null) {
			TextInputDialog newFileNameDialog = new TextInputDialog();
			newFileNameDialog.setTitle("Save Game Data File Name");
			newFileNameDialog.setHeaderText("Please enter a file name to save the game data");
			newFileNameDialog.setContentText("Game Name: ");
			String newFileName;
			try {
				Optional<String> msgBoxRespDialog = newFileNameDialog.showAndWait();
				newFileName = msgBoxRespDialog.get();
				System.out.println(newFileName);
			} catch (NoSuchElementException e) {
				System.out.println("User has selected cancel");

			}

		}

		else {
			
		}
		stage.close();
	}

}
