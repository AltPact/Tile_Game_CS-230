import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileReader {

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
			int curPlayer = s.nextInt(); // TODO: DUPLICATE DATA, this is included in curState
			int movesLeftForCurrent = s.nextInt();
			boolean isGoalHit = s.nextBoolean();

			/* read current game state */
			GameState curState = readGameState(s, height, width, numPlayers);

			/* read player data */
			PlayerPiece[] players = new PlayerPiece[numPlayers];
			for (int p = 0; p < numPlayers; p++) {
				int playerX = curState.getPlayersPositions()[p][0];  // 0 and 1 may need to be swapped here? not sure if X/Y or Y/X
				int playerY = curState.getPlayersPositions()[p][1];
				String playerColour = s.next();
				Boolean backtrackApplied = s.nextBoolean();
				players[p] = new PlayerPiece(playerX, playerY, playerColour, backtrackApplied);
			}

			/* read past game states */
			ArrayList<GameState> pastStates = new ArrayList<GameState>();
			int numPastStates = s.nextInt();
			for (int state = 0; state < numPastStates; state++) {
				pastStates.add(readGameState(s, height, width, numPlayers));
			}

			/* read the silk bag */
			SilkBag bag = readSilkBag(s);

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
	 * @param numPlayers The number of players to start the game with.
	 * @return An instantiated Game object.
	 */
	public static Game readBoardFile(String filename, int numPlayers) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f);

			/* read board meta data */
			int height = s.nextInt();
			int width = s.nextInt();

			/* read the starting state of the game */
			GameState startState = readGameState(s, height, width, numPlayers);

			/* read player data */
			PlayerPiece[] players = new PlayerPiece[numPlayers];
			for (int p = 0; p < numPlayers; p++) {
				int playerX = startState.getPlayersPositions()[p][0];  // 0 and 1 may need to be swapped here? not sure if X/Y or Y/X
				int playerY = startState.getPlayersPositions()[p][1];
				String playerColour = s.next();
				Boolean backtrackApplied = s.nextBoolean();
				players[p] = new PlayerPiece(playerX, playerY, playerColour, backtrackApplied);
			}


			/* read the silk bag */
			SilkBag bag = readSilkBag(s);

			return new Game(startState, new ArrayList<GameState> (), bag, players);  // use empty ArrayList for pastStates since there are none
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		}
	}


	private static SilkBag readSilkBag(Scanner s) {
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
		return new SilkBag(numActionTiles, numPlaceableTiles);
	}


	/**
	 * Reads a "GameState" object from a file. When loading a game that is in-progress, this will likely
	 * be called multiple times, once for each previous state of the game, so that Backtrack can work.
	 *
	 * @param s File scanner to read data through
	 * @param height Height of the board
	 * @param width Width of the board
	 * @param numPlayers Number of players in the game
	 * @return An instantiated GameState object.
	 */
	private static GameState readGameState(Scanner s, int height, int width, int numPlayers) {
		boolean isGoalHit = s.nextBoolean();
		int curPlayer = s.nextInt();

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

		int[][] playerPositions = new int[numPlayers][2];
		for (int p = 0; p < numPlayers; p++) {
			playerPositions[p][0] = s.nextInt();
			playerPositions[p][1] = s.nextInt();
		}
		// if a board file is being read that supports more players than are needed, skip unneeded player positions
		s.skip(Pattern.compile("..ENDPLAYERPOS"));

		// TODO: both action tiles and actiontileplacables are currently abstract, you can't instantiate them to put them in an array.
		// TODO: ActionTilePlacable also has no implementation, so currently serves no purpose. need to talk about whether this is an efficient
		// TODO: inheritence structure of if ActionTilePlaceable should be a subclass of Placeable (or some other solution)
		ArrayList<ActionTile>[] curActionTilesForEachPlayer = new ArrayList[numPlayers];
		/* COMMENTED FOR SAKE OF COMPILING UNTIL THIS IS FIXED
		for (int p = 0; p < numPlayers; p++) {
			curActionTilesForEachPlayer[p]= new ArrayList<ActionTile> ();
			int numActionTilesInHand = s.nextInt();
			for (int a = 0; a < numActionTilesInHand; a++) {
				TileType actionTileType = TileType.valueOf(s.next());
				curActionTilesForEachPlayer[p].add(new ActionTilePlaceable(actionTileType));
			}
		}
		*/
		// if a board file is being read that supports more players than are needed, skip unneeded player action tiles
		s.skip(Pattern.compile("..ENDPLAYERTILES"));

		return new GameState(stateTiles, playerPositions, curActionTilesForEachPlayer, curPlayer, isGoalHit);
	}

}
