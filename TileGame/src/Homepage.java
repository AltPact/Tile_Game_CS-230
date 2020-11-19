import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Homepage extends Application{
	private static Stage stage;
	private static MediaPlayer background;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Scene run
		GameWindow gameWin = new GameWindow();
		StackPane home = gameWin.initStackpane();
		BorderPane homePane = FXMLLoader.load(getClass().getResource("/fxml/HomePagePane.fxml"));
		home.getChildren().add(homePane);
		Parent root = home;
		Scene scene = new Scene(root, 600, 400);
		gameWin.init(root, scene,home);
		primaryStage.setScene(scene);
        stage=primaryStage;
        primaryStage.setOnCloseRequest(e->{
        	closeWindow();
        });
		primaryStage.show();
		//Play bg music
		File bgmF = new File("src/soundtracks/bgm.mp3");
		Media bgm = new Media(bgmF.toURI().toString());
        background = new MediaPlayer(bgm);
        background.setVolume(0.2);
        background.setAutoPlay(true);
	}
	public static void closeWindow() {
		System.out.println("Save game data");
	    stage.close();
	}

}
