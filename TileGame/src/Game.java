
 
/* Java Doc Not Yet Complete.
 * Class Not Yet Complete.
 * No implementation for backtrack yet as file reader/writer not yet implemented.
 * No working implementation as silk bag not yet complete.
 * 
 * TO DO TODAY:
 * 		- Implement the relevant game state constructors.
 * 			end Game
 * 		- Past States reduce data.
 *  
 *  Board needs method to get all tiles.
 * 
 */





import java.util.ArrayList;
/**
 * Game Class is designed to implement the game.
 * It has the ability for a player to draw a tile, play a action tile,
 * place a placeable tile and move, when each is required.
 * @author Wan Fai Tong (1909787), Alex Ullman (851732) and Joshua Sinderberry (851800)
 * 
 * @version 1.2
 *
 */
public class Game {
	
	private Board board;
	private SilkBag bag;
	private PlayerPiece[] players;
	private int curPlayer;
	private int movesRemaingForThisPlayer = 1;
	private ArrayList<ActionTilePlaceable> tilesInAction;
	private boolean isGoalReached;
	private ArrayList<GameState> pastStates;
	
	
	/**
	 * This constructor is designed to be used to construct a new 
	 * game, it should have a silk bag, the players and the board parsed to it.
	 * @param bag The silk bag that should operate this game
	 * @param players The players, they should already have their starting positions loaded.
	 * @param board The game board that should be played. 
	 */
	public Game(SilkBag bag, PlayerPiece[] players, Board board) {
		this.board = board;
		this.bag = bag;
		this.players = players;
		this.curPlayer = 0;
		this.movesRemaingForThisPlayer = 1;
		this.tilesInAction = new ArrayList<ActionTilePlaceable>();
		this.isGoalReached = false;
		this.pastStates = new ArrayList<GameState>();
		
	}
	
	/**
	 * This constructor is designed to be used to construct a game that is already in play.
	 * It rebuilds a game based on the curState that has been parsed to it by the caller.
	 * It also takes a array list of past states, a silk bag that is fully populated. 
	 * @param curState The state the game should be loaded into
	 * @param pastStates The past states of the game.
	 * @param bag The silk bag.
	 * @param players The players, the they do not need to have their positions loaded.
	 */
	public Game(GameState curState, ArrayList<GameState> pastStates, SilkBag bag, PlayerPiece[] players) {
		this.bag = bag;
		this.players = players;
		this.pastStates = pastStates;
		reconstructGame(curState);
		
	}
	
	 //TODO now game class has been reprogrammed, get the data that is relevant for the graphics context.
	
	/**
	 * @private
	 * This method is designed to be able to build the game from a current state.
	 * @param curState The state the game should be loaded into.
	 */
	private void reconstructGame(GameState curState) {
		this.board = new Board(curState.getBoard().length, curState.getBoard()[0].length, curState.getBoard(), bag);
		this.tilesInAction = curState.getTilesInAction();
		this.curPlayer = curState.getCurPlayer();
		this.movesRemaingForThisPlayer = curState.getMovedPlayer();
		this.tilesInAction = curState.getTilesInAction();
		for(int i = 0; i < players.length ; i++) {
			players[i].setX(curState.getPlayersPositions()[i][0]);
			players[i].setX(curState.getPlayersPositions()[i][1]);
			players[i].setBulkActionTiles(curState.getActionTileForPlayer(i));
		}
	}
	
