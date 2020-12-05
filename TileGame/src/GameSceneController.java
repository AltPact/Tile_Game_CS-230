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
	private static Group arrows;
	
	private static GameState currentGameState;
	private static Placeable[][] tileBoard;
	private static int boardHeight;
	private static int boardWidth;
	private static int initPlayerPos[][];
	private static int currentPlayerPosX;
	private static int currentPlayerPosY;
	private static int turns=1;
	private static ArrayList<Box> tileInventory;
	private static ArrayList<ActionTile> actionTilesOwned;
	private static Group inventory;
	private static Placeable activePlaceable;  // the floor tile drawn from the silk bag which must be placed by the current player (if they drew a floor tile)
	private static Box selectedTile;
	private static int phase;
	private static ParallelTransition clickableAnime;
	private static Label turnLabel;
	private static ImageView playerIndicator;
	@FXML
	public BorderPane GB;
	@FXML
	public Button BackHomeButton;
	@FXML
	public Button QuitButton;
	@FXML
	public Pane rightMenuPane;

	public static Pane sRightMenuPane;
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
		sRightMenuPane=rightMenuPane;
		//gameObjects.getChildren().add(objectFactory.makeFireFly());
		// setScene
		subScene = new SubScene(gameObjects, sceneWidth, sceneHeight);
		setCamera();
		// addScene
		GB.setCenter(subScene);
		
		newTurn();
		//pushTileAnimation(0,2,0);
	}
	

	private static void newTurn() {
		// displayTurns();
		
		playerPlaying = playerObjectArray[currentGameState.getCurPlayer()];
		phase=1;
		displayTurns();
		
		setRightMenu();
	}
	
	private static void checkWin() {
		updateGameState();
		if(!currentGameState.getIsGoalHit()) {
			turns++;
			newTurn();
		}
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
		//System.out.println("board used in gamescene:");
		for(int a = 0; a < boardHeight; a++) {
			for(int b = 0; b < boardWidth; b++) {
				//System.out.print(tileBoard[a][b].getType() + " ");
			}
			//System.out.println("");
		}
		subScene = null;
		objectFactory= new ObjFactory();
		gameObjects = new Group();
		tileArray = new Box[boardHeight][boardWidth];
		playerObjectArray = new Group[4];
		playerPieceLink = new HashMap<Sphere, PlayerPiece>();
		playerPlaying=null;
		clickAble = new ArrayList<Box>();
		clickableAnima=null;
		scaleArray = new ArrayList<ScaleTransition>();
		tiles = new Group();
		arrows = new Group();
	}
    
	
	
	public void addFloor() {
		Box floor = objectFactory.makeFloor();
		floor.setTranslateX(420);
		floor.setTranslateY(400);
		gameObjects.getChildren().add(floor);
		//System.out.println(floor.getTranslateZ());
	}

	private static void updateGameState() {
		currentGameState=currentGame.getCurrentGameState();
	}
	
	/**
	 * add all tiles at start of game
	 */
	public void addTile() {
		int y = 400-(boardHeight*100)/2;
		//System.out.println("Starting X: "+(400-boardWidth/2));
		//System.out.println("Starting Y: "+y);
		for (int h = 0; h < boardHeight; h++) {
			int x = 400-(boardWidth*100)/2;
			
			for (int w = 0; w < boardWidth; w++) {
				Box tile = objectFactory.makeTile(tileBoard[h][w]);
				tile.translateXProperty().set(x);
				tile.translateYProperty().set(y);
				tile.translateZProperty().set(0);
				tileArray[h][w] = tile;//box array
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
		for (int i = 0; i < initPlayerPos.length; i++) {
			// put player pos in int
			int x = initPlayerPos[i][0];
			int y = initPlayerPos[i][1];
			Group player = objectFactory.makePlayer(i);
			player.setMouseTransparent(true);
			player.translateXProperty().set(tileArray[y][x].getTranslateX());
			player.translateYProperty().set(tileArray[y][x].getTranslateY());
			player.translateZProperty().set(-50);
			//playerPieceLink.put(sphere, pieceArray[i]);
			gameObjects.getChildren().add(player);
			playerObjectArray[i] = player;
		}
		// System.out.println(gameObjects.getChildren());
	}

	
	private static void updateBoard() {
		updateGameState();
		Placeable[][] newBoard=currentGameState.getBoard();
		for(int h=0;h<boardHeight;h++) {
			for(int w=0;w<boardWidth;w++) {
				objectFactory.textureTheTile(tileArray[h][w],newBoard[h][w]);
			}
		}
	}
	
	private static void updatePlayerPosition() {
		int[][] playerPosition=currentGameState.getPlayersPositions();
		for(int playerNum=0;playerNum<playerPosition.length;playerNum++) {
			int x=playerPosition[playerNum][1];
			int y=playerPosition[playerNum][0];
			System.out.println("Player Position X: "+x+" Y: "+y);
			Box onTile =tileArray[x][y];
			movePlayer(playerObjectArray[playerNum],onTile.getTranslateX(),onTile.getTranslateY());
		    }
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


	/**
	 * updates the x/y/z positions of all tiles so that they are drawn in the correct place on the screen.
	 */
	public static void updateTileTranslations() {
		int y = 400-(boardHeight*100)/2;
		for (int q = 0; q < boardHeight; q++) {
			int x = 400-(boardWidth*100)/2;
			for (int i = 0; i < boardWidth; i++) {
				tileArray[q][i].translateXProperty().set(x);
				tileArray[q][i].translateYProperty().set(y);
				tileArray[q][i].translateZProperty().set(0);
				x += 100;//gap between tiles
			}
			y += 100;//gap between tiles
		}
	}

	/**
	 * pushes the current activePlaceable tile into the given row/column
	 * TODO: needs to remove arrows
	 * TODO: when setPushable is updated to animate opposite tiles, this will need to be updated with another paramater, the "direction" of the shift.
	 */
	/**
	 *
	 * @param direction the direction to push to tile in from (top/bottom/left/right)
	 */
	/*public static void pushTile(int i, int direction) {
		
		System.out.println("current active tile: " +activePlaceable.getType());
		arrows.getChildren().removeAll();  // remove all arrows now that one has been clicked
		int newX;  // x/y of the tile being added
		int newY;
		int remX;  // x/y of the tile being pushed off the board
		int remY;
		if (direction == 0) {  // top
			tiles.getChildren().remove(tileArray[boardHeight - 1][i]);
			for (int y = boardHeight - 1; y > 0; y--) {
				tileArray[y][i] = tileArray[y-1][i];
			}
			newX = i;
			newY = 0;
		} else if (direction == 2) {  // left
			tiles.getChildren().remove(tileArray[i][boardWidth - 1]);
			for (int x = boardWidth - 1; x > 0; x--) {
				tileArray[i][x] = tileArray[i][x-1];
			}
			newX = 0;
			newY = i;
		} else if (direction == 1) {  // bottom
			tiles.getChildren().remove(tileArray[0][i]);
			for (int y = 0; y < (boardHeight - 1); y++) {
				tileArray[y][i] = tileArray[y+1][i];
			}
			newX = i;
			newY = boardHeight - 1;
		} else{  // assume direction == 3, right
			tiles.getChildren().remove(tileArray[i][0]);
			for (int x = 0; x < (boardWidth - 1); x++) {
				tileArray[i][x] = tileArray[i][x+1];
			}
			newX = boardWidth - 1;
			newY = i;
		}

		Box newBox = objectFactory.makeTile(activePlaceable);
		tileArray[newY][newX] = newBox;
		int displayX = (400-(boardWidth*100)/2) + (100 * newX);  //copied from addTile()
		int displayY = (400-(boardHeight*100)/2) + (100 * newY);
		newBox.translateXProperty().set(displayX);
		newBox.translateYProperty().set(displayY);
		newBox.translateZProperty().set(0);
		tiles.getChildren().add(newBox);  // add new tile to tiles to be drawn
		updateBoard();
	}
    */
	/**
	 * creates arrows next to all free rows/columns that can be clicked by the player to insert a tile into that row/column
	 */
	public static void setPushableArrows() {
		ArrayList<Box> pushableTiles = new ArrayList<Box>();
		boolean[][] insertablePlaces = currentGameState.getInsertableLocations(); // [rows, columns][index]
		for (int x = 0; x < boardWidth; x++) { // for each column
			final int finalX = x;
			if (insertablePlaces[0][x]) {
				pushableTiles.add(tileArray[0][x]);
				System.out.println("0 "+x);
				Box newDownArrow = objectFactory.makeArrow(1);  // make down arrow
				System.out.println(tileArray[0][x].getTranslateX()+" "+tileArray[0][x].getTranslateY());
				newDownArrow.translateXProperty().set(tileArray[0][x].getTranslateX());  // same x
				newDownArrow.translateYProperty().set(tileArray[0][x].getTranslateY() - 100);  // -100y
				newDownArrow.translateZProperty().set(0);
				arrows.getChildren().add(newDownArrow);
				newDownArrow.setOnMouseClicked(e -> {
					try {
						System.out.println(activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable,finalX,0,true);
						updateGameState();
						pushTileAnimation(finalX,0,1);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}
					
				});

				pushableTiles.add(tileArray[boardHeight-1][x]);
				Box newUpArrow = objectFactory.makeArrow(0);  // make up arrow
				newUpArrow.translateXProperty().set(tileArray[boardHeight-1][x].translateXProperty().get());  // same x
				newUpArrow.translateYProperty().set(tileArray[boardHeight-1][x].translateYProperty().get() + 100);  // +100y
				newUpArrow.translateZProperty().set(0);
				arrows.getChildren().add(newUpArrow);
				newUpArrow.setOnMouseClicked(e -> {
					try {
						System.out.println(activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable,finalX,boardHeight-1,true);
						updateGameState();
						pushTileAnimation(finalX,boardHeight-1,0);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}
					
				});
			}
		}
		for (int y = 0; y < boardHeight; y++) {  // for each row
			final int finalY = y;
			if (insertablePlaces[1][y]) {
				pushableTiles.add(tileArray[y][0]);
				Box newRightArrow = objectFactory.makeArrow(3);  // make right arrow
				newRightArrow.translateXProperty().set(tileArray[y][0].translateXProperty().get() - 100);  // -100x
				newRightArrow.translateYProperty().set(tileArray[y][0].translateYProperty().get());  // same y
				newRightArrow.translateZProperty().set(0);
				arrows.getChildren().add(newRightArrow);
				newRightArrow.setOnMouseClicked(e -> {
					try {
						System.out.println(activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable,0,finalY,false);
						updateGameState();
						pushTileAnimation(0,finalY,3);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}
				});

				pushableTiles.add(tileArray[y][boardWidth-1]);
				Box newLeftArrow = objectFactory.makeArrow(2);  // make left arrow
				newLeftArrow.translateXProperty().set(tileArray[y][boardWidth - 1].translateXProperty().get() + 100);  // +100x
				newLeftArrow.translateYProperty().set(tileArray[y][boardWidth - 1].translateYProperty().get());  // same y
				newLeftArrow.translateZProperty().set(0);
				arrows.getChildren().add(newLeftArrow);
				newLeftArrow.setOnMouseClicked(e -> {
					try {
						System.out.println(activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable,boardWidth-1,finalY,false);
						updateGameState();
						pushTileAnimation(boardWidth-1,finalY,2);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}
				});
			}
		}
		gameObjects.getChildren().add(arrows);
	}

	/**
	 * TODO: old, dont need this anymore
	 * animates and makes clickable all columns/rows that a tile can be pushed into
	 */
	/*public static void setPushable() {
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
	}*/
	
	private void updatePhase() {
		if(phase<3) {
		   phase=0;
		}else {
			phase++;
		}
	}
	
	private static void displayTurns() {
		Label turnsLabel = new Label("Turns: "+turns);
		turnsLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		turnsLabel.setTextFill(Color.WHITE);
		turnsLabel.setFont(new Font("Arial", 40));
		turnsLabel.setTranslateX(-150);
		turnsLabel.setTranslateY(350);
		turnsLabel.setPrefWidth(1200);
		turnsLabel.setAlignment(Pos.CENTER);
		gameObjects.getChildren().add(turnsLabel);
		ScaleTransition turnShow = new ScaleTransition(Duration.millis(800), turnsLabel);
		turnShow.setFromY(0.1);
		turnShow.setToY(1);
		
		PauseTransition hold = new PauseTransition(Duration.millis(1000));
		
		ScaleTransition turnOut = new ScaleTransition(Duration.millis(800), turnsLabel);
		turnOut.setFromY(1);
		turnOut.setToY(0.1);
		
		SequentialTransition seqTransition = new SequentialTransition(turnShow,hold,turnOut);
		seqTransition.setOnFinished(e->{
			gameObjects.getChildren().remove(turnsLabel);
			
			currentGameState=currentGame.getNewTileForCurrentPlayer();
			actionTilesOwned = currentGameState.getActionTileForPlayer(currentGameState.getCurPlayer());
			Tile drawTile=currentGameState.getTileDrawn();
			
			showDrawTile(drawTile);
			if(!drawTile.isAction()) {
				activePlaceable=(Placeable) drawTile;
				showPlaceableFloor(drawTile);
				setPushableArrows();
			}
			if(actionTilesOwned.size() > 0) {
			    showInventory();
			}
		});
		seqTransition.play();
	}
	
	private static void displayErrorMessage(String errorMessage) {
		Label error = new Label(errorMessage);
		error.setStyle("-fx-background-color: transparent;");
		error.setTextFill(Color.RED);
		error.setFont(new Font("Arial", 40));
		gameObjects.getChildren().add(error);
		TranslateTransition errorPop = new TranslateTransition(Duration.seconds(1),error);
		errorPop.setFromX(400);
		errorPop.setFromY(1000);
		errorPop.setToX(400);
		errorPop.setToY(850);
		
		PauseTransition hold = new PauseTransition(Duration.millis(2000));
		
		SequentialTransition seqTransition = new SequentialTransition(errorPop,hold);
		seqTransition.setOnFinished(e->{
			gameObjects.getChildren().remove(error);
		});
		seqTransition.play();
	}
	
	public static void setMoveableTiles() {
		updateGameState();
		boolean[][] moveableSpaces = currentGameState.getMoveableSpaces();
		clickableAnime = new ParallelTransition();
		System.out.println(moveableSpaces.length);
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				System.out.print(moveableSpaces[y][x]+", ");
				final int finalX = x;
				if(moveableSpaces[y][x]) {
					clickableAnime.getChildren().add(animateTile(tileArray[y][x]));
					tileArray[y][x].setOnMouseClicked(e -> {
						playerDeliberateMove(playerPlaying, finalY, finalX);
					});
					clickAble.add(tileArray[y][x]);
				}
			}
			System.out.println("");
		}
		clickableAnime.play();
	}
	
	
	public static void movePlayer(Group player, double x, double y) {
		TranslateTransition playerMove = new TranslateTransition(Duration.seconds(1),player);
		playerMove.setToX(x);
		playerMove.setToY(y);
		playerMove.play();
	}
	
	public static void playerDeliberateMove(Group player, int y, int x) {
		try {
	        currentGame.moveCurrentPlayer(x, y);
			movePlayer(player,tileArray[y][x].getTranslateX(),tileArray[y][x].getTranslateY());
			resetClickable();
			updateGameState();
			checkWin();
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
	

	
	public static void resetClickable() {
		for (Box tile : clickAble) {
			tile.setOnMouseClicked(null);
			tile.scaleXProperty().set(1);
			tile.scaleYProperty().set(1);
		}
		clickableAnime.pause();
		clickAble.clear();
		//updateBoard();
		//updatePlayerPosition();
	}


	public static void pushTileAnimation(int rows, int columns, int orientation) {
		gameObjects.getChildren().remove(arrows);
		arrows.getChildren().removeAll();
		hideInventory();
		
		
		Box tileToRemove=null;
		int starting,ending,incValue,arrayX,arrayY;
		double X,Y;
		if (orientation==2 || orientation==3) {
			
			if (orientation==2) {
				starting=0;
				ending=boardHeight-1;
				incValue=1;
				arrayX=columns;
			}else {
				starting=boardHeight-1;
				ending=0;
				incValue=-1;
				arrayX=columns;
			}
			//System.out.println("S: " + starting + " ending: " + ending+ " inc: "+incValue);
			System.out.println(arrayX+" "+ starting +" "+tileArray[arrayX][starting]);
			tileToRemove = tileArray[arrayX][starting];
			X = tileToRemove.getTranslateX();
			Y = tileToRemove.getTranslateY();
			for (int i = starting; i!= ending; i+=incValue) {
				//System.out.println("R: "+i+" S: " + starting + " ending: " + ending+ " inc: "+incValue);
				Box newTile = tileArray[arrayX][(i+incValue)];
				moveTile(X, Y, newTile,false);
				tileArray[arrayX][i] = newTile;
				//System.out.println("R: "+i);
				X = newTile.getTranslateX();
				Y = newTile.getTranslateY();
			}
		}else {
			if (orientation==0) {
				starting=0;
				ending=boardWidth-1;
				incValue=1;
				arrayY=rows;
			}else {
				starting=boardWidth-1;
				ending=0;
				incValue=-1;
				arrayY=rows;
			}
			//System.out.println("S: " + starting + " ending: " + ending+ " inc: "+incValue);
			tileToRemove = tileArray[starting][arrayY];
			X = tileToRemove.getTranslateX();
			Y = tileToRemove.getTranslateY();
			for (int i = starting; i!= ending; i+=incValue) {
				//System.out.println("R: "+i+" S: " + starting + " ending: " + ending+ " inc: "+incValue);
				Box newTile = tileArray[(i+incValue)][arrayY];
				moveTile(X, Y, newTile,false);
				tileArray[i][arrayY] = newTile;
				//System.out.println("R: "+i);
				X = newTile.getTranslateX();
				Y = newTile.getTranslateY();
			}
		}
		// vertical
		
		tiles.getChildren().remove(tileToRemove);
		pushNewTile(X,Y,rows,columns,true);
	}
	public static Box pushNewTile(double x, double y,int rows, int columns, boolean last) {
		Box newTile =objectFactory.makeTile(activePlaceable);
		newTile.setTranslateX(1000);
		newTile.setTranslateY(600);
		newTile.setTranslateZ(0);
		tiles.getChildren().add(newTile);
		moveTile(x,y,newTile,last);
		tileArray[columns][rows]=newTile;
		return newTile;
	}

	public static void moveTile(double x, double y, Box moveTile, boolean last) {
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(500), moveTile);
		tileMove.setToX(x);
		tileMove.setToY(y);
		if(last) {
			tileMove.setOnFinished(e->{
				updateBoard();
				updatePlayerPosition();
				setMoveableTiles();
			});
		}
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

	public static void animationPlayTile(double x, double y,String playType) {
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
		if(playType.equals("fire")) {
		    imageTextureElement.setDiffuseMap(new Image("/img/texture/fireMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("/img/texture/fireMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/fireMagicCircle.png"));
		}else if(playType.equals("ice")){
			imageTextureElement.setDiffuseMap(new Image("/img/texture/frozenMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("/img/texture/iceMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/iceMagicCircle.png"));
		}else if(playType.equals("wind")){
			imageTextureElement.setDiffuseMap(new Image("/img/texture/windMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("/img/texture/windMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/windMagicCircle.png"));
		}else{
			imageTextureElement.setDiffuseMap(new Image("/img/texture/backTrackMagic.png"));
            imageTextureMC.setDiffuseMap(new Image("/img/texture/portalMagicCircle.png"));
            imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/portalMagicCircle.png"));
		}
		elementType.setMaterial(imageTextureElement);
		magicCircle.setMaterial(imageTextureMC);
		gameObjects.getChildren().addAll(elementType,magicCircle);
		
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
}
	public static void teleportPlayer(Group player, double x, double y) {
    	Cylinder teleCylinder = new Cylinder(80,200);
    	PhongMaterial teleClinderTexture = new PhongMaterial();
    	teleClinderTexture.setSelfIlluminationMap(new Image("/img/texture/teleCylinder.png"));
    	teleCylinder.setMaterial(teleClinderTexture);
    	teleCylinder.setRotationAxis(Rotate.X_AXIS);
    	teleCylinder.setRotate(90);
    	teleCylinder.setTranslateZ(-60);
    	teleCylinder.setVisible(false);
    	
    	PauseTransition hold = new PauseTransition(Duration.millis(1000));
    	hold.setOnFinished(e->{
    		teleCylinder.setVisible(true);
    	});
    	ScaleTransition teleFadeIn = new ScaleTransition(Duration.millis(1000),teleCylinder);    
    	teleFadeIn.setFromX(0);
    	teleFadeIn.setFromY(0);
    	teleFadeIn.setToX(1);
    	teleFadeIn.setToY(1);
    	teleFadeIn.setOnFinished(e->{
    		player.setTranslateX(x);
    		player.setTranslateY(y);
    	});
    	
    	ScaleTransition teleFadeOut = new ScaleTransition(Duration.millis(1000),teleCylinder);    
    	teleFadeOut.setFromX(1);
    	teleFadeOut.setFromY(1);
    	teleFadeOut.setToX(0);
    	teleFadeOut.setToY(0);
    	teleFadeOut.setOnFinished(e->{
    		player.getChildren().remove(teleCylinder);
    	});
    	
    	animationPlayTile(player.getTranslateX(),player.getTranslateY(),"teleport");
    	SequentialTransition seqTransition = new SequentialTransition(hold,teleFadeIn,teleFadeOut);
    	seqTransition.play();
    	player.getChildren().add(teleCylinder);
    }
	
	private static void setRightMenu() {
		sRightMenuPane.getChildren().remove(turnLabel);
		turnLabel = new Label(Integer.toString(turns));
		turnLabel.setPrefWidth(20);
		turnLabel.setLayoutX(59);
		turnLabel.setLayoutY(9);
		turnLabel.setTextFill(Color.WHITE);
		turnLabel.setFont(new Font("Arial", 29));
		sRightMenuPane.getChildren().add(turnLabel);
		TranslateTransition turnLabelMove = new TranslateTransition(Duration.millis(300), turnLabel);
		turnLabelMove.setFromY(-50);
		turnLabelMove.setToY(0);
		turnLabelMove.play();
		
		showCurPlayer();
		
		
		teleportPlayer(playerPlaying, tileArray[4][4].getTranslateX(), tileArray[4][4].getTranslateY());
	}
	
	
	private static void showPlaceableFloor(Tile floorTile) {
		inventory=new Group();
		Box floor=null;
		final Box fTile;
		if(floorTile.getType()==TileType.Corner) {
			floor=objectFactory.makeTileInInventory(4);
		}else if(floorTile.getType()==TileType.Straight) {
			floor=objectFactory.makeTileInInventory(5);
		}else if(floorTile.getType()==TileType.TShaped) {
			floor=objectFactory.makeTileInInventory(6);
		}
		selectedTile=floor;
		fTile=floor;
		floor.setOnMouseClicked(e->{
			fTile.setRotate(fTile.getRotate()+90);
			activePlaceable.rotateRight();
		});
		floor.setTranslateY(400);
		floor.setTranslateZ(-50);
		inventory.getChildren().add(floor);
		TranslateTransition floortileMove = new TranslateTransition(Duration.millis(1000), floor);
		floortileMove.setFromX(1200);
		floortileMove.setToX(950);
		floortileMove.play();
		gameObjects.getChildren().add(inventory);
	}
	

	public static void playFireTile() {
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				final int finalX = x;
				animateTile(tileArray[y][x]);
				tileArray[y][x].setOnMouseClicked(e -> {
					placeFireTile(finalY, finalX);
				});
				clickAble.add(tileArray[y][x]);
			}
		}
	}

	public static void placeFireTile(int y, int x) {
		Fire fireTile = null;
		for (ActionTile actionTile:actionTilesOwned) {
			if (actionTile.getType() == TileType.Fire) {
				fireTile = (Fire) actionTile;
			}
		}
		try {
			currentGameState = currentGame.playFire(fireTile, x, y);
			updateBoard();
			resetClickable();
		} catch (IncorrectTileTypeException | IllegalFireException e) {
			e.printStackTrace();
		}
	}

	public static void playIceTile() {
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				final int finalX = x;
					animateTile(tileArray[y][x]);
					tileArray[y][x].setOnMouseClicked(e -> {
						placeIceTile(finalY, finalX);
					});
					clickAble.add(tileArray[y][x]);
			}
		}
	}
	
	private static void placeIceTile(int y, int x) {
		Ice iceTile = null;
		for(ActionTile actionTile:actionTilesOwned) {
			if(actionTile.getType() == TileType.Ice) {
				iceTile = (Ice) actionTile;
			}
		}
		//System.out.println("Ice tile used:" + iceTile);
		//System.out.println("insert x:" + x + " insert y:" + y);
		try {
			currentGame.playIce(iceTile, x, y);
		} catch (IncorrectTileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(actionTilesOwned.size());
		resetClickable();
	}
	
	public void playBacktrack() {
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				final int finalX = x;
					animateTile(tileArray[y][x]);
					tileArray[y][x].setOnMouseClicked(e -> {
						placeIceTile(finalY, finalX);
					});
					clickAble.add(tileArray[y][x]);
			}
		}
	}
	
	public static void showInventory() {
		//System.out.println("hi");
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
		double y = -200;
		
		for(ActionTile actionTile:actionTilesOwned) {
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
		for(int i = 0; i < actionTileObtained.length; i++) {
			if(actionTileObtained[i]) {
			Box acTile = objectFactory.makeTileInInventory(i);
			acTile.setTranslateX(inventoryBase.getTranslateX()-10);
			acTile.setTranslateY(y);
			acTile.setTranslateZ(inventoryBase.getTranslateZ()-50);
			if(actionTileObtained[0]) {
				acTile.setOnMouseClicked(e->{
					//what to do when a fire tile is clicked
					playFireTile();
				});
			} else if(actionTileObtained[1]) {
				acTile.setOnMouseClicked(e->{
					//what to do when an ice tile is clicked					
					playIceTile();
				});
			} else if(actionTileObtained[2]) {
				acTile.setOnMouseClicked(e->{
					//what to do when a doubleMove tile is clicked
					if(selectedTile!=acTile) {
						setSelectedTile(acTile);
					}else {
						acTile.setRotate(acTile.getRotate()+90);
						
					}
				});
			} else if(actionTileObtained[3]) {
				acTile.setOnMouseClicked(e->{
					//what to do when a doubleMove tile is clicked
					if(selectedTile!=acTile) {
						setSelectedTile(acTile);
					}else {
						acTile.setRotate(acTile.getRotate()+90);
						
					}
				});
			}
			tileInventory.add(acTile);
			inventory.getChildren().add(acTile);
			y+=70;
			}
		}
		gameObjects.getChildren().add(inventory);
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(1000), inventory);
		tileMove.setFromX(1200);
		tileMove.setToX(950);
		tileMove.play();

	}
	
	private static void hideInventory() {
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(1000), inventory);
		tileMove.setFromX(950);
		tileMove.setToX(1200);
		tileMove.setOnFinished(e->{
			gameObjects.getChildren().remove(inventory);
		});
		tileMove.play();
	}
	
	private static void setSelectedTile(Box newSelected) {
		if(selectedTile!=null) {
		   selectedTile.setWidth(70);
		   selectedTile.setHeight(70);
		}
		newSelected.setWidth(90);
		newSelected.setHeight(90);
		selectedTile=newSelected;
	}
	
	private static void showCurPlayer() {
		sRightMenuPane.getChildren().remove(playerIndicator);
		int curPlayerNum = currentGameState.getCurPlayer();
		playerIndicator = new ImageView();
		playerIndicator.setFitHeight(59);
		playerIndicator.setFitWidth(57);
		playerIndicator.setLayoutX(39);
		playerIndicator.setLayoutY(83);
		sRightMenuPane.getChildren().add(playerIndicator);
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
	
	private static void showDrawTile(Tile drawTile) {
		Image tileImage=null;
		if(drawTile.getType()==TileType.Straight) {
			tileImage = new Image("/img/texture/Straight.png");
		}else if(drawTile.getType()==TileType.Corner) {
			tileImage = new Image("/img/texture/Corner.png");
		}else if(drawTile.getType()==TileType.TShaped) {
			tileImage = new Image("/img/texture/TShaped.png");
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
		sRightMenuPane.getChildren().add(tileIcon);
		ScaleTransition appearTile = new ScaleTransition(Duration.seconds(1),tileIcon);
		appearTile.setFromX(0);
		appearTile.setFromY(0);
		appearTile.setToX(1);
		appearTile.setToY(1);
		appearTile.setOnFinished(e->{
			sRightMenuPane.getChildren().add(tileType);
		});
		PauseTransition hold = new PauseTransition(Duration.millis(1000));
		hold.setOnFinished(e->{
			sRightMenuPane.getChildren().remove(tileType);
		});
		
		ScaleTransition disappearTile = new ScaleTransition(Duration.seconds(1),tileIcon);
		disappearTile.setFromX(1);
		disappearTile.setFromY(1);
		disappearTile.setToX(0);
		disappearTile.setToY(0);
		SequentialTransition showTileSeq = new SequentialTransition(appearTile,hold,disappearTile);
		showTileSeq.play();
	}
	
	public static Label showTileType(Tile drawTile) {
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
