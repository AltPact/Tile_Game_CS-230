import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

	public static Game readGameFile(String filename) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f);

			/* read game meta data */
			int height = s.nextInt();
			int width = s.nextInt();
			int numPlayers = s.nextInt();
			int curPlayer = s.nextInt(); // TODO: DUPLICATE DATA, this is included in curState
			int movesLeftForCurrent = s.nextInt();
			boolean isGoalHit = s.nextBoolean();

			/* read player data */
			/* TODO: this data is all duplicates of data in curState (except colour), and can't instantiate an array of ActionTiles so can't construct player pieces */
			for (int p = 0; p < numPlayers; p++) {
				int playerX = s.nextInt();
				int playerY = s.nextInt();
				String playerColour = s.next();
				//
			}

			/* read current game state */
			GameState curState = readGameState(s, height, width, numPlayers);

			/* read past game states */
			ArrayList<GameState> pastStates = new ArrayList<GameState>();
			int numPastStates = s.nextInt();
			for (int state = 0; state < numPastStates; state++) {
				pastStates.add(readGameState(s, height, width, numPlayers));
			}

			/* read the silk bag */

			int[] numActionTiles = new int[] {0, 0, 0, 0};
			int[] numPlaceableTiles = new int[] {0, 0, 0};
			int numBagTiles = s.nextInt();
			for (int bagTile = 0; bagTile < numBagTiles; bagTile++) {
				int curTile = s.nextInt();
				if (curTile < 4) {  // if action tile
					numActionTiles[curTile] = numActionTiles[curTile] + 1;
				} else {  // if placeable tile
					numPlaceableTiles[curTile % 3] = numPlaceableTiles[curTile % 3] + 1;
				}
			}
			SilkBag bag = new SilkBag(numActionTiles, numPlaceableTiles);

			return new Game(curState, pastStates, bag, players, curPlayer, movesLeftForCurrent, tilesInAction);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		}
	}



	private static GameState readGameState(Scanner s, int height, int width, int numPlayers) {
		boolean isGoalHit = s.nextBoolean();
		int curPlayer = s.nextInt();

		Placeable[][] stateTiles = new Placeable[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				TileType type = TileType.valueOf(s.next());
				boolean isFixed = s.nextBoolean();
				int orientation = s.nextInt();
				stateTiles[y][x] = new Placeable(type, type == TileType.Goal, isFixed, orientation);
			}
		}

		int[][] playerPositions = new int[numPlayers][2];
		for (int p = 0; p < numPlayers; p++) {
			playerPositions[p][0] = s.nextInt();
			playerPositions[p][1] = s.nextInt();
		}

		// TODO: action tiles are currently abstract, you can't instantiate them to put them in an array.
		ArrayList<ActionTile>[] curActionTilesForEachPlayer = new ArrayList[numPlayers];
		for (int p = 0; p < numPlayers; p++) {
			curActionTilesForEachPlayer[p]= new ArrayList<ActionTile> ();
			int numActionTilesInHand = s.nextInt();
			for (int a = 0; a < numActionTilesInHand; a++) {
				TileType actionTileType = TileType.valueOf(s.next());
				curActionTilesForEachPlayer[p].add(new ActionTile(actionTileType));
			}
		}

		return new GameState(stateTiles, playerPositions, curActionTilesForEachPlayer, curPlayer, isGoalHit);
	}

	public static void oldReadBoardFile(String filename) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f);
			int width = s.nextInt();
			int height = s.nextInt();
			/* read all fixed tiles */
			int numFixedTiles = s.nextInt();
			Tile[] fixedTiles = new Tile[numFixedTiles];
			int[][] fixedTileCoords = new int[numFixedTiles][2];
			for (int t = 0; t < numFixedTiles; t++) {
				int x = s.nextInt();
				int y = s.nextInt();
				String tileTypeStr = s.next();
				TileType tileTypeEnum = TileType.valueOf(tileTypeStr);
				int orientation = s.nextInt();
				boolean isGoal = s.nextBoolean();
				fixedTiles[t] = new Placeable(tileTypeEnum, isGoal, true);
				fixedTiles[t].setOrientation(orientation);
				fixedTileCoords[t][0] = x;
				fixedTileCoords[t][1] = y;
			}
			/* read all tiles to go in the silk bag */
			/* TODO: Tile is an abstract class, can't be constructed using only tileType. Stored as strings for now */
			int numBagTiles = s.nextInt();
			String[] bagTiles = new String[numBagTiles];
			for (int t = 0; t < numBagTiles; t++) {
				bagTiles[t] = s.next();
			}
			/* read all action tiles on the board */
			/* TODO: these should be read at the same time as fixedTiles. file format should be changed later. */
			int numActionTiles = s.nextInt();
			String[] actionTiles = new String[numActionTiles]; // TODO: this needs to be not a string.
			for (int t = 0; t < numActionTiles; t++) {
				// TODO: much more information will be needed than just tile type, will need to talk about this
				actionTiles[t] = s.next();
			}

			s.close();
			return;  // TODO: need to construct Game object
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/* public static void oldReadGameFile(String filename) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f);

			int numPlayers = s.nextInt();
			PlayerPiece[] players = new PlayerPiece[numPlayers];
			TileType[][] playerHands = new String[numPlayers][10]; // assumes max hand size of 10
			
			for (int p = 0; p < numPlayers; p++) {
				String name = s.next();
				int x = s.nextInt();
				int y = s.nextInt();
				String colour = s.next();
				boolean backtrackApplied = s.nextBoolean();
				players[p] = new PlayerPiece(name, x, y, colour, backtrackApplied);
				
				int actionTilesOwned = s.nextInt();
				for (int t = 0; t < actionTilesOwned; t++) {
					String tileTypeStr = s.next();
					TileType tileTypeEnum = TileType.valueOf(tileTypeStr);
					String actionTileType = s.next();  // TODO: probably need to remove this
					boolean drawnThisTurn = s.hasNextBoolean(); // TODO: need to implement this
					playerHands[p][t] = tileTypeEnum;
				}
			}
			
			int curTurn = s.nextInt();

			int silkBagSize = s.nextInt();
			String[] silkBag = new String[silkBagSize];  // TODO: probably need to make this not be a string
			for (int t = 0; t < silkBagSize; t++) {
				silkBag[t] = s.next();
			}			

			int boardWidth = s.nextInt();  // TODO: maybe make reading this data a function to be reused when reading previous states?
			int boardHeight = s.nextInt();
			Tile[][] currentBoard = new Tile[boardWidth][boardHeight];
			for (int x = 0; x < boardWidth; x++) {
				for (int y = 0; y < boardHeight; y++) {
					String tileTypeStr = s.next();
					TileType tileTypeEnum = TileType.valueOf(tileTypeStr);
					int rotation = s.nextInt();
					boolean isFixed = s.hasNextBoolean();
					String owner = s.next();
					boolean onFire = s.hasNextBoolean();
					boolean frozen = s.hasNextBoolean();
					int timeRemaining = s.nextInt();
					if (tileTypeEnum == TileType.Ice) {
						currentBoard[x][y] = new Ice(owner, numPlayers);
						// TODO: no function exists yet for the time remaining to be set on initialisation
					} else if (tileTypeEnum == TileType.Fire) {
						currentBoard[x][y] = new Fire(owner, numPlayers);
						// TODO: see above
					} else {
						currentBoard[x][y] = new Placeable(tileType, isGoal, isFixed, orientation);
					}
				}
			}

			int previousBoardStates = s.nextInt();
			Tile[][][] previousBoards = new Tile[previousBoardStates][boardWidth][boardHeight];
			for (int b = 0; b < previousBoardStates; b++) {
				for (int x = 0; x < boardWidth; x++) {
					for (int y = 0; y < boardHeight; y++) {
						String tileTypeStr = s.next();
						TileType tileTypeEnum = TileType.valueOf(tileTypeStr);
						int rotation = s.nextInt();
						boolean isFixed = s.hasNextBoolean();
						String owner = s.next();
						boolean onFire = s.hasNextBoolean();
						boolean frozen = s.hasNextBoolean();
						int timeRemaining = s.nextInt();
						if (tileTypeEnum == TileType.Ice) {
							previousBoards[b][x][y] = new Ice(owner, numPlayers);
							// TODO: no function exists yet for the time remaining to be set on initialisation
						} else if (tileTypeEnum == TileType.Fire) {
							previousBoards[b][x][y] = new Fire(owner, numPlayers);
							// TODO: see above
						} else {
							previousBoards[b][x][y] = new Placeable(tileType, isGoal, isFixed, orientation);
						}
					}
				}
			}
			
			s.close();
			return; // TODO: need to construct Game object
		} catch (FileNotFoundException e){
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	} */

}
