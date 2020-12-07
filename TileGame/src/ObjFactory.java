import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
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

/**
 * A class used to construct textured 3D objects that can be drawn to the screen by GameSceneController
 *
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177)
 */
public class ObjFactory {
	private static final int TILE_LENGTH =90;
	private static final int TILE_DEPTH =15;
	private static final int BOARD_LENGTH =1200;
	private static final int BOARD_DEPTH =25;

	/* initialising the materials to be used for 3D models */
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

	/**
	 * Constructor, initialises the textures for all materials
	 */
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

	/**
	 * Sets the texture for the T-Shaped Tile's material
	 */
	private void initTTileTexture() {
		tTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/TShaped.png")));
	}

	/**
	 * Sets the texture for the Corner Tile's material
	 */
	private void initCornerTileTexture() {
		cornerTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Corner.png")));
	}

	/**
	 * Sets the texture for the Straight Tile's material
	 */
	private void initStraightTileTexture() {
		straightTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Straight.png")));
	}

	/**
	 * Sets the texture for the Goal Tile's material
	 */
	private void initGoalTileTexture() {
		goalTileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Goal.jpg")));
	}

	/**
	 * Sets the texture for the background's material
	 */
	private void initFloorTexture() {
		boardTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Garden.jpg")));
	}

	/**
	 * Sets the texture for the Fixed Corner Tile's material
	 */
	private void initFixedCornerTexture() {
		fixedCornerTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FixedCorner.jpg")));
	}

	/**
	 * Sets the texture for the Fixed T-Shaped Tile's material
	 */
	private void initFixedTTexture() {
		fixedTTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FixedT.jpg")));
	}

	/**
	 * Sets the texture for the Fixed Straight Tile's material
	 */
	private void initFixedStraightTexture() {
		fixedStraightTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FixedStraight.jpg")));
	}

	/**
	 * Sets the texture for the Frozen Corner Tile's material
	 */
	private void initFrozenCornerTexture() {
		frozenCornerTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FrozenCorner.jpg")));
	}

	/**
	 * Sets the texture for the Frozen T-Shaped Tile's material
	 */
	private void initFrozenTTexture() {
		frozenTTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FrozenTShaped.jpg")));
	}

	/**
	 * Sets the texture for the Frozen Straight Tile's material
	 */
	private void initFrozenStraightTexture() {
		frozenStraightTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/FrozenStraight.jpg")));
	}

	/**
	 * Sets the texture for the Fire Tile's material
	 */
	private void initFireTexture() {
		fireTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/fire.jpg")));
	}

	/**
	 * Sets the textures for the arrows' materials
	 */
	private void initArrowTextures() {
		arrowUpTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-up.png")));
		arrowDownTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-down.png")));
		arrowLeftTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-left.png")));
		arrowRightTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/arrow-right.png")));
	}

