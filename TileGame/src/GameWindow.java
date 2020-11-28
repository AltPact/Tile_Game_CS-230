import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
/**
 * File Name: GameSceneController.java Created: 19/11/2020 Modified: 19/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class GameWindow {

	
	protected static Parent root;
	protected static Scene currentScene;
	protected static StackPane homepane;
	protected static ImageView bgImg;
	protected static BorderPane content;
	protected static MediaPlayer background;
	protected static Game currentGame;
	/**
	 * Constructor of GameWindow
	 */
	public GameWindow() {
	}

	/**
	 * This method initialize the GameWindow
	 * @param root The reference of root object
	 * @param currentScene The reference of the scene
	 * @param homepane The reference of the the StackPane
	 */
	public void init(Parent root, Scene currentScene, StackPane homepane) {
		this.root = root;
		this.currentScene = currentScene;
		this.homepane = homepane;
	}

	/**
	 * This method makes and initializes the StackPane
	 * @return The StackPane
	 */
	public StackPane initStackpane() {
		StackPane background = new StackPane();
		background.setPrefSize(600, 400);
		background.getChildren().add(setImageView());
		return background;
	}
	/**
	 * This method makes the background
	 * and add animation to move the background
	 * @return The background ImageView
	 */
	public ImageView setImageView() {
		Image image = new Image("/img/bg.jpg");
		bgImg = new ImageView();
		bgImg.setImage(image);
		bgImg.setFitHeight(1000);
		bgImg.setFitWidth(1000);
		bgImg.setPickOnBounds(true);
		bgImg.setPreserveRatio(true);
		// ----Set moving background----
		Path p = new Path();
		p.getElements().add(new MoveTo(400, 300));
		p.getElements().add(new HLineTo(600));
		PathTransition trans = new PathTransition();
		trans.setNode(bgImg);
		trans.setDuration(Duration.seconds(10));
		trans.setPath(p);
		trans.setCycleCount(PathTransition.INDEFINITE);
		trans.setAutoReverse(true);
		trans.play();
		return bgImg;
	}
	/**
	 * This method switches the current pane to a new pane
	 * @param fxmlPath The pathname of the fxml
	 * @param current The reference of the current pane
	 */
	public void switchPane(String fxmlPath, BorderPane current, String inOri) throws IOException {
		if(fxmlPath.equals("/fxml/GameBoardPane.fxml")) {
			homepane.getChildren().remove(bgImg);
			background.stop();
			//currentGame = new Game();
		}
		content = FXMLLoader.load(getClass().getResource(fxmlPath));
		homepane.getChildren().remove(current);
		homepane.getChildren().add(content);
		System.out.println("new: " + homepane.getChildrenUnmodifiable());
		if(inOri.equals("forward")) {
			content.translateXProperty().set(currentScene.getWidth());
		}else{
			content.translateXProperty().set(-currentScene.getWidth());
		}
		Timeline tl = new Timeline();
		KeyValue kv = new KeyValue(content.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        
		tl.getKeyFrames().add(kf);
		tl.play();
	}
	
    public void setCurrentGame(Game gameToBeSet) {
    	currentGame=gameToBeSet;
    }
}
