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
			Scanner s = new Scanner(f);

			/* read game meta data */
			int height = s.nextInt();
			int width = s.nextInt();
			int numPlayers = s.nextInt();
			PlayerPiece[] players = new PlayerPiece[numPlayers];
			int curPlayer = s.nextInt(); // TODO: DUPLICATE DATA, this is included in curState
			int movesLeftForCurrent = s.nextInt();
			boolean isGoalHit = s.nextBoolean();

			/* read current game state */
			GameState curState = readGameState(s, height, width, players);

			/* read player data */
			for (int p = 0; p < numPlayers; p++) {
				String name = s.next();
				File pDataFile = new File(name); // TODO: needs to look in correct directory, will work for now
				PlayerData pData = PlayerDataFileReader.readFile(pDataFile);
				int playerX = curState.getPlayersPositions()[p][0];  // 0 and 1 may need to be swapped here? not sure if X/Y or Y/X
				int playerY = curState.getPlayersPositions()[p][1];
				String playerColour = s.next();
				Boolean backtrackApplied = s.nextBoolean();
				players[p] = new PlayerPiece(playerX, playerY, playerColour, backtrackApplied, pData);
			}

			/* read past game states */
			ArrayList<GameState> pastStates = new ArrayList<GameState>();
			int numPastStates = s.nextInt();
			for (int state = 0; state < numPastStates; state++) {
				pastStates.add(readGameState(s, height, width, players));
			}

			/* read the silk bag */
			SilkBag bag = readSilkBag(s);

			s.close();
			return new Game(curState, pastStates, bag, players);
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
	 * @return An instantiated GameState object.
	 */
	private static GameState readGameState(Scanner s, int height, int width, PlayerPiece[] players) {
		/* read gamestate metadata */
		boolean isGoalHit = s.nextBoolean();
		int curPlayer = s.nextInt();
		int movesLeftForCurPlayer = s.nextInt();
		int numPlayers = players.length;

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

		/* read player positions */
		int[][] playerPositions = new int[numPlayers][2];
		for (int p = 0; p < numPlayers; p++) {
			playerPositions[p][0] = s.nextInt();
			playerPositions[p][1] = s.nextInt();
		}
		// if a board file is being read that supports more players than are needed, skip unneeded player positions
		s.skip(Pattern.compile("..ENDPLAYERPOS"));

		/* read action tiles */
		ArrayList<ActionTile>[] curActionTilesForEachPlayer = new ArrayList[numPlayers];
		for (int p = 0; p < numPlayers; p++) {
			curActionTilesForEachPlayer[p]= new ArrayList<ActionTile> ();
			int numActionTilesInHand = s.nextInt();
			for (int a = 0; a < numActionTilesInHand; a++) {
				TileType actionTileType = TileType.valueOf(s.next());
				switch (actionTileType) {
					case Ice:
						curActionTilesForEachPlayer[p].add(new Ice(players[p], numPlayers));
						break;
					case Fire:
						curActionTilesForEachPlayer[p].add(new Fire(players[p], numPlayers));
						break;
					case BackTrack:
						curActionTilesForEachPlayer[p].add(new BackTrack(players[p]));
						break;
					case DoubleMove:
						curActionTilesForEachPlayer[p].add(new BackTrack(players[p]));
						break;
				}
			}
		}
		// if a board file is being read that supports more players than are needed, skip unneeded player action tiles
		s.skip(Pattern.compile("..ENDPLAYERTILES"));

		GameState returnState = new GameState();  // TODO: should construct this at the start and fill it as the file is read
		returnState.setBoard(stateTiles, width, height);
		returnState.setPlayerPositions(playerPositions);
		returnState.setActionTilesForPlayers(curActionTilesForEachPlayer);
		returnState.setCurrentPlayer(curPlayer, movesLeftForCurPlayer);
		returnState.isGoalHit(isGoalHit);

		return returnState;
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
