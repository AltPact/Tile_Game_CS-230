/**
 * A class representing a game's board; an array of tiles and the positions of players.
 * 
 * @author Sam Steadman (1910177) and Joe Sell? (?)
 * @version 1.0
 */
public class Board {
	private int width;
	private int height;
	private Tile[][] tiles;
	private PlayerPiece[] players;
	private int[][] insertablePlaces;
	// private SilkBag bag; TODO: add silk bag
	
	/**
	 * Constructor for Board
	 * @param bag The silk bag tiles will be pulled from to fill the board
	 * @param tiles The initial state of the board, likely only containing fixed tiles
	 */
	Board(int width, int height, String bag, Tile[][] tiles) {  // TODO: add silk bag
		this.width = tiles[0].length;
		this.height = tiles[1].length;
		this.tiles = tiles;
		//fillBoard(bag); TODO: add silk bag
	}
	
	/**
	 * Fills any empty spaces in the board using floor tiles from the silk bag.
	 * @param bag The bag to draw floor tiles from.
	 */
	private void fillBoard(SilkBag bag) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tiles[x][y] == null) {
					tiles[x][y] = bag.drawFloor();
				}
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if (x > width || y > height) {
			return null; // should throw an exception here maybe
		} else {
			return tiles[x][y];
		}
	}
	
	public PlayerPiece getPlayerPiece(int p) {
		if (p > players.length) {
			return null;
		} else {
			return players[p];
		}
	}
	
	public int[][] getInsertablePlaces() {
		return insertablePlaces;
	}
}
