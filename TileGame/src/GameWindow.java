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
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameWindow {

	protected static Parent root;
	protected static Scene currentScene;
	protected static StackPane homepane;
	protected static ImageView bgImg;
	protected static BorderPane content;
	protected static String homeFXML = "/fxml/HomePagePane.fxml";

	public GameWindow() {
	}

	public void init(Parent root, Scene currentScene, StackPane homepane) {
		this.root = root;
		this.currentScene = currentScene;
		this.homepane = homepane;
	}

	// switch scene
	public StackPane initStackpane() {
		StackPane background = new StackPane();
		background.setPrefSize(600, 400);
		background.getChildren().add(setImageView());
		return background;
	}
    // set ImageView
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

	// switch scene(actually it is switching pane)
	public void switchPane(String fxmlPath, BorderPane current) throws IOException {
		System.out.println("curbor: " + current);
		content = FXMLLoader.load(getClass().getResource(fxmlPath));
		homepane.getChildren().remove(current);
		content.translateXProperty().set(currentScene.getWidth());
		homepane.getChildren().add(content);
		System.out.println("new: " + homepane.getChildrenUnmodifiable());
		Timeline tl = new Timeline();
		KeyValue kv = new KeyValue(content.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);

		tl.getKeyFrames().add(kf);
		tl.play();
	}

}
