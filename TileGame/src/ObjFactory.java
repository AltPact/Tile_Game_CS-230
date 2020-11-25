import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class ObjFactory {
	private static final int tileLength=90;
	private static final int tiledepth=15;
	private static final int boardLength=1200;
	private static final int boarddepth=25;
	
	private static PhongMaterial tTileTexture = new PhongMaterial();
	private static PhongMaterial cornerTileTexture = new PhongMaterial();
	private static PhongMaterial straightTileTexture = new PhongMaterial();
	private static PhongMaterial goalTileTexture = new PhongMaterial();
	private static PhongMaterial boardTexture = new PhongMaterial();

	public ObjFactory() {
		initTTileTexture();
		initCornerTileTexture();
		initStraightTileTexture();
		initGoalTileTexture();
		initFloorTexture();
	}
	
	private void initTTileTexture() {
		tTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/TShaped.png")));
	}
	
	private void initCornerTileTexture() {
		cornerTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Corner.png")));
	}
	
	private void initStraightTileTexture() {
		straightTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Straight.png")));
	}
	private void initGoalTileTexture() {
		goalTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Goal.jpg")));
	}
	private void initFloorTexture() {
		boardTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Garden.jpg")));
	}
	
	public Box makeTTile() {
		Box tile = new Box(tileLength,tileLength,tiledepth);
		tile.setMaterial(tTileTexture);
		return tile;
	}
	
	public Box makeStraightTile() {
		Box tile = new Box(tileLength,tileLength,tiledepth);
		tile.setMaterial(cornerTileTexture);
		return tile;
	}
	
	public Box makeCornerTile() {
		Box tile = new Box(tileLength,tileLength,tiledepth);
		tile.setMaterial(straightTileTexture);
		return tile;
	}
	
	public Box makeGoalTile() {
		Box tile = new Box(tileLength,tileLength,tiledepth);
		tile.setMaterial(goalTileTexture);
		return tile;
	}
	
	public Box makeFloor() {
		Box floor = new Box(boardLength,boardLength,boarddepth);
		floor.setMaterial(boardTexture);
		return floor;
	}
	
	public Group makeLightSource(int x, int y) {
		Group lightSource = new Group();
		PointLight light = new PointLight(Color.YELLOW);
		light.getTransforms().add(new Translate(x+(x*1.4), y+(y*1.4), -400));
		Random randomAngle = new Random();
		Image nightFireFly = new Image("/img/firefly.png", 150, 150, false, false);
		ImageView firefly = new ImageView(nightFireFly);
		firefly.setOpacity(2);
		firefly.setRotate(randomAngle.nextInt(360));
		firefly.setTranslateX(x);
		firefly.setTranslateY(y);
		firefly.setTranslateZ(-400);
		firefly.setMouseTransparent(true);
		setAnimation(firefly);
		lightSource.getChildren().addAll(light,firefly);
		return lightSource;
	}
	
	public void setAnimation(ImageView firefly) {
		ScaleTransition fireFlyMoving = new ScaleTransition(Duration.millis(500), firefly);
		fireFlyMoving.setByX(firefly.getScaleX()*0.05);
		fireFlyMoving.setByY(firefly.getScaleY()*0.05);
		fireFlyMoving.setCycleCount(Animation.INDEFINITE);
		fireFlyMoving.setAutoReverse(true);
		fireFlyMoving.play();
	}
	
}
