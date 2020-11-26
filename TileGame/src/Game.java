/*
 * Java Doc Not Yet Complete.
 * Class Not Yet Complete.
 * No implementation for backtrack yet as file reader/writer not yet implemented.
 * No working implementation as silk bag not yet complete.
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
	 * This class allows for the creation of a new game, it takes a file name
	 * to show which board file should be read and a array of player piece.
	 * @param fileName The file to be loaded.
	 * @param players The players that are going to play.
	 */
	public Game(GameState curState, ArrayList<GameState> pastStates, SilkBag bag, PlayerPiece[] players, int curPlayer, int movesLeftForCurrent, ArrayList<ActionTilePlaceable> tilesInAction) {
		this.bag = bag;
		this.board = new Board(curState.getBoard().length, curState.getBoard()[0].length, curState.getBoard());
		this.players = players;
		this.curPlayer = curPlayer;
		this.tilesInAction = tilesInAction;
		this.pastStates = pastStates;;
	}
	
	
	public GameState getState() {
		int[][] playerPositions = new int[players.length][2];
		ArrayList[] actionTilesForPlayers = new ArrayList[players.length];
		for(int i = 0; i < players.length; i++) {
			actionTilesForPlayers[i] = players[i].getActionTilesOwned();
			playerPositions[i][0] = players[i].getX();
			playerPositions[i][1] = players[i].getY();
		}
		return new GameState(board.getTiles(), playerPositions, actionTilesForPlayers, curPlayer, isGoalReached);
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
	
	public ArrayList<ActionTile> getActionTilesForPlayer() {
		return players[curPlayer].getActionTilesOwned();
	}
	
	public void insertTile(Placeable tileToBeInserted, int x, int y, boolean vertical) {
		board.insertPiece(x, y, vertical, tileToBeInserted);
	}
	
	public void playDoubleMove(ActionTile doubleMove) {
		players[curPlayer].playActionTile(doubleMove);
		this.movesRemaingForThisPlayer++;
	}
	
	public void playBackTrack(ActionTile backtrack, int playerAgainst) throws IllegalBackTrackException {
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
			}
			
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalBackTrackException("Not enough moves made to conduct backtrack against player " + playerAgainst);
		} finally {
			players[curPlayer].addActionTile(new BackTrack(players[curPlayer]));
		}
		
		players[playerAgainst].setX(backTrackedX);
		players[playerAgainst].setY(backTrackedY);
		
	}
	
	public void playIce(Ice ice, int x, int y) throws IncorrectTileTypeException {
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
	}
	
	public void playFire(Fire fire, int x, int y) throws IncorrectTileTypeException {
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
	}
	
	public boolean move(int direction) {
		if (movesRemaingForThisPlayer <= 0) {
			return false;
		}
		int curX = players[curPlayer].getX();
		int curY = players[curPlayer].getY();
		Placeable curTile =  (Placeable) board.getTile(curX, curY);
		int newX = 0;
		int newY = 0;
		if(curTile.canMove(direction)) {
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
		} else {
			return false;
		}
		
		Placeable newTile =  (Placeable) board.getTile(curX, curY);
		
		int oppDirection = 0;
		if(direction == 0) {
			oppDirection = 2;
		} else if(direction == 1) {
			oppDirection = 3;
		} else if(direction == 2) {
			oppDirection = 0;
		} else if(direction == 3) {
			oppDirection = 1;
		}
		
		if(newTile.canMove(oppDirection)) {
			players[curPlayer].setX(newX);
			players[curPlayer].setY(newY);
			if(newTile.isGoal()) {
				this.isGoalReached = true;
			}
			return true;
		} else {
			return false;
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
		GameState newState = getState();
		this.pastStates.add(newState);
		
		return newState;
		
	}

}
