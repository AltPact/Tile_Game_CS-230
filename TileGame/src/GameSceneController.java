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
import javafx.animation.ScaleTransition;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * File Name: GameSceneController.java Created: 07/11/2020 Modified: 10/11/2020
 * 
 * @author Wan Fai Tong (1909787) and Sam Steadman (1910177) Version: 1.0
 */
public class GameSceneController extends GameWindow implements Initializable {

	private static final int sceneWidth = 470;
	private static final int sceneHeight = 400;
	private static final int cameraX = 180;
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
	
	@FXML
	public BorderPane GB;
	@FXML
	public Button BackHomeButton;
	@FXML
	public Button QuitButton;

	/**
	 * This method initialize this page
	 * 
	 * @param url       The location of root object
	 * @param resources The resources to localize the root object
	 */
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		
		currentGameState=currentGame.getInitalGameState();
		initVariables();
		addFloor();
		addTile();
		initLightSource();
		addPlayer();
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
		
		int y = 0;
		for (int q = 0; q < boardHeight; q++) {
			int x = 0;
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
		for (int i = 0; i < 4; i++) {
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

	public static void getNewTile() {
		Tile newTile = currentGame.getNewTileForCurrentPlayer();
		
	}
	/*public static void setMoveableTile(int centerTileX, int centerTileY) {
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

	public static void resetClickable() {
		for (Box tile : clickAble) {
			tile.setOnMouseClicked(null);
			tile.scaleXProperty().set(1);
			tile.scaleYProperty().set(1);
		}
		clickAble.clear();
	}

	private static ScaleTransition animateTile(Box tile) {
		ScaleTransition enlarge = new ScaleTransition(Duration.millis(500), tile);
		enlarge.setToX(0.8);
		enlarge.setToY(0.8);
		enlarge.setCycleCount(Animation.INDEFINITE);
		enlarge.setAutoReverse(true);
		return enlarge;
	}

	public static void moveTile(double x, double y, Box moveTile) {
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(500), moveTile);
		tileMove.setToX(x);
		tileMove.setToY(y);
		tileMove.play();
	}
	*/
	

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
		ApplicationController.closeWindow();
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