	public Tile getNewTileForCurrentPlayer() {
		TileType newTileType = bag.draw();
		Tile newTile = null;
		if(newTileType == TileType.BackTrack) {
			newTile = new BackTrack(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.DoubleMove) {
			newTile = new DoubleMove(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.Ice) {
			newTile = new DoubleMove(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.Fire) {
			newTile = new DoubleMove(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else {
			newTile = new Placeable(newTileType);
		}
		return newTile;
	}
	
	
	public GameState insertTile(Placeable tileToBeInserted, int x, int y, boolean vertical) {
		board.insertPiece(x, y, vertical, tileToBeInserted);
		int[] postion = {x,y};
		return tileAfterInsertion(tileToBeInserted, postion);
	}
	
	public void playDoubleMove(ActionTile doubleMove) {
		players[curPlayer].playActionTile(doubleMove);
		this.movesRemaingForThisPlayer++;
	}
	
	public GameState playBackTrack(ActionTile backtrack, int playerAgainst) throws IllegalBackTrackException {
		if (players[playerAgainst].getBacktrack()) {
			throw new IllegalBackTrackException(playerAgainst + " Has already had backtrack applied");
		}
		players[curPlayer].playActionTile(backtrack);
		int i = pastStates.size();
		GameState twoMovesAgo = null;
		try {
			GameState testState = pastStates.get(i);
			while(testState.getCurPlayer() != playerAgainst) {
				i--;
				testState = pastStates.get(i);
			}
			twoMovesAgo = pastStates.get(i - players.length);

		
		
		int backTrackedX = twoMovesAgo.getPlayersPositions()[playerAgainst][0];
		int backTrackedY = twoMovesAgo.getPlayersPositions()[playerAgainst][1];
		
		Placeable testTile = (Placeable) board.getTile(backTrackedX, backTrackedY);
			while(testTile.isOnFire()) {
				int gameStateIndex = pastStates.indexOf(twoMovesAgo);
				gameStateIndex =- players.length;
				GameState furtherBackState = pastStates.get(gameStateIndex);
				backTrackedX = furtherBackState.getPlayersPositions()[playerAgainst][0];
				backTrackedY = furtherBackState.getPlayersPositions()[playerAgainst][1];
				testTile = (Placeable) board.getTile(backTrackedX, backTrackedY);
				players[playerAgainst].setX(backTrackedX);
				players[playerAgainst].setY(backTrackedY);
			}
			
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalBackTrackException("Not enough moves made to conduct backtrack against player " + playerAgainst);
		} finally {
			players[curPlayer].addActionTile(new BackTrack(players[curPlayer]));
		}
		
		return actionTileBackTrack(playerAgainst);

	}
	
	public GameState playIce(Ice ice, int x, int y) throws IncorrectTileTypeException {
		players[curPlayer].playActionTile(ice);
		Placeable[] tilesToAction= new Placeable[9];
		tilesToAction[0] = (Placeable) board.getTile((x - 1), (y - 1));
		tilesToAction[1] = (Placeable) board.getTile((x - 1), y);
		tilesToAction[2] = (Placeable) board.getTile((x - 1), (y + 1));
		tilesToAction[3] = (Placeable) board.getTile(x, (y - 1));
		tilesToAction[4] = (Placeable) board.getTile(x, y);
		tilesToAction[5] = (Placeable) board.getTile(x, (y + 1));
		tilesToAction[6] = (Placeable) board.getTile((x + 1), (y - 1));
		tilesToAction[7] = (Placeable) board.getTile((x + 1), y);
		tilesToAction[8] = (Placeable) board.getTile((x + 1) , (y + 1));
		ice.instantiateAction(tilesToAction);
		return actionTilePlayed();
	}
	
	public GameState playFire(Fire fire, int x, int y) throws IncorrectTileTypeException {
		players[curPlayer].playActionTile(fire);
		Placeable[] tilesToAction= new Placeable[9];
		tilesToAction[0] = (Placeable) board.getTile((x - 1), (y - 1));
		tilesToAction[1] = (Placeable) board.getTile((x - 1), y);
		tilesToAction[2] = (Placeable) board.getTile((x - 1), (y + 1));
		tilesToAction[3] = (Placeable) board.getTile(x, (y - 1));
		tilesToAction[4] = (Placeable) board.getTile(x, y);
		tilesToAction[5] = (Placeable) board.getTile(x, (y + 1));
		tilesToAction[6] = (Placeable) board.getTile((x + 1), (y - 1));
		tilesToAction[7] = (Placeable) board.getTile((x + 1), y);
		tilesToAction[8] = (Placeable) board.getTile((x + 1) , (y + 1));
		fire.instantiateAction(tilesToAction);
		return actionTilePlayed();
	}
	
	public GameState moveCurrentPlayer(int direction) throws IllegalMove {
		if (movesRemaingForThisPlayer <= 0) {
			throw new IllegalMove("This player has no moves remaining");
		}
		int curX = players[curPlayer].getX();
		int curY = players[curPlayer].getY();
		boolean[][] moveableSpaces = board.getMoveableSpaces(players[curPlayer]);
		int newX = 0;
		int newY = 0;
		if(direction == 0) {
			newX = curX;
			newY = curY - 1;
		} else if(direction == 1) {
			newX = curX + 1;
			newY = curY;
		} else if(direction == 2) {
			newX = curX;
			newY = curY + 1;
		} else if(direction == 3) {
			newX = curX - 1;
			newY = curY;
		}
		if(moveableSpaces[newX][newY]) {
			players[curPlayer].setX(newX);
			players[curPlayer].setY(newY);
			movesRemaingForThisPlayer--;
			return playerMoved();
		} else {
			throw new IllegalMove("Player Cannot move in this Direction");
		}
	}
		
	public GameState endTurn() {
		curPlayer ++;
		if ((curPlayer % players.length) == 0) {
			curPlayer = 0;
		}
		for(ActionTilePlaceable tile : tilesInAction) {
			tile.decrementTime();
		}
		GameState newState = makeStateEndTurn();
		this.pastStates.add(newState);
		
		return newState;
		
	}
	
	private GameState actionTilePlayed() {
		GameState newState = new GameState();
		newState.setBoard(board.getTiles());
		newState.setActionTileApplied();
		return newState;
	}
	
	private int[][] getPlayerPositions(){
		int[][] playerPositions = new int[players.length][2];
		for(int i = 0; i < players.length; i++) {
			playerPositions[i][0] = players[i].getX();
			playerPositions[i][1] = players[i].getY();
		}
		return playerPositions;
	}
	
	private GameState playerMoved() {
		GameState newState = new GameState();
		newState.setChangedPlayerPosition(curPlayer, getPlayerPositions()[curPlayer]);
		newState.setPlayerPositions(getPlayerPositions());
		newState.isGoalHit(isGoalReached);
		return newState;
	}


	private GameState actionTileBackTrack(int playerBackTracked) {
		GameState newState = new GameState();
		newState.setChangedPlayerPosition(playerBackTracked, getPlayerPositions()[playerBackTracked]);
		newState.setPlayerPositions(getPlayerPositions());
		return newState;
	}
	
	private GameState tileAfterInsertion(Tile t, int[] positionOfInsertedTile) {
		GameState newState = new GameState();
		newState.setChangedTile(t, positionOfInsertedTile);
		newState.setPlayerPositions(getPlayerPositions());
		return newState;	
	}
	
	private GameState makeStateEndTurn() {
		GameState newState = new GameState();
		newState.setPlayerPositions(getPlayerPositions());
		newState.setBoard(board.getTiles());
		newState.setTilesInAction(tilesInAction);
		@SuppressWarnings("unchecked")
		ArrayList<ActionTile>[] tilesOwnedByPlayers = new ArrayList[players.length];
		for(int i = 0; i < players.length; i++) {
			tilesOwnedByPlayers[i] = players[i].getActionTilesOwned(); 
		}
		newState.setCurrentPlayer(curPlayer, movesRemaingForThisPlayer);
		newState.setMoveableSpaces(board.getMoveableSpaces(players[curPlayer]));
		return newState;
		
	}
	
	public GameState getCurState() {
		ArrayList<ActionTile>[] tilesOwnedByPlayers = new ArrayList[players.length];
		for(int i = 0; i < players.length; i++) {
			tilesOwnedByPlayers[i] = players[i].getActionTilesOwned();
		}
		return new GameState(board.getTiles(), getPlayerPositions(), tilesOwnedByPlayers, curPlayer, isGoalReached);
	}

	public ArrayList<GameState> getPastStates() {
		return pastStates;
	}

	public SilkBag getSilkBag() {
		return bag;
	}

	public Board getBoard() {
		return board;
	}

	public int getNumPlayers() {
		return players.length;
	}

	public int getCurPlayer() {
		return curPlayer;
	}

	public int getMovesLeftForCurrent() {
		return movesRemaingForThisPlayer;
	}

	public boolean isGoalReached () {
		return isGoalReached;
	}
}
