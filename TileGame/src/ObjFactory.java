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
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
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
	
	private static PhongMaterial fixedCornerTexture = new PhongMaterial();
	private static PhongMaterial fixedTTexture = new PhongMaterial();
	private static PhongMaterial fixedStraightTexture = new PhongMaterial();
	private static PhongMaterial frozenCornerTexture = new PhongMaterial();
	private static PhongMaterial frozenTTexture = new PhongMaterial();
	private static PhongMaterial frozenStraightTexture = new PhongMaterial();
	private static PhongMaterial fireTexture = new PhongMaterial();

	public ObjFactory() {
		initTTileTexture();
		initCornerTileTexture();
		initStraightTileTexture();
		initGoalTileTexture();
		initFloorTexture();
		initFixedCornerTexture();
		initFixedTTexture();
		initFixedStraightTexture();
		initFrozenCornerTexture();
		initFrozenTTexture();
		initFrozenStraightTexture();
		initFireTexture();
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
	private void initFixedCornerTexture() {
		fixedCornerTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FixedCorner.jpg")));
	}
	private void initFixedTTexture() {
		fixedTTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FixedT.jpg")));
	}
	private void initFixedStraightTexture() {
		fixedStraightTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FixedStraight.jpg")));
	}
	private void initFrozenCornerTexture() {
		frozenCornerTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FrozenCorner.jpg")));
	}
	private void initFrozenTTexture() {
		frozenTTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FrozenTShaped.jpg")));
	}
	private void initFrozenStraightTexture() {
		frozenStraightTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FrozenStraight.jpg")));
	}
	private void initFireTexture() {
		fireTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/fire.jpg")));
	}
	
	
	public Box makeTile(Placeable tileType) {
		Box tile = new Box(tileLength,tileLength,tiledepth);
		
		if(tileType.getType()==TileType.Fire) {
			tile.setMaterial(fireTexture);
		}else if(tileType.getType()==TileType.Straight&&tileType.isFrozen()) {
			tile.setMaterial(frozenStraightTexture);
		}else if(tileType.getType()==TileType.Corner&&tileType.isFrozen()) {
			tile.setMaterial(frozenCornerTexture);
		}else if(tileType.getType()==TileType.TShaped&&tileType.isFrozen()) {
			tile.setMaterial(frozenTTexture);
		}else if(tileType.getType()==TileType.Straight&&tileType.isFixed()) {
			tile.setMaterial(fixedStraightTexture);
		}else if(tileType.getType()==TileType.Corner&&tileType.isFixed()) {
			tile.setMaterial(fixedCornerTexture);
		}else if(tileType.getType()==TileType.TShaped&&tileType.isFixed()) {
			tile.setMaterial(fixedTTexture);
		}else if(tileType.getType()==TileType.TShaped) {
		    tile.setMaterial(tTileTexture);
		}else if(tileType.getType()==TileType.Straight) {
			tile.setMaterial(straightTileTexture);
		}else if(tileType.getType()==TileType.Corner) {
			tile.setMaterial(cornerTileTexture);
		}else if(tileType.getType()==TileType.Goal) {
			tile.setMaterial(goalTileTexture);
		}
		return tile;
	}
	
	public void textureTheTile(Box tile, Placeable tileType) {
		if(tileType.getType()==TileType.Fire) {
			tile.setMaterial(fireTexture);
		}else if(tileType.getType()==TileType.Straight&&tileType.isFrozen()) {
			tile.setMaterial(frozenStraightTexture);
		}else if(tileType.getType()==TileType.Corner&&tileType.isFrozen()) {
			tile.setMaterial(frozenCornerTexture);
		}else if(tileType.getType()==TileType.TShaped&&tileType.isFrozen()) {
			tile.setMaterial(frozenTTexture);
		}else if(tileType.getType()==TileType.Straight&&tileType.isFixed()) {
			tile.setMaterial(fixedStraightTexture);
		}else if(tileType.getType()==TileType.Corner&&tileType.isFixed()) {
			tile.setMaterial(fixedCornerTexture);
		}else if(tileType.getType()==TileType.TShaped&&tileType.isFixed()) {
			tile.setMaterial(fixedTTexture);
		}else if(tileType.getType()==TileType.TShaped) {
		    tile.setMaterial(tTileTexture);
		}else if(tileType.getType()==TileType.Straight) {
			tile.setMaterial(straightTileTexture);
		}else if(tileType.getType()==TileType.Corner) {
			tile.setMaterial(cornerTileTexture);
		}else if(tileType.getType()==TileType.Goal) {
			tile.setMaterial(goalTileTexture);
		}
	}
	
	
	public Box makeFloor() {
		Box floor = new Box(boardLength,boardLength,boarddepth);
		floor.setMaterial(boardTexture);
		return floor;
	}
	
	public Group makePlayer(int playerIndex) {
		Group wholeBody = new Group();
		Group wholeHat = new Group();
		Group wand = new Group();
		double bodyWidth=30;
		Box body = colorLegs(new Box(50,bodyWidth,60));
		Box leftLeg = colorLegs(new Box((body.getWidth()/2)-5, bodyWidth ,body.getDepth()));
		Box rightLeg = colorLegs(new Box((body.getWidth()/2)-5, bodyWidth ,body.getDepth()));
		Box hat = new Box(30,30,30);
		Box hatFlatPlate = new Box(90,90,5);
		wholeHat = colorHat(hat, hatFlatPlate,playerIndex);
		Cylinder ward = new Cylinder(5,160);
		Sphere orb = new Sphere(10);
		wand = colorWand(ward, orb);
		
		body.translateZProperty().set(-body.getDepth());
		leftLeg.translateXProperty().set((body.getWidth()/2)-(leftLeg.getWidth()/2));
		rightLeg.translateXProperty().set(-(body.getWidth()/2)+(leftLeg.getWidth()/2));
		hat.translateZProperty().set(-body.getDepth()*2);
		hatFlatPlate.translateZProperty().set(-body.getDepth()*2+hat.getDepth()/2-17);
		Transform rotatehatX = new Rotate(45, Rotate.X_AXIS);
		Transform rotatehatY = new Rotate(45, Rotate.Y_AXIS);
		hat.getTransforms().add(rotatehatX);
		hat.getTransforms().add(rotatehatY);
		Transform rotate = new Rotate(45, Rotate.Z_AXIS);
		wholeHat.getTransforms().add(rotate);
		ward.translateXProperty().set(body.getWidth());
		ward.translateYProperty().set(body.getHeight());
		ward.translateZProperty().set(body.getTranslateZ());
		Transform wardRotate = new Rotate(90, Rotate.X_AXIS);
		ward.getTransforms().add(wardRotate);
		orb.setTranslateX(ward.getTranslateX());
		orb.setTranslateY(ward.getTranslateY());
		orb.setTranslateZ(-ward.getHeight()*0.9);
		
		wholeBody.getChildren().addAll(body,leftLeg,rightLeg,wholeHat,wand);
		return wholeBody;
	}
	
	public Group colorHat(Box hat, Box hatPlate, int playerIndex) {
		Group wholeHat = new Group();
		PhongMaterial color;
		if(playerIndex==0) {
		    color = new PhongMaterial(Color.RED);
		}else if(playerIndex==1) {
			color = new PhongMaterial(Color.YELLOW);
		}else if(playerIndex==2) {
			color = new PhongMaterial(Color.GREEN);
		}else {
			color = new PhongMaterial(Color.BLUE);
		}
		hat.setMaterial(color);
		hatPlate.setMaterial(color);
		wholeHat.getChildren().addAll(hat,hatPlate);
		return wholeHat;
	}
	
	public Group colorWand(Cylinder c, Sphere g) {
		Group wand = new Group();
		PhongMaterial Gcolor = new PhongMaterial(Color.CYAN);
		PhongMaterial Ccolor = new PhongMaterial(Color.GRAY);
		g.setMaterial(Gcolor);
		c.setMaterial(Ccolor);
		wand.getChildren().addAll(c,g);
		return wand;
	}
	
	public Box colorLegs(Box leg) {
		PhongMaterial Lcolor = new PhongMaterial(Color.BLACK);
		leg.setMaterial(Lcolor);
		return leg;
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
