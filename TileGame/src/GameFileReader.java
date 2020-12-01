import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GameFileReader {

	/**
	 * Reads a file describing a game in progress, constructs a Game using this information.
	 *
	 * @param filename The name of the file to read.
	 * @return An instantiated Game object.
	 */
	public static Game readGameFile(String filename) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f).useDelimiter(",");
			GameState curState = new GameState();
			
			curState.isGoalHit(s.nextBoolean());
			curState.setCurrentPlayer(s.nextInt(), s.nextInt());
			
			int numPlayers = s.nextInt();
			
			PlayerPiece[] players = new PlayerPiece[numPlayers];
			
			/* read player data */
			for (int p = 0; p < numPlayers; p++) {
				String name = s.next();
				int playerX = s.nextInt();  // 0 and 1 may need to be swapped here? not sure if X/Y or Y/X
				int playerY = s.nextInt();
				String playerColour = s.next();
				Boolean backtrackApplied = s.nextBoolean();
				File pDataFile = new File(name); // TODO: needs to look in correct directory, will work for now
				PlayerData pData = PlayerDataFileReader.readFile(pDataFile);
				players[p] = new PlayerPiece(playerX, playerY, playerColour, backtrackApplied, pData);
			}
			
			readCurrentGameState(curState, s, players);


			/* read the silk bag */
			SilkBag bag = readSilkBag(s);

			s.close();
			return new Game(curState, curState.getPastStates(), bag, players);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a file describing a "template" for a game - a board not yet fully populated with tiles that can be used
	 * to start a new game. Constructs a Game with this information.
	 *
	 * @param filename The name of the file to read.
	 * @param playerNames The names of players which will be used to refer to PlayerData files.
	 * @return An instantiated Game object.
	 */
	public static Game readBoardFile(String filename, String[] playerNames) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f);
			int numPlayers = playerNames.length;
			PlayerPiece[] players = new PlayerPiece[numPlayers];

			/* read board meta data */
			int height = s.nextInt();
			int width = s.nextInt();

			/* read the starting state of the game */
			GameState startState = readGameState(s, height, width, players);

			/* read player data */
			for (int p = 0; p < numPlayers; p++) {
				File pDataFile = new File(playerNames[p]); // TODO: needs to look in correct directory, will work for now
				PlayerData pData = PlayerDataFileReader.readFile(pDataFile);
				int playerX = startState.getPlayersPositions()[p][0];  // 0 and 1 may need to be swapped here? not sure if X/Y or Y/X
				int playerY = startState.getPlayersPositions()[p][1];
				String playerColour = s.next();
				Boolean backtrackApplied = s.nextBoolean();
				players[p] = new PlayerPiece(playerX, playerY, playerColour, backtrackApplied, pData);
			}

			/* read the silk bag */
			SilkBag bag = readSilkBag(s);

			s.close();
			return new Game(startState, new ArrayList<GameState> (), bag, players);  // use empty ArrayList for pastStates since there are none
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		}
	}


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
	 * Reads a "GameState" object from a file. When loading a game that is in-progress, this will likely
	 * be called multiple times, once for each previous state of the game, so that Backtrack can work.
	 *
	 * @param s File scanner to read data through
	 * @param height Height of the board
	 * @param width Width of the board
	 * @param players Array of players to be referred to as the owners of action tiles.
	 * @return 
	 * @return An instantiated GameState object.
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
			curState.setActionTilesByPlayerNumber(actionTilesOwned, p);
		}
		
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
				tile.setTimeRemaining(timeRemaing);
				tilesInAction.add(tile);
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
	
	
	private static GameState readPastState(Scanner s, int numOfPlayers) {
		GameState pastState = new GameState();
		pastState.setCurrentPlayer(s.nextInt(), s.nextInt());
		int[][] playerPos = new int[numOfPlayers][2];
		for(int p = 0; p < numOfPlayers; p++) {
			playerPos[p][0] = s.nextInt();
			playerPos[p][1] = s.nextInt();
		}
		pastState.setPlayerPositions(playerPos);
		return pastState;
	}

	public ArrayList<String> listGameFiles(){
		ArrayList<String> files = null;
		
		return files;
	}
	
	public ArrayList<String> listBoardFiles(){
		ArrayList<String> files = null;
		
		return files;
	}

}
