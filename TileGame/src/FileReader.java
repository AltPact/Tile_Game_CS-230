import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class FileReader {

	public static void readBoardFile(String filename) {
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
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
		
	public static void readGameFile(String filename) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f);
			/* read player data */
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
			/* read the silk bag */
			int silkBagSize = s.nextInt();
			String[] silkBag = new String[silkBagSize];  // TODO: probably need to make this not be a string
			for (int t = 0; t < silkBagSize; t++) {
				silkBag[t] = s.next();
			}			
			/* read the game's current board */
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
					if (tileTypeEnum = TileType.Ice) {
						currentBoard[x][y] = new Ice(owner, numPlayers);
						// TODO: no function exists yet for the time remaining to be set on initialisation
					} else if (tileTypeEnum = TileType.Fire) {
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
						if (tileTypeEnum = TileType.Ice) {
							previousBoards[b][x][y] = new Ice(owner, numPlayers);
							// TODO: no function exists yet for the time remaining to be set on initialisation
						} else if (tileTypeEnum = TileType.Fire) {
							previousBoards[b][x][y] = new Fire(owner, numPlayers);
							// TODO: see above
						} else {
							previousBoards[b][x][y] = new Placeable(tileType, isGoal, isFixed, orientation);
						}
					}
				}
			}
		} catch (FileNotFoundException e){
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
  }
}
