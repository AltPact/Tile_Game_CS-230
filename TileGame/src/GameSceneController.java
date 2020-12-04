import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.scene.PointLight;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * File Name: GameSceneController.java Created: 07/11/2020 Modified: 10/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class GameSceneController extends GameWindow implements Initializable {

	private static final int sceneWidth = 480;
	private static final int sceneHeight = 400;
	private static final int cameraX = 200;
	private static final int cameraY = 200;
	private static SubScene subScene;
	private static ObjFactory objectFactory;
	private static Group gameObjects;
	private static Box[][] tileArray;
	private static Group[] playerObjectArray;
	private static Group playerPlaying;
	private static HashMap<Sphere, PlayerPiece> playerPieceLink;
	private static ArrayList<Box> clickAble;
	private static ParallelTransition clickableAnima;
	private static ArrayList<ScaleTransition> scaleArray;
	private static Group tiles;
	
	private static GameState currentGameState;
	private static Placeable[][] tileBoard;
	private static int boardHeight;
	private static int boardWidth;
	private static int initPlayerPos[][];
	private static int currentPlayerPosX;
	private static int currentPlayerPosY;
	private static int turns=1;
	private static ArrayList<Box> tileInventory;
	private static Group inventory;
	private static Placeable activePlaceable;  // the floor tile drawn from the silk bag which must be placed by the current player (if they drew a floor tile)
	private static Box selectedTile;
	
	@FXML
	public BorderPane GB;
	@FXML
	public Button BackHomeButton;
	@FXML
	public Button QuitButton;
	@FXML
	public Label turnLabel;
	@FXML
	public ImageView playerIndicator;
	@FXML
	public Pane rightMenuPane;

	/**
	 * This method initialize this page
	 * 
	 * @param url       The location of root object
	 * @param resources The resources to localize the root object
	 */
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		currentGame = FileReaderWriterTest.generateTestGame();
		currentGameState=currentGame.getInitalGameState();
		initVariables();
		addFloor();
		addTile();
		initLightSource();
		addPlayer();
		setRightMenu();
		//gameObjects.getChildren().add(objectFactory.makeFireFly());
		// setScene
		subScene = new SubScene(gameObjects, sceneWidth, sceneHeight);
		setCamera();
		// addScene
		GB.setCenter(subScene);
		
		newTurn();
		
	}
	
	public void initLightSource() {
		gameObjects.getChildren().add(objectFactory.makeLightSource(100,100));
		gameObjects.getChildren().add(objectFactory.makeLightSource(500,300));
		gameObjects.getChildren().add(objectFactory.makeLightSource(100,600));
	}
	
	public void initVariables() {
		currentGame = FileReaderWriterTest.generateTestGame();
		tileBoard=currentGameState.getBoard();
		boardHeight=tileBoard.length;
		boardWidth=tileBoard[0].length;
		subScene = null;
		objectFactory= new ObjFactory();
		gameObjects = new Group();
		tileArray = new Box[9][9];
		playerObjectArray = new Group[4];
		playerPieceLink = new HashMap<Sphere, PlayerPiece>();
		playerPlaying=null;
		clickAble = new ArrayList<Box>();
		clickableAnima=null;
		scaleArray = new ArrayList<ScaleTransition>();
		tiles = new Group();
	}
    
	
	
	public void addFloor() {
		Box floor = objectFactory.makeFloor();
		floor.setTranslateX(420);
		floor.setTranslateY(400);
		gameObjects.getChildren().add(floor);
		System.out.println(floor.getTranslateZ());
	}
	public void addTile() {
		int y = 400-(boardHeight*100)/2;
		//System.out.println("Starting X: "+(400-boardWidth/2));
		//System.out.println("Starting Y: "+y);
		for (int q = 0; q < boardHeight; q++) {
			int x = 400-(boardWidth*100)/2;
			
			for (int i = 0; i < boardWidth; i++) {
				Box tile = objectFactory.makeTile(tileBoard[q][i]);
				tile.translateXProperty().set(x);
				tile.translateYProperty().set(y);
				tile.translateZProperty().set(0);
				tileArray[i][q] = tile;//box array
				tiles.getChildren().add(tile);//tile group
				x += 100;//gap between tiles
				//System.out.println(tile.getTranslateZ());
			}
			y += 100;//gap between tiles
		}
		gameObjects.getChildren().add(tiles);//add tile group to game group
	}

	public void addPlayer() {
		initPlayerPos=currentGameState.getPlayersPositions();
		//PlayerPiece pieceArray[] = currentGame.getPlayerPieceArray();
		for (int i = 0; i < 2; i++) {
			// put player pos in int
			int x = initPlayerPos[i][0];
			int y = initPlayerPos[i][1];
			Group player = objectFactory.makePlayer(i);
			player.setMouseTransparent(true);
			player.translateXProperty().set(tileArray[x][y].getTranslateX());
			player.translateYProperty().set(tileArray[x][y].getTranslateY());
			player.translateZProperty().set(-50);
			//playerPieceLink.put(sphere, pieceArray[i]);
			gameObjects.getChildren().add(player);
			playerObjectArray[i] = player;
		}
		// System.out.println(gameObjects.getChildren());
	}
	
	public void movePlayer(Group player, double x, double y) {
		TranslateTransition playerMove = new TranslateTransition(Duration.seconds(1),player);
		playerMove.setToX(x);
		playerMove.setToY(y);
		playerMove.play();
	}
	
	//Testing method
	/*public static void printPlayerHashMap() {
		for(Sphere player : playerPieceLink.keySet()) {
            if(playerPlaying==player) {
				System.out.println("Current player: ");
			}
			System.out.println("Player: "+player+" PlayerPiece: "+playerPieceLink.get(player));
			
		}
	}*/

	public void setCamera() {
		PerspectiveCamera camera = new PerspectiveCamera();
		camera.translateXProperty().set(cameraX);
		camera.translateYProperty().set(cameraY);
		camera.translateZProperty().set(-1200);
		subScene.setCamera(camera);
	}

	public static void newTurn() {
		// displayTurns();
		playerPlaying = playerObjectArray[currentGameState.getCurPlayer()];
		//printPlayerHashMap();
		//setPushable();
		// setMoveableTile(xCor, yCor);
	}
	
	/**
	 * pushes the current activePlaceable tile into the given row/column
	 * TODO: when setPushable is updated to animate opposite tiles, this will need to be updated with another paramater, the "direction" of the shift.
	 */
	public static void pushTile(int i, boolean vertical) {
		int newX;  // x/y of the tile being added
		int newY;
		int remX;  // x/y of the tile being pushed off the board
		int remY;
		if (vertical) {
			for (int y = boardHeight - 1; y > 0; y--) {
				tileArray[y][i] = tileArray[y-1][i];
			}
			remX = i;
			remY = boardHeight - 1;
			newX = i;
			newY = 0;
		} else {
			for (int x = boardWidth - 1; x > 0; x--) {
				tileArray[i][x] = tileArray[i][x-1];
			}
			remX = boardWidth - 1;
			remY = i;
			newX = 0;
			newY = i;
		}

		Box newBox = objectFactory.makeTile(activePlaceable);
		tileArray[newY][newX] = newBox;
		int displayX = (400-(boardWidth*100)/2) + (100 * newX);  //copied from addTile()
		int displayY = (400-(boardHeight*100)/2) + (100 * newY);
		newBox.translateXProperty().set(displayX);
		newBox.translateYProperty().set(displayY);
		newBox.translateZProperty().set(0);
		tiles.getChildren().remove(tileArray[remY][remX]);  // remove tile being pushed off the board from tiles, will no longer be drawn
		tiles.getChildren().add(newBox);  // add new tile to tiles to be drawn
	}

	/**
	 * animates and makes clickable all columns/rows that a tile can be pushed into
	 * TODO: also needs to animate and make clickable the tiles on the opposite side of the board. currently will only work for tiles on left/top sides
	 */
	public static void setPushable() {
		ArrayList<Box> pushableTiles = new ArrayList<Box>();
		boolean[][] insertablePlaces = currentGameState.getInsertableLocations();
		for (int x = 0; x < boardWidth; x++) { // for each column
			final int finalX = x;
			if (insertablePlaces[1][x]) {
				pushableTiles.add(tileArray[x][0]);
				tileArray[0][x].setOnMouseClicked(e -> {
					pushTile(finalX, true);
				});
			}
		}
		for (int y = 0; y < boardHeight; y++) {  // for each row
			final int finalY = y;
			if (insertablePlaces[0][y]) {
				pushableTiles.add(tileArray[y][0]);
				tileArray[y][0].setOnMouseClicked(e -> {
					pushTile(finalY, false);
				});
			}
		}
	}
	
	public static void setMoveableTiles() {
		boolean[][] moveableSpaces = currentGameState.getMoveableSpaces();
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				final int finalX = x;
				if(moveableSpaces[y][x]) {
					animateTile(tileArray[y][x]);
					tileArray[y][x].setOnMouseClicked(e -> {
						movePlayer(playerPlaying, finalY, finalX);
					});
					clickAble.add(tileArray[y][x]);
				}
			}
		}
	}
	
	public static void movePlayer(Group player, int y, int x) {
		try {
			currentGame.moveCurrentPlayer(x, y);
			TranslateTransition playerMove = new TranslateTransition(Duration.seconds(1),player);
			playerMove.setToX(x);
			playerMove.setToY(y);
			playerMove.play();
		} catch (IllegalMove e) {
			e.printStackTrace();
		}
		
	}
	
	private static ScaleTransition animateTile(Box tile) {
		ScaleTransition enlarge = new ScaleTransition(Duration.millis(500), tile);
		enlarge.setToX(0.8);
		enlarge.setToY(0.8);
		enlarge.setCycleCount(Animation.INDEFINITE);
		enlarge.setAutoReverse(true);
		return enlarge;
	}
	

	/*public static void getNewTile() {
		Tile newTile = currentGame.getNewTileForCurrentPlayer();
		
	}
	public static void setMoveableTile(int centerTileX, int centerTileY) {
		scaleArray = new ArrayList<ScaleTransition>();
		clickableAnima = new ParallelTransition();
		if (centerTileY > 0) {
			// top tile
			Box topTile = tileArray[centerTileX][centerTileY - 1];
			topTile.setOnMouseClicked(e -> {
				setPlayerPosition(playerPlaying, topTile, centerTileX, centerTileY - 1);
			});
			scaleArray.add(animateTile(topTile));
			clickAble.add(topTile);
		}
		if (centerTileX > 0) {
			// left tile
			Box leftTile = tileArray[centerTileX - 1][centerTileY];
			leftTile.setOnMouseClicked(e -> {
				setPlayerPosition(playerPlaying, leftTile, centerTileX - 1, centerTileY);
			});
			scaleArray.add(animateTile(leftTile));
			clickAble.add(leftTile);
		}
		if (centerTileX < 8) {
			// right tile
			Box rightTile = tileArray[centerTileX + 1][centerTileY];
			rightTile.setOnMouseClicked(e -> {
				setPlayerPosition(playerPlaying, rightTile, centerTileX + 1, centerTileY);
			});
			scaleArray.add(animateTile(rightTile));
			clickAble.add(rightTile);
		}
		if (centerTileY < 8) {
			// botton tile
			Box bottonTile = tileArray[centerTileX][centerTileY + 1];
			bottonTile.setOnMouseClicked(e -> {
				setPlayerPosition(playerPlaying, bottonTile, centerTileX, centerTileY + 1);
			});
			scaleArray.add(animateTile(bottonTile));
			clickAble.add(bottonTile);
		}
		clickableAnima = new ParallelTransition();
		for (ScaleTransition animation : scaleArray) {
			clickableAnima.getChildren().add(animation);
			System.out.println("Add animate");
		}
		clickableAnima.play();
	}*/


	/*public static void setPushable() {
		//clickableAnima = new ParallelTransition();<-animation
		clickAble = new ArrayList<Box>();
		boolean moveablePos[][]=currentGameState.getMoveableSpaces();
		currentPlayerPosX=initPlayerPos[currentGameState.getCurPlayer()][0];
		currentPlayerPosY=initPlayerPos[currentGameState.getCurPlayer()][1];
		
		for (int y = 0; y <= boardWidth; y++) {
			for(int x = 0; x<=boardHeight;x++) {
				if(moveablePos[y][x]==true) {
					Box pushableTile = tileArray[y][x];
					clickAble.add(pushableTile);
					int dir;
					if(currentPlayerPosY<y) {
						dir=0;
					}else if(currentPlayerPosX<x) {
						dir=1;
					}else if(currentPlayerPosY>y) {
						dir=2;
					}else {
						dir=3;
					}
					pushableTile.setOnMouseClicked(e -> {
						try {
							currentGameState=currentGame.moveCurrentPlayer(dir);
							
						} catch (IllegalMove e1) {
							System.out.println("Cannot move there");
						}
						
					});
					//clickableAnima.getChildren().add(animateTile(pushableTile));
				}
			}
		}*/
			/*if (rows == 0 || rows == 8) {
				for (int columns = 2; columns < 8; columns += 4) {
					Box pushableTile = tileArray[columns][rows];
					pushableTile.setDisable(false);
					clickAble.add(pushableTile);
					int r = rows;
					int c = columns;
					if (rows == 0) {
						pushableTile.setOnMouseClicked(e -> {
							pushTile(r, c, "downward");
						});
					} else {
						pushableTile.setOnMouseClicked(e -> {
							pushTile(r, c, "upward");
						});
					}
					clickableAnima.getChildren().add(animateTile(pushableTile));
				}
			}

			if (rows == 2 || rows == 6) {
				for (int columns = 0; columns <= 8; columns += 8) {
					Box pushableTile = tileArray[columns][rows];
					pushableTile.setDisable(false);
					clickAble.add(pushableTile);
					int r = rows;
					int c = columns;
					if (columns == 0) {
						pushableTile.setOnMouseClicked(e -> {
							pushTile(r, c, "right");
						});
					} else {
						pushableTile.setOnMouseClicked(e -> {
							pushTile(r, c, "left");
						});
					}
					clickableAnima.getChildren().add(animateTile(pushableTile));
				}
			}
		
		//clickableAnima.play();<-play animation
	}*/

	/*public static void pushTile(int rows, int columns, String orientation) {
		System.out.println("Rows: " + rows + " Columns: " + columns + " b: " + orientation);
		clickableAnima.stop();
		resetClickable();
		pushTileAnimation(rows, columns, orientation);
		int xCor = currentGame.getPlayingPlayerPiece().getX();
		int yCor = currentGame.getPlayingPlayerPiece().getY();
		setMoveableTile(xCor, yCor);
	}
	

	public static void pushTileAnimation(int rows, int columns, String orientation) {
		Box tileToRemove=null;
		int starting,ending,incValue,arrayX,arrayY;
		double X,Y;
		if (orientation.equals("upward") || orientation.equals("downward")) {
			
			if (orientation.equals("upward")) {
				starting=0;
				ending=8;
				incValue=1;
				arrayX=columns;
			}else {
				starting=8;
				ending=0;
				incValue=-1;
				arrayX=columns;
			}
			//System.out.println("S: " + starting + " ending: " + ending+ " inc: "+incValue);
			tileToRemove = tileArray[arrayX][starting];
			X = tileToRemove.getTranslateX();
			Y = tileToRemove.getTranslateY();
			for (int i = starting; i!= ending; i+=incValue) {
				System.out.println("R: "+i+" S: " + starting + " ending: " + ending+ " inc: "+incValue);
				Box newTile = tileArray[arrayX][(i+incValue)];
				moveTile(X, Y, newTile);
				tileArray[arrayX][i] = newTile;
				System.out.println("R: "+i);
				X = newTile.getTranslateX();
				Y = newTile.getTranslateY();
			}
		}else {
			if (orientation.equals("left")) {
				starting=0;
				ending=8;
				incValue=1;
				arrayY=rows;
			}else {
				starting=8;
				ending=0;
				incValue=-1;
				arrayY=rows;
			}
			//System.out.println("S: " + starting + " ending: " + ending+ " inc: "+incValue);
			tileToRemove = tileArray[starting][arrayY];
			X = tileToRemove.getTranslateX();
			Y = tileToRemove.getTranslateY();
			for (int i = starting; i!= ending; i+=incValue) {
				System.out.println("R: "+i+" S: " + starting + " ending: " + ending+ " inc: "+incValue);
				Box newTile = tileArray[(i+incValue)][arrayY];
				moveTile(X, Y, newTile);
				tileArray[i][arrayY] = newTile;
				System.out.println("R: "+i);
				X = newTile.getTranslateX();
				Y = newTile.getTranslateY();
			}
		}
		// vertical
		
		tiles.getChildren().remove(tileToRemove);
		pushNewTile(X,Y,rows,columns);
	}
	
	public static Box pushNewTile(double x, double y,int rows, int columns) {
		Box newTile =objectFactory.makeTTile();
		newTile.setTranslateX(1000);
		newTile.setTranslateY(600);
		newTile.setTranslateZ(0);
		tiles.getChildren().add(newTile);
		moveTile(x,y,newTile);
		tileArray[columns][rows]=newTile;
		return newTile;
	}

	public static void setPlayerPosition(Sphere player, Box tile, int x, int y) {
		clickableAnima.stop();
		scaleArray = new ArrayList<ScaleTransition>();
		// set Game class value
		currentGame.setPlayerPiece(playerPieceLink.get(player), x, y);
		TranslateTransition playerMove = new TranslateTransition(Duration.seconds(1));
		playerMove.setNode(player);
		playerMove.setToX(tile.getTranslateX());
		playerMove.setToY(tile.getTranslateY());
		playerMove.play();
		// reset button
		resetClickable();
		currentGame.newTurns();
		newTurn();
	}
*/
	public static void resetClickable() {
		for (Box tile : clickAble) {
			tile.setOnMouseClicked(null);
			tile.scaleXProperty().set(1);
			tile.scaleYProperty().set(1);
		}
		clickAble.clear();
	}



	

	public static void moveTile(double x, double y, Box moveTile) {
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(500), moveTile);
		tileMove.setToX(x);
		tileMove.setToY(y);
		tileMove.play();
	}

	/*
	 * public static void displayTurns() { //incomplete Label numOfTurns = new
	 * Label("Turns: "+Integer.toString(currentGame.getTurns()));
	 * numOfTurns.setStyle("-fx-text-fill: White; -fx-background-color: black;");
	 * numOfTurns.setAlignment(Pos.CENTER); numOfTurns.setMinWidth(400);
	 * numOfTurns.setMinHeight(50); numOfTurns.setFont(new Font(50));
	 * numOfTurns.setLayoutX(200); numOfTurns.setLayoutY(350);
	 * gameObjects.getChildren().add(numOfTurns); FadeTransition turnsFade = new
	 * FadeTransition(Duration.seconds(2),numOfTurns); turnsFade.setFromValue(0);
	 * turnsFade.setToValue(1.0); turnsFade.setCycleCount(2);
	 * turnsFade.setAutoReverse(true); turnsFade.play();
	 * gameObjects.getChildren().remove(numOfTurns); }
	 */
	/*public static Sphere getPlayingPlayer() {
		return playerObjectArray[currentGame.getPlayingPlayerIndex()];
	}

	public static void colorTheTile() {
		PhongMaterial greenFill = new PhongMaterial(Color.GREEN);
		for (Box tile : clickAble) {
			tile.setMaterial(greenFill);
		}
	}*/

	public void animationPlayTile(double x, double y,String playerType) {
		Box elementType = new Box(200,200,2);
		elementType.translateXProperty().set(x);
		elementType.translateYProperty().set(y);
		elementType.translateZProperty().set(-55);
		elementType.setMouseTransparent(true);
		Cylinder magicCircle = new Cylinder(200,1);
		magicCircle.translateXProperty().set(x);
		magicCircle.translateYProperty().set(y);
		magicCircle.translateZProperty().set(-50);
		Transform rotateMC = new Rotate(90, Rotate.X_AXIS);
		magicCircle.getTransforms().add(rotateMC);
		magicCircle.setMouseTransparent(true);
		
		PhongMaterial imageTextureElement = new PhongMaterial();
		PhongMaterial imageTextureMC = new PhongMaterial();
		if(playerType.equals("fire")) {
		    imageTextureElement.setDiffuseMap(new Image("fireMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("fireMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("fireMagicCircle.png"));
		}else if(playerType.equals("ice")){
			imageTextureElement.setDiffuseMap(new Image("frozenMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("iceMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("iceMagicCircle.png"));
		}else if(playerType.equals("wind")){
			imageTextureElement.setDiffuseMap(new Image("windMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("windMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("windMagicCircle.png"));
		}else{
			imageTextureElement.setDiffuseMap(new Image("backTrackMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("portalMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("portalMagicCircle.png"));
		}
		elementType.setMaterial(imageTextureElement);
		magicCircle.setMaterial(imageTextureMC);
		
		
		ScaleTransition elementFade = new ScaleTransition(Duration.millis(200),elementType);    
		elementFade.setFromX(0);
		elementFade.setFromY(0);
		elementFade.setToX(1);
		elementFade.setToY(1);

		
		ScaleTransition mcFade = new ScaleTransition(Duration.millis(1000),magicCircle);    
		mcFade.setFromX(0);
		mcFade.setFromY(0);
		mcFade.setToX(1);
		mcFade.setToY(1);
		
		RotateTransition mcRotate = new RotateTransition(Duration.millis(1000),magicCircle);  
		mcRotate.setByAngle(360);
		
		ParallelTransition mcTrans = new ParallelTransition(elementFade,mcFade,mcRotate);
		
		PauseTransition hold = new PauseTransition(Duration.millis(1000));
		
		ScaleTransition mcBackFade = new ScaleTransition(Duration.millis(500),magicCircle);    
		mcBackFade.setFromX(1);
		mcBackFade.setFromY(1);
		mcBackFade.setToX(0);
		mcBackFade.setToY(0);
		
		ScaleTransition elementBackFade = new ScaleTransition(Duration.millis(200),elementType);    
		elementBackFade.setFromX(1);
		elementBackFade.setFromY(1);
		elementBackFade.setToX(0);
		elementBackFade.setToY(0);
		
		ParallelTransition mcBackTrans = new ParallelTransition(mcBackFade,elementBackFade);
		
		SequentialTransition seqTransition = new SequentialTransition(mcTrans,hold,mcBackTrans);
		seqTransition.setOnFinished(e->{
			gameObjects.getChildren().removeAll(elementType,magicCircle);
		});
		
		seqTransition.play();
		gameObjects.getChildren().addAll(elementType,magicCircle);
		
	
}
	
	private void setRightMenu() {
		turnLabel.setText(Integer.toString(turns));
		TranslateTransition turnLabelMove = new TranslateTransition(Duration.millis(300), turnLabel);
		turnLabelMove.setFromY(-50);
		turnLabelMove.setToY(0);
		turnLabelMove.play();
		
		showCurPlayer();
		currentGameState=currentGame.getNewTileForCurrentPlayer();
		Tile drawTile=currentGameState.getTileDrawn();
		
		showDrawTile(drawTile);
		showInventory();
	}
	
	public void showInventory() {
		selectedTile=null;
		boolean actionTileObtained[]= {false,false,false,false};
		inventory = new Group();
		tileInventory = new ArrayList<Box>();
		Box inventoryBase = new Box(90,500,10);
		PhongMaterial baseTexture = new PhongMaterial(Color.BROWN);
		inventoryBase.setMaterial(baseTexture);
		inventory.setTranslateY(400);
		inventory.setTranslateZ(-50);
		inventory.getChildren().add(inventoryBase);
		ArrayList<ActionTile> tilesOwned = currentGameState.getActionTileForPlayer(currentGameState.getCurPlayer());
		double y = -200;
		
		for(ActionTile actionTile:tilesOwned) {
			if(actionTile.getType()==TileType.Fire) {
				actionTileObtained[0]=true;
			}else if(actionTile.getType()==TileType.Ice) {
				actionTileObtained[1]=true;
			}else if(actionTile.getType()==TileType.DoubleMove) {
				actionTileObtained[2]=true;
			}else if(actionTile.getType()==TileType.BackTrack) {
				actionTileObtained[3]=true;
			}
		}
		int counter=0;
		for(boolean tileObtained:actionTileObtained) {
			if(tileObtained) {
			Box acTile = objectFactory.makeTileInInventory(counter);
			acTile.setTranslateX(inventoryBase.getTranslateX()-10);
			acTile.setTranslateY(y);
			acTile.setTranslateZ(inventoryBase.getTranslateZ()-50);
			acTile.setOnMouseClicked(e->{
				if(selectedTile!=acTile) {
					setSelectedTile(acTile);
				}else {
					acTile.setRotate(acTile.getRotate()+90);
				}
			});
			tileInventory.add(acTile);
			inventory.getChildren().add(acTile);
			y+=70;
			}
			counter++;
		}
		gameObjects.getChildren().add(inventory);
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(1000), inventory);
		tileMove.setFromX(1200);
		tileMove.setToX(950);
		tileMove.play();

	}
	
	private void hideInventory() {
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(1000), inventory);
		tileMove.setFromX(950);
		tileMove.setToX(1200);
		tileMove.setOnFinished(e->{
			gameObjects.getChildren().remove(inventory);
		});
		tileMove.play();
		
	}
	
	private void setSelectedTile(Box newSelected) {
		if(selectedTile!=null) {
		   selectedTile.setWidth(70);
		   selectedTile.setHeight(70);
		}
		newSelected.setWidth(90);
		newSelected.setHeight(90);
		selectedTile=newSelected;
	}
	
	private void showCurPlayer() {
		int curPlayerNum = currentGameState.getCurPlayer();
		if(curPlayerNum==0) {
			playerIndicator.setImage(new Image("/img/redWizard.png"));
		}else if(curPlayerNum==1) {
			playerIndicator.setImage(new Image("/img/yellowWizard.png"));
		}else if(curPlayerNum==2) {
			playerIndicator.setImage(new Image("/img/blueWizard.png"));
		}else{
			playerIndicator.setImage(new Image("/img/purpleWizard.png"));
		}
		TranslateTransition changePlayer = new TranslateTransition(Duration.seconds(0.8),playerIndicator);
		changePlayer.setFromX(50);
		changePlayer.setToX(0);
		changePlayer.play();
	}
	
	private void showDrawTile(Tile drawTile) {
		Image tileImage=null;
		if(drawTile.getType()==TileType.Straight) {
			tileImage = new Image("/img/texture/Straight.png");
		}else if(drawTile.getType()==TileType.Corner) {
			tileImage = new Image("/img/texture/Corner.png");
		}else if(drawTile.getType()==TileType.TShaped) {
			tileImage = new Image("/img/texture/Corner.png");
		}else if(drawTile.getType()==TileType.Fire) {
			tileImage = new Image("/img/texture/fireTile.jpg");
		}else if(drawTile.getType()==TileType.Ice) {
			tileImage = new Image("/img/texture/IceTile.jpg");
		}else if(drawTile.getType()==TileType.BackTrack) {
			tileImage = new Image("/img/texture/backTrackTile.jpg");
		}else if(drawTile.getType()==TileType.DoubleMove) {
			tileImage = new Image("/img/texture/doubleMoveTile.jpg");
		}
		Label tileType = showTileType(drawTile);
		ImageView tileIcon = new ImageView();
		tileIcon.setImage(tileImage);
		tileIcon.setFitHeight(50);
		tileIcon.setFitWidth(50);
		tileIcon.setLayoutX(42);
		tileIcon.setLayoutY(225);
		rightMenuPane.getChildren().add(tileIcon);
		ScaleTransition appearTile = new ScaleTransition(Duration.seconds(1),tileIcon);
		appearTile.setFromX(0);
		appearTile.setFromY(0);
		appearTile.setToX(1);
		appearTile.setToY(1);
		appearTile.setOnFinished(e->{
			rightMenuPane.getChildren().add(tileType);
		});
		PauseTransition hold = new PauseTransition(Duration.millis(1000));
		hold.setOnFinished(e->{
			rightMenuPane.getChildren().remove(tileType);
		});
		
		ScaleTransition disappearTile = new ScaleTransition(Duration.seconds(1),tileIcon);
		disappearTile.setFromX(1);
		disappearTile.setFromY(1);
		disappearTile.setToX(0);
		disappearTile.setToY(0);
		SequentialTransition showTileSeq = new SequentialTransition(appearTile,hold,disappearTile);
		showTileSeq.play();
	}
	
	public Label showTileType(Tile drawTile) {
		Label typeType = new Label(drawTile.getType().name());
		typeType.setTextFill(Color.WHITE);
		typeType.setAlignment(Pos.CENTER);
		typeType.setLayoutX(37);
		typeType.setLayoutY(205);
		return typeType;
	}

	
	/**
	 * This method is called when the back button is click it will call the
	 * switchPane() method which switch to HomePagePane
	 * 
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionB(ActionEvent event) throws IOException {
		switchPane("/fxml/HomePagePane.fxml", GB, "back");
	}

	/**
	 * This method is called when the quit button is click it will close the
	 * application window
	 * 
	 * @param event The action event
	 */
	@FXML
	public void buttonOnActionQ(ActionEvent event) throws IOException {
		ApplicationController.closeWindow(this.getCurrentGame());
	}

	/**
	 * This method is called when mouse is on the back button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnB() {
		BackHomeButton.setStyle("-fx-background-color: GOLDENROD; -fx-background-radius: 5px;");
	}

	/**
	 * This method is called when mouse is off the back button it will change the
	 * color of the button back
	 */
	@FXML
	public void mouseOFFB() {
		BackHomeButton.setStyle("-fx-background-color: BLACK; -fx-background-radius: 5px; -fx-border-color: GOLDENROD; -fx-border-radius: 5px; -fx-border-width: 2;");
	}

	/**
	 * This method is called when mouse is on the quit button it will change the
	 * color of the button
	 */
	@FXML
	public void mouseOnQ() {
		QuitButton.setStyle("-fx-background-color: GOLDENROD; -fx-background-radius: 5px;");
	}

	/**
	 * This method is called when mouse is off the quit button it will change the
	 * color of the button back
	 */
	@FXML
	public void mouseOFFQ() {
		QuitButton.setStyle("-fx-background-color: BLACK; -fx-background-radius: 5px; -fx-border-color: GOLDENROD; -fx-border-radius: 5px; -fx-border-width: 2;");
	}

}
