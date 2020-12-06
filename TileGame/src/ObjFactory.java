import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
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
	private static PhongMaterial arrowUpTexture = new PhongMaterial();
	private static PhongMaterial arrowDownTexture = new PhongMaterial();
	private static PhongMaterial arrowLeftTexture = new PhongMaterial();
	private static PhongMaterial arrowRightTexture = new PhongMaterial();


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
		initArrowTextures();
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
	private void initArrowTextures() {
		arrowUpTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-up.png")));
		arrowDownTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-down.png")));
		arrowLeftTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-left.png")));
		arrowRightTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-right.png")));
	}
	
	public Box makeTileInInventory(int tileType) {
		Box tile = new Box(70,70,10);
		PhongMaterial tileTexture = new PhongMaterial();
		if(tileType==0) {//fire
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/fireTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/fireTile.jpg")));
		}else if(tileType==1) {//ice
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/IceTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/IceTile.jpg")));
		}else if(tileType==2) {//doubleMove
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/doubleMoveTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/doubleMoveTile.jpg")));
		}else if(tileType==3) {//backtrack
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/backTrackTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/backTrackTile.jpg")));
		}else if(tileType==4) {//corner
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Corner.png")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/Corner.png")));
		}else if(tileType==5) {//straight
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Straight.png")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/Straight.png")));
		}else if(tileType==6) {//t-shaped
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/TShaped.png")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/TShaped.png")));
		}
		tile.setMaterial(tileTexture);
		return tile;
	}

	/**
	 * Constructs a textured arrow box that will indicate where a tile can be pushed into the board
	 * @param orientation the orientation of the arrow (up, down, left, right)
	 * @return a textured box that can be drawn to the screen
	 */
	public Box makeArrow(int orientation) {
		Box arrow = new Box(tileLength, tileLength, tiledepth);
		switch (orientation) {
			case 0:  // up
				arrow.setMaterial(arrowUpTexture);
				break;
			case 1:  // down
				arrow.setMaterial(arrowDownTexture);
				break;
			case 2:  // left
				arrow.setMaterial(arrowLeftTexture);
				break;
			case 3:  // right
				arrow.setMaterial(arrowRightTexture);
				break;
		}
		return arrow;
	}


	public Box makeTile(Placeable tileType) {
		Box tile = new Box(tileLength,tileLength,tiledepth);
		
		if(tileType.isOnFire()) {
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
		    System.out.println("make TShaped");
		}else if(tileType.getType()==TileType.Straight) {
			tile.setMaterial(straightTileTexture);
			System.out.println("make Straight");
		}else if(tileType.getType()==TileType.Corner) {
			tile.setMaterial(cornerTileTexture);
			System.out.println("make Corner");
		}else if(tileType.getType()==TileType.Goal) {
			tile.setMaterial(goalTileTexture);
		}
		
		return setTileOrien(tile,tileType);
	}
	
	private Box setTileOrien(Box tile, Placeable tileObject) {
		int orient = tileObject.getOrientation();
		Transform rotate =null;
		if(orient==1) {
			rotate=new Rotate(90, Rotate.Z_AXIS);
		}else if(orient==2) {
			rotate=new Rotate(180, Rotate.Z_AXIS);
		}else if(orient==3) {
			rotate=new Rotate(270, Rotate.Z_AXIS);
		}
		if(rotate!=null) {
		tile.getTransforms().add(rotate);
		}
		
		//System.out.println("Box ori: "+tile.getTransforms()+" Place ori: "+tileObject.getOrientation());
		return tile;
	}
	
	public Box makeSkipButton() {
		Box skipButton = new Box(100,50,3);
		PhongMaterial skipTexture = new PhongMaterial();
		skipTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/skipButton.png")));
		skipTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/skipButton.png")));
		skipButton.setMaterial(skipTexture);
		return skipButton;
	}
	
	public void textureTheTile(Box tile, Placeable tileType) {
		if(tileType.isOnFire()) {
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
	public Box makeActionTileInventory() {
		Box actionTileInventory = new Box(100,500,10);
		PhongMaterial Lcolor = new PhongMaterial(Color.BROWN);
		actionTileInventory.setMaterial(Lcolor);
		return actionTileInventory;
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
