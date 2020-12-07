import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
/**
 * A class that reads a game board file or a game file and constructs a game object.
 * 
 * @author Sam Steadman (1910177), Alex Ullman (851732) and Joshua Sinderberry (851800)
 * @version 2
 *
 */
public class GameFileReader {

	/**
	 * Reads a file describing a game in progress, constructs a Game using this information.
	 *
	 * @param a file that should be read.
	 * @return An instantiated Game object.
	 */
	public static Game readGameFile(File f) {
		Scanner s = null;
		try {
			s = new Scanner(f).useDelimiter(",");
			GameState curState = new GameState();
			
			curState.isGoalHit(s.nextBoolean());
			//Current player and moves remaining.
			curState.setCurrentPlayer(s.nextInt(), s.nextInt());
			curState.setHasPlayerInsertedTile(s.nextBoolean());
			
			int numPlayers = s.nextInt();
			
			PlayerPiece[] players = new PlayerPiece[numPlayers];
			
			/* read player data */
			for (int p = 0; p < numPlayers; p++) {
				int playerX = s.nextInt();  
				int playerY = s.nextInt();
				String playerColour = s.next();
				Boolean backtrackApplied = s.nextBoolean();
				String name = s.next();
				File pDataFile = new File("./data/playerdata/" + name);
				PlayerData pData = PlayerDataFileReader.readFile(pDataFile);
				players[p] = new PlayerPiece(playerX, playerY, playerColour, backtrackApplied, pData);
			}
			
			readCurrentGameState(curState, s, players);


			/* read the silk bag */
			SilkBag bag = readSilkBag(s);

			return new Game(curState, curState.getPastStates(), bag, players);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		} finally {
			s.close();
		}
	}

	/**
	 * Reads a file describing a "template" for a game - a board not yet fully populated with tiles that can be used
	 * to start a new game. Constructs a Game with this information.
	 *
	 * @param f the file that should be read.
	 * @param players Player Pieces, should instantiated but can have X and Y set to 0.   
	 * @return An instantiated Game object.
	 */
	public static Game readBoardFile(File f, PlayerPiece[] players) {
		Scanner s = null;
		try {
			s = new Scanner(f).useDelimiter(",");
			int numPlayers = players.length;
			
			int p;
			for(p = 0; p < numPlayers; p++) {
				players[p].setX(s.nextInt());
				players[p].setY(s.nextInt());
			}
			// if a board file is being read that supports more players than are needed, skip unneeded player positions
			
			while(p < 4) {
				s.nextInt();
				s.nextInt();
				p++;
			}
			
			/* read board meta data */
			int height = s.nextInt();
			int width = s.nextInt();
			int numOfFixedTiles = s.nextInt();
			Placeable[][] tiles = new Placeable[height][width];
			for(int t = 0; t < numOfFixedTiles; t++) {
				int x = s.nextInt();
				int y = s.nextInt();
				int orientation = s.nextInt();
				boolean isGoal = s.nextBoolean();
				TileType type = TileType.valueOf(s.next());
				Placeable newTile = new Placeable(type, isGoal, true, orientation);
				tiles[y][x] = newTile;
			}
			
			/* read the silk bag */
			SilkBag bag = readSilkBag(s);
			
			Board board = new Board(width, height, tiles);
			board.fillBoard(bag);

			return new Game(bag, players, board);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		} finally {
			s.close();
		}
	}

	/**
	 * @private 
	 * Reads a silk bag file. The amount of tiles of each type that should be present.
	 * @param s Scanner
	 * @return The new silk bag.
	 */
	private static SilkBag readSilkBag(Scanner s) {
		int[] numActionTiles = new int[4];
		int[] numPlaceableTiles = new int[3];
		for (int a = 0; a < 4; a++) {
			numActionTiles[a] = s.nextInt();
		}
		for (int p = 0; p < 3; p++) {
			numPlaceableTiles[p] = s.nextInt();
		}

		return new SilkBag(numActionTiles, numPlaceableTiles);
	}