	/**
	 * Create a 3D model of a Tile to be displayed in your inventory
	 * @param tileType The type of tile to model
	 * @return A textured box resembling the specified TileType
	 */
	public Box makeTileInInventory(TileType tileType) {
		Box tile = new Box(70,70,10);
		PhongMaterial tileTexture = new PhongMaterial();
		if(tileType==TileType.Fire) {//fire
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/fireTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/fireTile.jpg")));
		}else if(tileType==TileType.Ice) {//ice
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/IceTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/IceTile.jpg")));
		}else if(tileType==TileType.DoubleMove) {//doubleMove
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/doubleMoveTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/doubleMoveTile.jpg")));
		}else if(tileType==TileType.BackTrack) {//backtrack
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/backTrackTile.jpg")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/backTrackTile.jpg")));
		}else if(tileType==TileType.Corner) {//corner
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Corner.png")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/Corner.png")));
		}else if(tileType==TileType.Straight) {//straight
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/Straight.png")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/Straight.png")));
		}else if(tileType==TileType.TShaped) {//t-shaped
			tileTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/TShaped.png")));
			tileTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/TShaped.png")));
		}
		tile.setMaterial(tileTexture);
		return tile;
	}

	/**
	 * Constructs a textured arrow box that will indicate where a tile can be pushed into the board
	 * @param orientation the orientation of the arrow (up, down, left, right)
	 * @return a textured box that resembles an arrow in the specified direction
	 */
	public Box makeArrow(int orientation) {
		Box arrow = new Box(TILE_LENGTH, TILE_LENGTH, TILE_DEPTH);
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

	/**
	 * Creates a 3D model of a Tile to be displayed on the board
	 * @param tileType The type of tile to model
	 * @return A textured box resembling the TileType specified
	 */
	public Box makeTile(Placeable tileType) {
		Box tile = new Box(TILE_LENGTH, TILE_LENGTH, TILE_DEPTH);
		
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
		   // System.out.println("make TShaped");
		}else if(tileType.getType()==TileType.Straight) {
			tile.setMaterial(straightTileTexture);
			//System.out.println("make Straight");
		}else if(tileType.getType()==TileType.Corner) {
			tile.setMaterial(cornerTileTexture);
			//System.out.println("make Corner");
		}else if(tileType.getType()==TileType.Goal) {
			tile.setMaterial(goalTileTexture);
		}
		
		return setTileOrien(tile,tileType);
	}

	/**
	 * Rotates a given box to match the orientation of a given Placeable object
	 * @param tile The box to rotate
	 * @param tileObject The tile's orientation to be copied
	 * @return A 3D box rotated to the orientation of the Placeable supplied
	 */
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
		return tile;
	}

	/**
	 * Creates a 3D model of a skip button to be drawn to the screen
	 * @return A box resembling a skip button
	 */
	public Box makeSkipButton() {
		Box skipButton = new Box(100,50,3);
		PhongMaterial skipTexture = new PhongMaterial();
		skipTexture.setDiffuseMap(new Image(getClass().getResourceAsStream("/img/texture/skipButton.png")));
		skipTexture.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/img/texture/skipButton.png")));
		skipButton.setMaterial(skipTexture);
		return skipButton;
	}

	/**
	 * Updates the texture of a given box to match the state of the Placeable supplied
	 * @param tile The box to re-texture
	 * @param tileType The Placeable to model
	 */
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

	/**
	 * Creates a 3D textured box to form the base of the board
	 * @return A box with a grass texture to be shown behind the board
	 */
	public Box makeFloor() {
		Box floor = new Box(BOARD_LENGTH, BOARD_LENGTH, BOARD_DEPTH);
		floor.setMaterial(boardTexture);
		return floor;
	}

	/**
	 * Creates a 3D player model, its colour depending on the playerIndex specified
	 * @param playerIndex the player's number, which determines their colour; 0= red, 1= yellow, 2= green, 3= blue
	 * @return a 3D player model to be drawn on the board
	 */
	public Group makePlayer(int playerIndex) {
		Group wholeBody = new Group();
		Group wholeHat = new Group();
		Group wand = new Group();
		double bodyWidth=30;
		Box body = colorLegs(new Box(50,bodyWidth,60));
		Box leftLeg = colorLegs(new Box((body.getWidth()/2)-5, bodyWidth ,body.getDepth()));
		Box rightLeg = colorLegs(new Box((body.getWidth()/2)-5, bodyWidth ,body.getDepth()));
		Box hat = new Box(25,25,25);
		Box hatFlatPlate = new Box(70,70,5);
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

	/**
	 * Colours a player model's hat according to their player index
	 * @param hat The top of the hat
	 * @param hatPlate The base of the hat
	 * @param playerIndex The owner of the hat, determining the colour; 0= red, 1= yellow, 2= green, 3= blue
	 * @return A group containing the re-coloured top and base of the hat
	 */
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


	/**
	 * Colours a player model's wand
	 * @param c The "base" of the wand to be coloured gray
	 * @param g The "orb" of the wand to be coloured cyan
	 * @return A group containing both re-coloured wand elements
	 */
	public Group colorWand(Cylinder c, Sphere g) {
		Group wand = new Group();
		PhongMaterial Gcolor = new PhongMaterial(Color.CYAN);
		PhongMaterial Ccolor = new PhongMaterial(Color.GRAY);
		g.setMaterial(Gcolor);
		c.setMaterial(Ccolor);
		wand.getChildren().addAll(c,g);
		return wand;
	}

	/**
	 * Colours a player model's leg
	 * @param leg The leg to colour
	 * @return The coloured leg
	 */
	public Box colorLegs(Box leg) {
		PhongMaterial Lcolor = new PhongMaterial(Color.BLACK);
		leg.setMaterial(Lcolor);
		return leg;
	}

	/**
	 * Creates a light source to be displayed above the board, accompanied by a transparent firefly texture
	 * @param x The desired x-coordinate of the light source
	 * @param y The desired y-coordinate of the light source
	 * @return A group containing the light source and firefly texture, translated to the given x/y
	 */
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
	/* TODO: DELETE THIS
	public Box makeActionTileInventory() {
		Box actionTileInventory = new Box(100,500,1);
		PhongMaterial Lcolor = new PhongMaterial(Color.BROWN);
		actionTileInventory.setMaterial(Lcolor);
		return actionTileInventory;
	}*/

	/**
	 * Sets the animation of a firefly texture to pulse gently
	 * @param firefly The firefly texture to animate
	 */
	public void setAnimation(ImageView firefly) {
		ScaleTransition fireFlyMoving = new ScaleTransition(Duration.millis(500), firefly);
		fireFlyMoving.setByX(firefly.getScaleX()*0.05);
		fireFlyMoving.setByY(firefly.getScaleY()*0.05);
		fireFlyMoving.setCycleCount(Animation.INDEFINITE);
		fireFlyMoving.setAutoReverse(true);
		fireFlyMoving.play();
	}
}
