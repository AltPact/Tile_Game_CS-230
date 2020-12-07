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
	private static int turns = 1;
	private static ArrayList<Box> tileInventory;
	private static ArrayList<ActionTile> actionTilesOwned;
	private static Group inventory;
	private static Placeable activePlaceable; // the floor tile drawn from the silk bag which must be placed by the
												// current player (if they drew a floor tile)
	private static Box selectedTile;
	private static int phase;
	private static ParallelTransition clickableAnime;
	private static Label turnLabel;
	private static ImageView playerIndicator;
	private static int playerNoGotBackTrack=-1;
	private static ArrayList<Box> actionTile = new ArrayList<Box>();
	private static Tile drawnTile;
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
		//currentGame = FileReaderWriterTest.generateTestGame();
		currentGameState = currentGame.getInitalGameState();
		initVariables();
		addFloor();
		addTile();
		initLightSource(); 
		addPlayer();
		sRightMenuPane = rightMenuPane;
		// gameObjects.getChildren().add(objectFactory.makeFireFly());
		// setScene
		subScene = new SubScene(gameObjects, sceneWidth, sceneHeight);
		setCamera();
		// addScene
		GB.setCenter(subScene);

		newTurn();
		//teleportPlayer(playerObjectArray[1], 300, 500);
	}

	/**
	 * Set game environment
	 */
	public void initLightSource() {
		gameObjects.getChildren().add(objectFactory.makeLightSource(100, 100));
		gameObjects.getChildren().add(objectFactory.makeLightSource(500, 300));
		gameObjects.getChildren().add(objectFactory.makeLightSource(100, 600));
	}

	public void initVariables() {
		tileBoard = currentGameState.getBoard();
		boardHeight = tileBoard.length;
		boardWidth = tileBoard[0].length;
		// System.out.println("board used in gamescene:");
		for (int a = 0; a < boardHeight; a++) {
			for (int b = 0; b < boardWidth; b++) {
				// System.out.print(tileBoard[a][b].getType() + " ");
			}
			// System.out.println("");
		}
		subScene = null;
		objectFactory = new ObjFactory();
		gameObjects = new Group();
		tileArray = new Box[boardHeight][boardWidth];
		playerObjectArray = new Group[currentGameState.getPlayersPositions().length];
		playerPieceLink = new HashMap<Sphere, PlayerPiece>();
		playerPlaying = null;
		clickAble = new ArrayList<Box>();
		clickableAnima = null;
		scaleArray = new ArrayList<ScaleTransition>();
		tiles = new Group();
		arrows = new Group();
		turns= currentGameState.getTurns();
	}

	public void addFloor() {
		Box floor = objectFactory.makeFloor();
		floor.setTranslateX(420);
		floor.setTranslateY(400);
		gameObjects.getChildren().add(floor);
		// System.out.println(floor.getTranslateZ());
	}

	private static void updateGameState() {
		System.out.println("UPDATE GAME STATE CURRENT PLAYER BEFORE" + currentGameState.getCurPlayer());
		currentGameState = currentGame.getCurrentGameState();
		System.out.println("UPDATE GAME STATE CURRENT PLAYER AFTER" + currentGameState.getCurPlayer());
		
	}

	/**
	* Setting up the visual representation of board
	*/
	public void addTile() {
		int y = 400 - (boardHeight * 100) / 2;
		// System.out.println("Starting X: "+(400-boardWidth/2));
		// System.out.println("Starting Y: "+y);
		for (int h = 0; h < boardHeight; h++) {
			int x = 400 - (boardWidth * 100) / 2;

			for (int w = 0; w < boardWidth; w++) {
				Box tile = objectFactory.makeTile(tileBoard[h][w]);
				tile.translateXProperty().set(x);
				tile.translateYProperty().set(y);
				tile.translateZProperty().set(0);
				tileArray[h][w] = tile;// box array
				tiles.getChildren().add(tile);// tile group
				x += 100;// gap between tiles
				// System.out.println(tile.getTranslateZ());
			}
			y += 100;// gap between tiles
		}
		gameObjects.getChildren().add(tiles);// add tile group to game group
	}
	
	/**
	* Adds players to the board
	*/
	public void addPlayer() {
		initPlayerPos = currentGameState.getPlayersPositions();
		// PlayerPiece pieceArray[] = currentGame.getPlayerPieceArray();
		for (int i = 0; i < initPlayerPos.length; i++) {
			// put player pos in int
			int x = initPlayerPos[i][1];
			int y = initPlayerPos[i][0];
			Group player = objectFactory.makePlayer(i);
			player.setMouseTransparent(true);
			player.translateXProperty().set(tileArray[y][x].getTranslateX());
			player.translateYProperty().set(tileArray[y][x].getTranslateY());
			player.translateZProperty().set(-50);
			// playerPieceLink.put(sphere, pieceArray[i]);
			gameObjects.getChildren().add(player);
			playerObjectArray[i] = player;
		}
		// System.out.println(gameObjects.getChildren());
	}

	/**
	* Sets up the players perspective
	*/
	public void setCamera() {
		PerspectiveCamera camera = new PerspectiveCamera();
		camera.translateXProperty().set(cameraX);
		camera.translateYProperty().set(cameraY);
		camera.translateZProperty().set(-1200);
		subScene.setCamera(camera);
	}
	
	/**
	 * New turns method
	 */
	private static void newTurn() {
		// displayTurns();
		updateGameState();
		updateBoard();
		if (!currentGameState.isGoalHit()) {
			playerPlaying = playerObjectArray[currentGameState.getCurPlayer()];
			System.out.println("Current player number: "+currentGameState.getCurPlayer());
			phase = 1;
			displayTurns();
			setRightMenu();
			turns++;
		}else {
			PlayerPiece winplayer=currentGameState.getPlayers()[turns%currentGameState.getPlayers().length];
			String winnerName=winplayer.getLinkedData().getName();
			ImageView winIcon=new ImageView(new Image("/img/texture/trophy.png"));
			winIcon.setTranslateX(-150);
			winIcon.setTranslateY(350);
			winIcon.setFitWidth(500);
			winIcon.setFitHeight(500);
			Label turnsLabel = new Label("Winner: " + winnerName);
			turnsLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
			turnsLabel.setTextFill(Color.WHITE);
			turnsLabel.setFont(new Font("Arial", 40));
			turnsLabel.setTranslateX(-150);
			turnsLabel.setTranslateY(350);
			turnsLabel.setMinWidth(1200);
			turnsLabel.setAlignment(Pos.CENTER);
			gameObjects.getChildren().addAll(winIcon,turnsLabel);

		}
	}
	
	/**
	 * Update methods
	 */
	private static void updateBoard() {
		updateGameState();
		Placeable[][] newBoard = currentGameState.getBoard();
		for (int h = 0; h < boardHeight; h++) {
			for (int w = 0; w < boardWidth; w++) {
				System.out.print(newBoard[h][w].isFrozen()+" <Frozen Fire>"+newBoard[h][w].isOnFire()+", ");
				objectFactory.textureTheTile(tileArray[h][w], newBoard[h][w]);
			}
			System.out.println();
		}
	}

	/**
	* Updates player positions
	*/
	private static void updatePlayerPosition() throws IllegalMove {
		updateGameState();
		int[][] playerPosition = currentGameState.getPlayersPositions();
		for (int playerNum = 0; playerNum < playerPosition.length; playerNum++) {
			int x = playerPosition[playerNum][1];
			int y = playerPosition[playerNum][0];
			System.out.println("Player Position X: " + x + " Y: " + y);
			Box onTile = tileArray[y][x];
			System.out.println("player got backTrack value: "+playerNoGotBackTrack);
			if(playerNoGotBackTrack>=0&&playerNoGotBackTrack==playerNum) {
				System.out.println("player got back: "+playerNoGotBackTrack+" player num: "+playerNum);
				teleportPlayer(playerObjectArray[playerNum], onTile.getTranslateX(),onTile.getTranslateY());
				playerNoGotBackTrack=-1;
				System.out.println("Teleport");
			}else {
				System.out.println("Move");
				movePlayer(playerObjectArray[playerNum], onTile.getTranslateX(), onTile.getTranslateY());
			}
		}
	}
	/**
	 * Translate methods
	 */
	private static void setOrientation(Box tile) {
		int ori=(int)(tile.getRotate()/90)%4;
		System.out.println("Translate rotate "+ori);
		activePlaceable.setOrientation(ori);
	}
	
	/**
	 * Game process
	 */
	
	/**
	 * creates arrows next to all free rows/columns that can be clicked by the
	 * player to insert a tile into that row/column
	 */
	private static void setRightMenu() {
		sRightMenuPane.getChildren().remove(turnLabel);
		turnLabel = new Label(Integer.toString(turns));
		turnLabel.setPrefWidth(200);
		turnLabel.setLayoutX(50);
		turnLabel.setLayoutY(9);
		turnLabel.setWrapText(true);
		turnLabel.setTextFill(Color.WHITE);
		turnLabel.setFont(new Font("Arial", 29));
		sRightMenuPane.getChildren().add(turnLabel);
		TranslateTransition turnLabelMove = new TranslateTransition(Duration.millis(300), turnLabel);
		turnLabelMove.setFromY(-50);
		turnLabelMove.setToY(0);
		turnLabelMove.play();

		showCurPlayer();

		//teleportPlayer(playerPlaying, tileArray[4][4].getTranslateX(),
		//tileArray[4][4].getTranslateY());
	}

	/**
	* Creating the visual representations of players
	*/
	private static void showCurPlayer() {
		sRightMenuPane.getChildren().remove(playerIndicator);
		int curPlayerNum = currentGameState.getCurPlayer();
		playerIndicator = new ImageView();
		playerIndicator.setFitHeight(59);
		playerIndicator.setFitWidth(57);
		playerIndicator.setLayoutX(39);
		playerIndicator.setLayoutY(83);
		sRightMenuPane.getChildren().add(playerIndicator);
		if (curPlayerNum == 0) {
			playerIndicator.setImage(new Image("/img/redWizard.png"));
		} else if (curPlayerNum == 1) {
			playerIndicator.setImage(new Image("/img/yellowWizard.png"));
		} else if (curPlayerNum == 2) {
			playerIndicator.setImage(new Image("/img/blueWizard.png"));
		} else {
			playerIndicator.setImage(new Image("/img/purpleWizard.png"));
		}
		TranslateTransition changePlayer = new TranslateTransition(Duration.seconds(0.8), playerIndicator);
		changePlayer.setFromX(50);
		changePlayer.setToX(0);
		changePlayer.play();
	}
	
	/**
	* Displays which players turn it is.
	* Displays the tile the player has drawn.
	* Displays the action tiles, if any, the player owns.
	*/
	private static void displayTurns() {
		Label turnsLabel = new Label("Turns: " + turns);
		turnsLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		turnsLabel.setTextFill(Color.WHITE);
		turnsLabel.setFont(new Font("Arial", 40));
		turnsLabel.setTranslateX(-150);
		turnsLabel.setTranslateY(350);
		turnsLabel.setMinWidth(1200);
		turnsLabel.setAlignment(Pos.CENTER);
		gameObjects.getChildren().add(turnsLabel);
		ScaleTransition turnShow = new ScaleTransition(Duration.millis(800), turnsLabel);
		turnShow.setFromY(0.1);
		turnShow.setToY(1);

		PauseTransition hold = new PauseTransition(Duration.millis(1000));

		ScaleTransition turnOut = new ScaleTransition(Duration.millis(800), turnsLabel);
		turnOut.setFromY(1);
		turnOut.setToY(0.1);

		SequentialTransition seqTransition = new SequentialTransition(turnShow, hold, turnOut);
		seqTransition.setOnFinished(e -> {
			gameObjects.getChildren().remove(turnsLabel);

			currentGameState = currentGame.getNewTileForCurrentPlayer();
			
			Tile drawTile = currentGameState.getTileDrawn();
			drawnTile=drawTile;
			System.out.println("************Type of Tile Drawn " + drawTile.getType());
            updateGameState();
			showDrawTile(drawTile);
			if (!drawTile.isAction()) {
				System.out.println("Drawn tile: " + drawTile.getType());
				activePlaceable = (Placeable) drawTile;
				
				boolean[][] insertables = currentGameState.getInsertableLocations();
                boolean anyInsertables = false;
                for (int i = 0; i < insertables.length; i++) {
                    for (int j = 0; j<insertables[i].length; j++) {
                        if (insertables[i][j]) {
                            anyInsertables = true;
                        }
                    }
                }
                
				if(!anyInsertables) {
					showInventory();
				}else {
					showPlaceableFloor(drawTile);
				    setPushableArrows();
				}
			}else {
				showInventory();
			}
			
		});
		seqTransition.play();
	}
	
	/**
	* Displays the type of tile the player is placing on the board.
	*/
	private static void showPlaceableFloor(Tile floorTile) {
		System.out.println("===========New Tile: " + floorTile.getType());
		inventory = new Group();
		Box floor = null;
		final Box fTile;
		floor = objectFactory.makeTileInInventory(floorTile.getType());
		/*if (floorTile.getType() == TileType.Corner) {
			
		//} else if (floorTile.getType() == TileType.Straight) {
			//floor = objectFactory.makeTileInInventory(5);
		//} else if (floorTile.getType() == TileType.TShaped) {
			//floor = objectFactory.makeTileInInventory(6);
		//}*/
		selectedTile = floor;
		fTile = floor;
		floor.setOnMouseClicked(e -> {
			fTile.setRotate(fTile.getRotate() + 90);
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
	
	/**
	* Allows the player to interact with the board, showing which rows and columns
	* the player can insert a tile into.
	* Updates the game state with the new board.
	* Throws an exception if a tile cannot be inserted.
	*/
	public static void setPushableArrows() {
		updateGameState();
		updateBoard();
		System.out.println("Current player: "+currentGameState.getCurPlayer());
		ArrayList<Box> pushableTiles = new ArrayList<Box>();
		boolean[][] insertablePlaces = currentGameState.getInsertableLocations(); // [rows, columns][index]
		System.out.print("rows: ");
        for (int i = 0; i < insertablePlaces[0].length; i++) {
            System.out.print(insertablePlaces[0][i] + ", ");
        }
        System.out.print("\ncolumns: ");
        for (int i = 0; i < insertablePlaces[1].length; i++) {
            System.out.print(insertablePlaces[1][i] + ", ");
        }
		
		for (int x = 0; x < boardWidth; x++) { // for each column
			final int finalX = x;
			if (insertablePlaces[0][x]) {
				pushableTiles.add(tileArray[0][x]);
				System.out.println("0 " + x);
				Box newDownArrow = objectFactory.makeArrow(1); // make down arrow
				System.out.println(tileArray[0][x].getTranslateX() + " " + tileArray[0][x].getTranslateY());
				newDownArrow.translateXProperty().set(tileArray[0][x].getTranslateX()); // same x
				newDownArrow.translateYProperty().set(tileArray[0][x].getTranslateY() - 100); // -100y
				newDownArrow.translateZProperty().set(0);
				arrows.getChildren().add(newDownArrow);
				newDownArrow.setOnMouseClicked(e -> {
					try {
						//setOrientation(selectedTile);
						System.out.println("------------Acive Placeable: " + activePlaceable.getType() + " Ori: " + activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable, finalX, 0, true);
						updateGameState();
						pushTileAnimation(finalX, 0, 1);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}

				});

				pushableTiles.add(tileArray[boardHeight - 1][x]);
				Box newUpArrow = objectFactory.makeArrow(0); // make up arrow
				newUpArrow.translateXProperty().set(tileArray[boardHeight - 1][x].translateXProperty().get()); // same x
				newUpArrow.translateYProperty().set(tileArray[boardHeight - 1][x].translateYProperty().get() + 100); // +100y
				newUpArrow.translateZProperty().set(0);
				arrows.getChildren().add(newUpArrow);
				newUpArrow.setOnMouseClicked(e -> {
					try {
						System.out.println("------------Acive Placeable: " + activePlaceable.getType() + " Ori: " + activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable, finalX, boardHeight - 1, true);
						updateGameState();
						pushTileAnimation(finalX, boardHeight - 1, 0);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}

				});
			}
		}
		for (int y = 0; y < boardHeight; y++) { // for each row
			final int finalY = y;
			if (insertablePlaces[1][y]) {
				pushableTiles.add(tileArray[y][0]);
				Box newRightArrow = objectFactory.makeArrow(3); // make right arrow
				newRightArrow.translateXProperty().set(tileArray[y][0].translateXProperty().get() - 100); // -100x
				newRightArrow.translateYProperty().set(tileArray[y][0].translateYProperty().get()); // same y
				newRightArrow.translateZProperty().set(0);
				arrows.getChildren().add(newRightArrow);
				newRightArrow.setOnMouseClicked(e -> {
					try {
						System.out.println("------------Acive Placeable: " + activePlaceable.getType() + " Ori: " + activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable, 0, finalY, false);
						updateGameState();
						pushTileAnimation(0, finalY, 3);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}
				});

				pushableTiles.add(tileArray[y][boardWidth - 1]);
				Box newLeftArrow = objectFactory.makeArrow(2); // make left arrow
				newLeftArrow.translateXProperty().set(tileArray[y][boardWidth - 1].translateXProperty().get() + 100); // +100x
				newLeftArrow.translateYProperty().set(tileArray[y][boardWidth - 1].translateYProperty().get()); // same
																												// y
				newLeftArrow.translateZProperty().set(0);
				arrows.getChildren().add(newLeftArrow);
				newLeftArrow.setOnMouseClicked(e -> {
					try {
						System.out.println("------------Acive Placeable: " + activePlaceable.getType() + " Ori: " + activePlaceable.getOrientation());
						currentGame.insertTile(activePlaceable, boardWidth - 1, finalY, false);
						updateGameState();
						pushTileAnimation(boardWidth - 1, finalY, 2);
					} catch (IllegalInsertionException e1) {
						displayErrorMessage("Cannot insert here");
					}
				});
			}
		}
		gameObjects.getChildren().add(arrows);
	}

	/**
	 * TODO: old, dont need this anymore animates and makes clickable all
	 * columns/rows that a tile can be pushed into
	 */
	/*
	 * public static void setPushable() { ArrayList<Box> pushableTiles = new
	 * ArrayList<Box>(); boolean[][] insertablePlaces =
	 * currentGameState.getInsertableLocations(); for (int x = 0; x < boardWidth;
	 * x++) { // for each column final int finalX = x; if (insertablePlaces[1][x]) {
	 * pushableTiles.add(tileArray[x][0]); tileArray[0][x].setOnMouseClicked(e -> {
	 * pushTile(finalX, true); }); } } for (int y = 0; y < boardHeight; y++) { //
	 * for each row final int finalY = y; if (insertablePlaces[0][y]) {
	 * pushableTiles.add(tileArray[y][0]); tileArray[y][0].setOnMouseClicked(e -> {
	 * pushTile(finalY, false); }); } } }
	 */

	private void updatePhase() {
		if (phase < 3) {
			phase = 0;
		} else {
			phase++;
		}
	}

	/**
	* Handles the visuals of error messages
	*/
	private static void displayErrorMessage(String errorMessage) {
		Label error = new Label(errorMessage);
		error.setStyle("-fx-background-color: transparent;");
		error.setTextFill(Color.RED);
		error.setFont(new Font("Arial", 40));
		gameObjects.getChildren().add(error);
		TranslateTransition errorPop = new TranslateTransition(Duration.seconds(1), error);
		errorPop.setFromX(400);
		errorPop.setFromY(1000);
		errorPop.setToX(400);
		errorPop.setToY(850);

		PauseTransition hold = new PauseTransition(Duration.millis(2000));

		SequentialTransition seqTransition = new SequentialTransition(errorPop, hold);
		seqTransition.setOnFinished(e -> {
			gameObjects.getChildren().remove(error);
		});
		seqTransition.play();
	}
	
	/**
	* Shows the moveable tiles available to the current player.
	* Shows the moveable spaces available to current player.
	* Updates game state on the players choice.
	*/
	public static void setMoveableTiles() {
		
		updateGameState();
		boolean moveable=false;
		System.out.println("CURRENT PLAYER: " + currentGameState.getCurPlayer());
		boolean[][] moveableSpaces = currentGameState.getMoveableSpaces();
		for(int h=0;h<boardHeight;h++) {
			for(int w=0;w<boardWidth;w++) {
				if(moveableSpaces[h][w]) {
					System.out.print("true");
					moveable=true;
				}
			}System.out.println("");
		}
		System.out.println("setMoveableTiles() "+!moveable);
		if(!moveable) {
			currentGame.endTurn();
			
			newTurn();
		}else {
		clickableAnime = new ParallelTransition();
		System.out.println(moveableSpaces.length);
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				System.out.print(moveableSpaces[y][x] + ", ");
				final int finalX = x;
				if (moveableSpaces[y][x]) {
					clickableAnime.getChildren().add(animateTile(tileArray[y][x]));
					tileArray[y][x].setOnMouseClicked(e -> {
						System.out.println("582");
						playerDeliberateMove(playerPlaying, finalY, finalX);
					});
					clickAble.add(tileArray[y][x]);
				}
			}
			System.out.println("");
		}
		clickableAnime.play();
		}
	}
	
	/**
	* Adds visuals for players movement.
	* Throws an exception if an illegal move is attempted.
	*/
	public static void movePlayer(Group player, double x, double y) throws IllegalMove {
		TranslateTransition playerMove = new TranslateTransition(Duration.seconds(1), player);
		playerMove.setToX(x);
		playerMove.setToY(y);
		playerMove.play();
	}

	/**
	* Moves the player and updates the game state
	*/
	public static void playerDeliberateMove(Group player, int y, int x) {
		try {
			currentGame.moveCurrentPlayer(x, y);
			updateGameState();
			movePlayer(player, tileArray[y][x].getTranslateX(), tileArray[y][x].getTranslateY());
			resetClickable();
			updatePlayerPosition();
			if(currentGameState.getMovesLeftForCurrentPlayer()>0) {
				setMoveableTiles();
			}else {
				currentGame.endTurn();
			    updateGameState();
			    newTurn();
			    System.out.println("turn ends");
			}
			
			
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

	/**
	* Resets clickable allowing the player to interact
	* with clickable and updating board.
	*/
	public static void resetClickable() {
		System.out.println("================================================");
		for (Box tile : clickAble) {
			tile.setOnMouseClicked(null);
			tile.scaleXProperty().set(1);
			tile.scaleYProperty().set(1);
		}
		clickableAnime.pause();
		clickAble.clear();
		updateBoard();
		/*try {
			updatePlayerPosition();
		} catch (IllegalMove e) {
			e.printStackTrace();
			displayErrorMessage("Cant move player this way");
		}*/
	}

	/**
	* Works out which directionn the current players tile is
	* being inserted in and removes the tile that gets pushed 
	* out in either deriction.
	*/
	public static void pushTileAnimation(int rows, int columns, int orientation) {
		System.out.println("============ Active Placeable Type : " + activePlaceable.getType() + " Ori: " + activePlaceable.getOrientation());
		
		gameObjects.getChildren().remove(arrows);
		arrows.getChildren().clear();
		hideInventory();
        
		Box tileToRemove = null;
		int starting, ending, incValue, arrayX, arrayY;
		double X, Y;
		if (orientation == 2 || orientation == 3) {

			if (orientation == 2) {
				starting = 0;
				ending = boardHeight - 1;
				incValue = 1;
				arrayX = columns;
			} else {
				starting = boardHeight - 1;
				ending = 0;
				incValue = -1;
				arrayX = columns;
			}
			// System.out.println("S: " + starting + " ending: " + ending+ " inc:
			// "+incValue);
			System.out.println(arrayX + " " + starting + " " + tileArray[arrayX][starting]);
			tileToRemove = tileArray[arrayX][starting];
			X = tileToRemove.getTranslateX();
			Y = tileToRemove.getTranslateY();
			for (int i = starting; i != ending; i += incValue) {
				// System.out.println("R: "+i+" S: " + starting + " ending: " + ending+ " inc:
				// "+incValue);
				Box newTile = tileArray[arrayX][(i + incValue)];
				moveTile(X, Y, newTile, false);
				tileArray[arrayX][i] = newTile;
				// System.out.println("R: "+i);
				X = newTile.getTranslateX();
				Y = newTile.getTranslateY();
			}
		} else {
			if (orientation == 0) {
				starting = 0;
				ending = boardWidth - 1;
				incValue = 1;
				arrayY = rows;
			} else {
				starting = boardWidth - 1;
				ending = 0;
				incValue = -1;
				arrayY = rows;
			}
			// System.out.println("S: " + starting + " ending: " + ending+ " inc:
			// "+incValue);
			tileToRemove = tileArray[starting][arrayY];
			X = tileToRemove.getTranslateX();
			Y = tileToRemove.getTranslateY();
			for (int i = starting; i != ending; i += incValue) {
				// System.out.println("R: "+i+" S: " + starting + " ending: " + ending+ " inc:
				// "+incValue);
				Box newTile = tileArray[(i + incValue)][arrayY];
				moveTile(X, Y, newTile, false);
				tileArray[i][arrayY] = newTile;
				// System.out.println("R: "+i);
				X = newTile.getTranslateX();
				Y = newTile.getTranslateY();
			}
		}
		// vertical

		tiles.getChildren().remove(tileToRemove);
		pushNewTile(X, Y, rows, columns, true);
	}

	/** 
	* Puts the tile that the current player has pulled 
	* from the bag in the correct space.
	*/
	public static Box pushNewTile(double x, double y, int rows, int columns, boolean last) {
		//System.out.println("Push "+activePlaceable.getType());
		Box newTile = objectFactory.makeTile(activePlaceable);
		newTile.setTranslateX(1000);
		newTile.setTranslateY(600);
		newTile.setTranslateZ(0);
		tiles.getChildren().add(newTile);
		moveTile(x, y, newTile, last);
		tileArray[columns][rows] = newTile;
		return newTile;
	}

	public static void moveTile(double x, double y, Box moveTile, boolean last) {
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(500), moveTile);
		tileMove.setToX(x);
		tileMove.setToY(y);
		if (last) {
			tileMove.setOnFinished(e -> {
				updateBoard();
				activePlaceable=null;
				try {
					updatePlayerPosition();
				} catch (IllegalMove e1) {
					
					e1.printStackTrace();
				}
				    
				    PauseTransition hold = new PauseTransition(Duration.millis(1000));
					hold.setOnFinished(lamda -> {
						showInventory();
					});
					hold.play();
			});
		}
		tileMove.play();
	}

	/**
	* Gets all the action tiles the current player owns.
	* Implements the action tiles when the current player
	* chooses to use them.
	* Implements the graphics of action tiles.
	*/
	public static void showInventory() {
		updateGameState();
		actionTile.clear();
		actionTilesOwned = currentGameState.getActionTileForPlayer(currentGameState.getCurPlayer());
		clickableAnime=new ParallelTransition();
		selectedTile = null;
		boolean actionTileObtained[] = { false, false, false, false };
		inventory = new Group();
		tileInventory = new ArrayList<Box>();
		
		Box skipB=objectFactory.makeSkipButton();
		skipB.setTranslateY(400);
		skipB.setTranslateZ(-5);
		skipB.setOnMouseClicked(e->{
			hideInventory();
			resetClickable();
			setMoveableTiles();
		});
		
		Box inventoryBase = new Box(90, 500, 10);
		PhongMaterial baseTexture = new PhongMaterial(Color.BROWN);
		inventoryBase.setMaterial(baseTexture);
		inventory.setTranslateY(400);
		inventory.setTranslateZ(-50);
		inventory.getChildren().addAll(inventoryBase,skipB);
		double y = -200;

		for (ActionTile actionTile : actionTilesOwned) {
			//System.out.println(actionTile.getType());
			if (actionTile.getType() == TileType.Fire) {
				actionTileObtained[0] = true;
			} else if (actionTile.getType() == TileType.Ice) {
				actionTileObtained[1] = true;
			} else if (actionTile.getType() == TileType.DoubleMove) {
				actionTileObtained[2] = true;
			} else if (actionTile.getType() == TileType.BackTrack) {
				actionTileObtained[3] = true;
			}
		}
		
		for (int i = 0; i < actionTilesOwned.size(); i++) {
			if(actionTilesOwned.get(i)!=drawnTile) {
			
				Box acTile = objectFactory.makeTileInInventory(actionTilesOwned.get(i).getType());
				actionTile.add(acTile);
				acTile.setTranslateX(inventoryBase.getTranslateX() - 10);
				acTile.setTranslateY(y);
				acTile.setTranslateZ(inventoryBase.getTranslateZ() - 50);
				if (actionTilesOwned.get(i).getType()==TileType.Fire) {
					acTile.setOnMouseClicked(e -> {
						// what to do when a fire tile is clicked
						if (selectedTile != acTile) {
							setSelectedTile(acTile);
							resetActionTileClickable();
							if(clickableAnime!=null&&clickableAnime.getStatus()==Animation.Status.RUNNING) {
								resetClickable();
								}
							playFireTile();
						} 
						System.out.println("Click fire");
					});
				} else if (actionTilesOwned.get(i).getType()==TileType.Ice) {
					acTile.setOnMouseClicked(e -> {
						// what to do when an ice tile is clicked
						if (selectedTile != acTile) {
							setSelectedTile(acTile);
							resetActionTileClickable();
							if(clickableAnime!=null&&clickableAnime.getStatus()==Animation.Status.RUNNING) {
								resetClickable();
								}
							playIceTile();
						} 
						System.out.println("Click ice");
					});
				} else if (actionTilesOwned.get(i).getType()==TileType.DoubleMove) {
					acTile.setOnMouseClicked(e -> {
						// what to do when a doubleMove tile is clicked
						if (selectedTile != acTile) {
							setSelectedTile(acTile);
							resetActionTileClickable();
							if(clickableAnime!=null&&clickableAnime.getStatus()==Animation.Status.RUNNING) {
							resetClickable();
							}
							animationPlayTile(playerPlaying.getTranslateX(),playerPlaying.getTranslateY(),"wind");
							playDoubleMove();
							
						}
						System.out.println("Click double");
					});
				} else if (actionTilesOwned.get(i).getType()==TileType.BackTrack) {
					acTile.setOnMouseClicked(e -> {
						// what to do when a Backtrack tile is clicked
						if (selectedTile != acTile) {
							setSelectedTile(acTile);
							resetActionTileClickable();
							if(clickableAnime!=null&&clickableAnime.getStatus()==Animation.Status.RUNNING) {
							resetClickable();
							}
							playBacktrack();
						}
						System.out.println("Click back");
						//setMoveableTiles();
					});
				}
			
				tileInventory.add(acTile);
				inventory.getChildren().add(acTile);
				y += 70;
			}
		}
		gameObjects.getChildren().add(inventory);
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(1000), inventory);
		tileMove.setFromX(1200);
		tileMove.setToX(950);
		tileMove.play();

	}
	
	private static void resetActionTileClickable() {
		for(Box t:actionTile) {
		    t.setOnMouseClicked(null);
		}
	}
	

	/** 
	* Shows the tile pulled from the silk bag onto the screen.
	*/
	private static void showDrawTile(Tile drawTile) {
		Image tileImage = null;
		if (drawTile.getType() == TileType.Straight) {
			tileImage = new Image("/img/texture/Straight.png");
		} else if (drawTile.getType() == TileType.Corner) {
			tileImage = new Image("/img/texture/Corner.png");
		} else if (drawTile.getType() == TileType.TShaped) {
			tileImage = new Image("/img/texture/TShaped.png");
		} else if (drawTile.getType() == TileType.Fire) {
			tileImage = new Image("/img/texture/fireTile.jpg");
		} else if (drawTile.getType() == TileType.Ice) {
			tileImage = new Image("/img/texture/IceTile.jpg");
		} else if (drawTile.getType() == TileType.BackTrack) {
			tileImage = new Image("/img/texture/backTrackTile.jpg");
		} else if (drawTile.getType() == TileType.DoubleMove) {
			tileImage = new Image("/img/texture/doubleMoveTile.jpg");
		}
		Label tileType = showTileType(drawTile);
		tileType.setAlignment(Pos.CENTER);
		tileType.setLayoutX(42);
		tileType.setLayoutY(200);
		ImageView tileIcon = new ImageView();
		tileIcon.setImage(tileImage);
		tileIcon.setFitHeight(50);
		tileIcon.setFitWidth(50);
		tileIcon.setLayoutX(42);
		tileIcon.setLayoutY(225);
		sRightMenuPane.getChildren().add(tileIcon);
		ScaleTransition appearTile = new ScaleTransition(Duration.seconds(1), tileIcon);
		appearTile.setFromX(0);
		appearTile.setFromY(0);
		appearTile.setToX(1);
		appearTile.setToY(1);
		appearTile.setOnFinished(e -> {
			sRightMenuPane.getChildren().add(tileType);
		});
		PauseTransition hold = new PauseTransition(Duration.millis(1000));
		hold.setOnFinished(e -> {
			sRightMenuPane.getChildren().remove(tileType);
		});

		ScaleTransition disappearTile = new ScaleTransition(Duration.seconds(1), tileIcon);
		disappearTile.setFromX(1);
		disappearTile.setFromY(1);
		disappearTile.setToX(0);
		disappearTile.setToY(0);
		SequentialTransition showTileSeq = new SequentialTransition(appearTile, hold, disappearTile);
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
	 * Action tile methods
	 */

	public static void playFireTile() {
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				final int finalX = x;
				
				clickableAnime.getChildren().add(animateTile(tileArray[y][x]));
				tileArray[y][x].setOnMouseClicked(e -> {
					placeFireTile(finalY, finalX);
					
				});
				clickAble.add(tileArray[y][x]);
			}
		}
		clickableAnime.play();
	}

	public static void placeFireTile(int y, int x) {
		//System.out.println("Fire y: "+y+" x: "+x);
		Fire fireTile = null;
		for (ActionTile actionTile : actionTilesOwned) {
			if (actionTile.getType() == TileType.Fire) {
				fireTile = (Fire) actionTile;
			}
		}
		try {
			//System.out.println("Fire tile"+fireTile);
			currentGameState = currentGame.newPlayFire(x, y);
			animationPlayTile(tileArray[y][x].getTranslateX(),tileArray[y][x].getTranslateY(),"fire");
			updateBoard();
			//resetClickable();
			hideInventory();
			resetClickable();
			setMoveableTiles();
			
		} catch (IncorrectTileTypeException | IllegalFireException e) {
			displayErrorMessage("Cannot place fire on player");
			
		}
	}

	public static void playIceTile() {
		//System.out.println("Call ice tile");
		for (int y = 0; y < boardHeight; y++) {
			final int finalY = y;
			for (int x = 0; x < boardWidth; x++) {
				final int finalX = x;
				clickableAnime.getChildren().add(animateTile(tileArray[y][x]));
				tileArray[y][x].setOnMouseClicked(e -> {
					//System.out.println("=========================PLay ice X: "+finalX+"Y: "+finalY);
					placeIceTile(finalY, finalX);
					
				});
				clickAble.add(tileArray[y][x]);
			}
		}
		clickableAnime.play();
	}

	private static void placeIceTile(int y, int x) {
		//System.out.println("ice y: "+y+" x: "+x);
		Ice iceTile = null;
		for (ActionTile actionTile : actionTilesOwned) {
			if (actionTile.getType() == TileType.Ice) {
				iceTile = (Ice) actionTile;
			}
		}
		try {
			currentGameState = currentGame.newPlayIce(x, y);
			animationPlayTile(tileArray[y][x].getTranslateX(),tileArray[y][x].getTranslateY(),"ice");
			updateBoard();
			hideInventory();
			resetClickable();
			setMoveableTiles();
		} catch (IncorrectTileTypeException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public static void playDoubleMove() {
		//System.out.println("Double Move played");
		DoubleMove dmTile=null;
		for(ActionTile actionTile : actionTilesOwned) {
			if(actionTile.getType() == TileType.DoubleMove) {
				dmTile=(DoubleMove) actionTile;
			}
		}
		currentGame.playDoubleMove(dmTile);
		
		hideInventory();
		resetClickable();
		setMoveableTiles();
	}
	

	public static void playBacktrack() {
		for (int p=0;p<playerObjectArray.length;p++) {
			if(playerObjectArray[p] != playerPlaying) {
				final Group fplayer=playerObjectArray[p];
				System.out.println("Making "+p+" clickable");
				playerObjectArray[p].setMouseTransparent(false);
				playerObjectArray[p].setOnMouseClicked(e -> {
					System.out.println("Back Track echo");
					useBacktrack(fplayer);
				});
			}
		}
	}
	
	public static void useBacktrack(Group playerObject) {
		playerObject.setMouseTransparent(true);
		playerObject.setOnMouseClicked(null);
		BackTrack backtrackTile = null;
		for(int playerNo=0;playerNo<playerObjectArray.length;playerNo++) {
			if(playerObjectArray[playerNo]==playerObject) {
				playerNoGotBackTrack=playerNo;
			}
		}
		for (ActionTile actionTile : actionTilesOwned) {
			if (actionTile.getType() == TileType.BackTrack) {
				backtrackTile = (BackTrack) actionTile;
			}
		}
		try {
			System.out.println("Player got backTrack: "+playerNoGotBackTrack);
		    currentGame.playBackTrack(backtrackTile, playerNoGotBackTrack);
			hideInventory();
			resetClickable();
			updatePlayerPosition();
			setMoveableTiles();
		} catch  (IllegalBackTrackException | IllegalMove e) {
			e.printStackTrace();
			displayErrorMessage("Cannot back track");
		}
	}
	
	/**
	 * Animation
	 */
	private static void hideInventory() {
		//System.out.println("Hide inventory");
		TranslateTransition tileMove = new TranslateTransition(Duration.millis(1000), inventory);
		tileMove.setFromX(950);
		tileMove.setToX(1200);
		tileMove.setOnFinished(e -> {
			inventory.getChildren().clear();
			gameObjects.getChildren().remove(inventory);

		});
		tileMove.play();
	}

	private static void setSelectedTile(Box newSelected) {
		if (selectedTile != null) {
			selectedTile.setWidth(70);
			selectedTile.setHeight(70);
		}
		newSelected.setWidth(90);
		newSelected.setHeight(90);
		
	}
	
	public static void teleportPlayer(Group player, double x, double y) {
		Box teleCylinder = new Box(120, 120, 200);
		PhongMaterial teleClinderTexture = new PhongMaterial();
		teleClinderTexture.setSelfIlluminationMap(new Image("/img/texture/teleCylinder.png"));
		teleCylinder.setMaterial(teleClinderTexture);
		teleCylinder.setTranslateZ(-60);
		teleCylinder.setVisible(false);

		PauseTransition hold = new PauseTransition(Duration.millis(1000));
		hold.setOnFinished(e -> {
			teleCylinder.setVisible(true);
		});
		ScaleTransition teleFadeIn = new ScaleTransition(Duration.millis(1000), teleCylinder);
		teleFadeIn.setFromX(0);
		teleFadeIn.setFromY(0);
		teleFadeIn.setToX(1);
		teleFadeIn.setToY(1);
		teleFadeIn.setOnFinished(e -> {
			player.setTranslateX(x);
			player.setTranslateY(y);
		});

		ScaleTransition teleFadeOut = new ScaleTransition(Duration.millis(1000), teleCylinder);
		teleFadeOut.setFromX(1);
		teleFadeOut.setFromY(1);
		teleFadeOut.setToX(0);
		teleFadeOut.setToY(0);
		teleFadeOut.setOnFinished(e -> {
			player.getChildren().remove(teleCylinder);
		});

		animationPlayTile(player.getTranslateX(), player.getTranslateY(), "teleport");
		SequentialTransition seqTransition = new SequentialTransition(hold, teleFadeIn, teleFadeOut);
		seqTransition.play();
		player.getChildren().add(teleCylinder);
	}

	public static void animationPlayTile(double x, double y, String playType) {
		Box elementType = new Box(200, 200, 2);
		elementType.translateXProperty().set(x);
		elementType.translateYProperty().set(y);
		elementType.translateZProperty().set(-5);
		elementType.setMouseTransparent(true);
		Cylinder magicCircle = new Cylinder(200, 1);
		magicCircle.translateXProperty().set(x);
		magicCircle.translateYProperty().set(y);
		magicCircle.translateZProperty().set(elementType.getTranslateZ() - 5);
		Transform rotateMC = new Rotate(90, Rotate.X_AXIS);
		magicCircle.getTransforms().add(rotateMC);
		magicCircle.setMouseTransparent(true);

		PhongMaterial imageTextureElement = new PhongMaterial();
		PhongMaterial imageTextureMC = new PhongMaterial();
		if (playType.equals("fire")) {
			imageTextureElement.setDiffuseMap(new Image("/img/texture/fireMagic.png"));
			imageTextureMC.setDiffuseMap(new Image("/img/texture/fireMagicCircle.png"));
			imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/fireMagicCircle.png"));
		} else if (playType.equals("ice")) {
			imageTextureElement.setDiffuseMap(new Image("/img/texture/frozenMagic.png"));
			imageTextureMC.setDiffuseMap(new Image("/img/texture/iceMagicCircle.png"));
			imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/iceMagicCircle.png"));
		} else if (playType.equals("wind")) {
			imageTextureElement.setDiffuseMap(new Image("/img/texture/windMagic.png"));
			imageTextureMC.setDiffuseMap(new Image("/img/texture/windMagicCircle.png"));
			imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/windMagicCircle.png"));
			elementType.setWidth(100);
			elementType.setHeight(100);
			magicCircle.setRadius(100);
		} else {
			imageTextureElement.setDiffuseMap(new Image("/img/texture/backTrackMagic.png"));
			imageTextureMC.setDiffuseMap(new Image("/img/texture/portalMagicCircle.png"));
			imageTextureMC.setSelfIlluminationMap(new Image("/img/texture/portalMagicCircle.png"));
			elementType.setWidth(100);
			elementType.setHeight(100);
			magicCircle.setRadius(100);
		}
		elementType.setMaterial(imageTextureElement);
		magicCircle.setMaterial(imageTextureMC);
		gameObjects.getChildren().addAll(elementType, magicCircle);

		ScaleTransition elementFade = new ScaleTransition(Duration.millis(200), elementType);
		elementFade.setFromX(0);
		elementFade.setFromY(0);
		elementFade.setToX(1);
		elementFade.setToY(1);

		ScaleTransition mcFade = new ScaleTransition(Duration.millis(1000), magicCircle);
		mcFade.setFromX(0);
		mcFade.setFromY(0);
		mcFade.setToX(1);
		mcFade.setToY(1);

		RotateTransition mcRotate = new RotateTransition(Duration.millis(1000), magicCircle);
		mcRotate.setByAngle(360);

		ParallelTransition mcTrans = new ParallelTransition(elementFade, mcFade, mcRotate);

		PauseTransition hold = new PauseTransition(Duration.millis(1000));

		ScaleTransition mcBackFade = new ScaleTransition(Duration.millis(500), magicCircle);
		mcBackFade.setFromX(1);
		mcBackFade.setFromY(1);
		mcBackFade.setToX(0);
		mcBackFade.setToY(0);

		ScaleTransition elementBackFade = new ScaleTransition(Duration.millis(200), elementType);
		elementBackFade.setFromX(1);
		elementBackFade.setFromY(1);
		elementBackFade.setToX(0);
		elementBackFade.setToY(0);

		ParallelTransition mcBackTrans = new ParallelTransition(mcBackFade, elementBackFade);

		SequentialTransition seqTransition = new SequentialTransition(mcTrans, hold, mcBackTrans);
		seqTransition.setOnFinished(e -> {
			gameObjects.getChildren().removeAll(elementType, magicCircle);
		});

		seqTransition.play();
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
		BackHomeButton.setStyle(
				"-fx-background-color: BLACK; -fx-background-radius: 5px; -fx-border-color: GOLDENROD; -fx-border-radius: 5px; -fx-border-width: 2;");
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
		QuitButton.setStyle(
				"-fx-background-color: BLACK; -fx-background-radius: 5px; -fx-border-color: GOLDENROD; -fx-border-radius: 5px; -fx-border-width: 2;");
	}

}