	/**
	 * @private
	 * Reads a "GameState" object from a file. When loading a game that is in-progress, this will likely
	 * be called multiple times, once for each previous state of the game, so that Backtrack can work.
	 * 
	 * @param curState a game state object, that can be filled. 
	 * @param s File scanner to read data through
	 * @param players Array of players to be referred to as the owners of action tiles.
	 */
	private static void readCurrentGameState(GameState curState, Scanner s, PlayerPiece[] players) {
		
		int width = s.nextInt();
		int height = s.nextInt();
		
		
		/* read tiles */
		Placeable[][] stateTiles = new Placeable[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				boolean isFull = s.nextBoolean();
				if (isFull) {
					TileType type = TileType.valueOf(s.next());
					boolean isFixed = s.nextBoolean();
					int orientation = s.nextInt();
					boolean isOnFire = s.nextBoolean();
					boolean isFrozen = s.nextBoolean();
					Placeable curTile = new Placeable(type, type == TileType.Goal, isFixed, orientation);
					if (isOnFire) {
						curTile.putOnFire();
					}
					if (isFrozen) {
						curTile.freeze();
					}
					stateTiles[y][x] = curTile;
				} else {  // if the space is empty, leave it null to be filled from the silk bag by Board
					stateTiles[y][x] = null;
				}
			}
		}
		
		
		curState.setBoard(stateTiles, width, height);
		ArrayList<ActionTile>[] actionTilesOwnedForEachPlayer = new ArrayList[players.length];
		
		/* Read the action tiles for each player */
		for(int p = 0; p < players.length; p++) {
			int numOfActionTilesOwned = s.nextInt();
			ArrayList<ActionTile> actionTilesOwned = new ArrayList<ActionTile>();
			for (int i = 0; i < numOfActionTilesOwned; i++) {
				TileType t = (TileType.valueOf(s.next()));
				if (t == TileType.BackTrack) {
					actionTilesOwned.add(new BackTrack(players[p]));
				} else if (t == TileType.DoubleMove) {
					actionTilesOwned.add(new DoubleMove(players[p]));
				} else if (t == TileType.Ice) {
					actionTilesOwned.add(new Ice(players[p], players.length));
				} else if (t == TileType.Fire) {
					actionTilesOwned.add(new Fire(players[p], players.length));
				} 		
			}

			actionTilesOwnedForEachPlayer[p] = actionTilesOwned;
		}
		
		curState.setActionTilesForPlayers(actionTilesOwnedForEachPlayer);
		
		/* Reads all of the action tiles in play at save time */
		int numOfTilesInAction = s.nextInt();
		ArrayList<ActionTilePlaceable> tilesInAction = new ArrayList<ActionTilePlaceable>();
		for(int i = 0; i < numOfTilesInAction; i++) {
			TileType t = (TileType.valueOf(s.next()));
			int owner = s.nextInt();
			int timeRemaing = s.nextInt();
			ActionTilePlaceable tile = null;
			if (t == TileType.Ice) {
				tile = new Ice(players[owner], players.length);
			} else if (t == TileType.Fire) {
				tile = new Fire(players[owner], players.length);
			}
			tile.setTimeRemaining(timeRemaing);
			tilesInAction.add(tile);
		}
		curState.setTilesInAction(tilesInAction);
		
		ArrayList<GameState> pastStates = new ArrayList<GameState>();
		
		int numOfPastStates = s.nextInt();
		for(int i = 0; i < numOfPastStates; i++) {
			pastStates.add(readPastState(s, players.length));
		}
		
		curState.setPastStates(pastStates);

		return;
	}
	
	/**
	 * A method that reads a past game state 
	 * @param s
	 * @param numOfPlayers
	 * @return
	 */
	private static GameState readPastState(Scanner s, int numOfPlayers) {
		GameState pastState = new GameState();
		//Current Player and the moves remaining
		pastState.setCurrentPlayer(s.nextInt(), s.nextInt());
		int[][] playerPos = new int[numOfPlayers][2];
		//Read the player positions in this game state.
		for(int p = 0; p < numOfPlayers; p++) {
			playerPos[p][0] = s.nextInt();
			playerPos[p][1] = s.nextInt();
		}
		pastState.setPlayerPositions(playerPos);
		return pastState;
	}
	/**
	 * @return a list of stored games files
	 */
	public String[] listGameFiles(){
		File f = new File ("./data/savedgames");
		return f.list();
	}
	
	/**
	 * @return a list of stored game boards.
	 */
	public String[] listBoardFiles(){
		File f = new File ("./data/gameboard");
		return f.list();
	}

}
