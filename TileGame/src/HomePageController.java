import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class HomePageController implements Initializable{
	
	private static Parent root;
	private static Scene currentScene;
	//FXML bindings
	@FXML
	public StackPane rSPane;
	@FXML
	public BorderPane bPane;
	@FXML
	public ImageView bgImg;
	@FXML
    public Button NewGameButton;
	@FXML
    public Button LoadGameButton;
	@FXML
    public Button TutorialButton;
	//Method runs when this class is created
	@Override
    public void initialize(URL url, ResourceBundle resources) {
		//System.out.print(bPane);
		//System.out.print(rSPane);
		//----Set moving background----
		Path p = new Path();
		p.getElements().add(new MoveTo(400,300));
		p.getElements().add(new HLineTo(600));
		PathTransition trans = new PathTransition();
		trans.setNode(bgImg);
		trans.setDuration(Duration.seconds(10));
		trans.setPath(p);
		trans.setCycleCount(PathTransition.INDEFINITE);
		trans.setAutoReverse(true);
		trans.play();
    } 
    //"New game" button action
	@FXML
	public void buttonOnActionN(ActionEvent event) throws IOException {
		//load NewGameScene
		switchScene("/fxml/NewGamePane.fxml");
		
	}
	//"Load game" button action
	@FXML
	public void buttonOnActionL(ActionEvent event) throws IOException {
			switchScene("/fxml/LoadGamePane.fxml");
			
	}
	//switch scene
	public void switchScene(String fxmlPath) throws IOException {
				root = FXMLLoader.load(getClass().getResource(fxmlPath));
				currentScene = NewGameButton.getScene();
				rSPane.getChildren().remove(bPane);
				root.translateXProperty().set(currentScene.getWidth());
				rSPane.getChildren().add(root);
				
				Timeline tl = new Timeline();
				KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
				KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
				
				tl.getKeyFrames().add(kf);
				
				tl.play();
				
				
	}
	
	//"Tutorial" button action
	@FXML
	public void buttonOnActionT(ActionEvent event) throws IOException {
		//load NewGameScene
		root = FXMLLoader.load(getClass().getResource("/fxml/TutorialPane.fxml"));
		currentScene = NewGameButton.getScene();
		//remove currentScene
		rSPane.getChildren().remove(bPane);
		root.translateXProperty().set(currentScene.getWidth());
		rSPane.getChildren().add(root);
		
		Timeline tl = new Timeline();
		KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
		
		tl.getKeyFrames().add(kf);
		
		tl.play();
		
		
	}
	//mouse on effect
	@FXML
	public void mouseOnN() {
		NewGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFN() {
		NewGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOnL() {
		LoadGameButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFL() {
		LoadGameButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOnT() {
		TutorialButton.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5em;");
		
	}
	@FXML
	public void mouseOFFT() {
		TutorialButton.setStyle("-fx-background-color: LIGHTSALMON; -fx-background-radius: 5em;");
		
	}
}
