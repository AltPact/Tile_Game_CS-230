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
	
	/**
	 * This class allows for the creation of a new game, it takes a file name
	 * to show which board file should be read and a array of player piece.
	 * @param fileName The file to be loaded.
	 * @param players The players that are going to play.
	 */
	public Game(String fileName, PlayerPiece[] players) {
		this.bag = new SilkBag()
		this.board = new Board(fileName, bag);
		this.players = players;
		this.curPlayer = 0;
		this.tilesInAction = new ArrayList<ActionTilePlaceable>();
	}
	
	
	public GameState getState() {
		return new GameState(board, players, curPlayer);
	}
	
	public Tile getNewTileForCurrentPlayer() {
		Tile newTile = bag.getTile();
		if (newTile.isAction()) {
			players[curPlayer].addActionTile((ActionTile)newTile);
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
	
	public void playBackTrack(ActionTile backtrack) {
		players[curPlayer].playActionTile(backtrack);
		//TODO Implement Backtrack 
	}
	
	public void playIce(Ice ice, int x, int y) {
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
	
	public void playFire(Fire fire, int x, int y) {
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
		int newX;
		int newY;
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
		
		int oppDirection;
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
		
		return getState();
		
	}

}
