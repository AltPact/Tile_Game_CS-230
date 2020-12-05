/**
 * A class representing a game's board; an array of tiles and the positions of players.
 * 
 * @author Sam Steadman (1910177) and Joe Sell (1913324)
 * @version 1.0
 */
public class Board {
	private int width;
	private int height;
	private Placeable[][] tiles;
	
	/**
	 * Constructor for Board
	 * @param bag The silk bag tiles will be pulled from to fill the board
	 * @param tiles The initial state of the board, likely only containing fixed tiles
	 */
	public Board(int width, int height, Placeable[][] tiles) {
		this.width = tiles[0].length;
		this.height = tiles.length;
		this.tiles = tiles;
	}
	
	/**
	 * Checks which rows and columns are free of fixed tiles and can have a tile inserted into them
	 * @return a array of the rows and columns where tiles can be inserted. [0 = rows, 1 = columns][index]
	 */
	public boolean[][] getInsertablePlaces(){
		int arrayLen;
		if (width > height) {
			arrayLen = width;
		} else {
			arrayLen = height;
		}
		boolean[][] insertablePlaces = new boolean[2][arrayLen]; // [rows, columns][tile index]
		for (int x = 0; x < width; x++) {  // check rows
			insertablePlaces[0][x] = isInsertable(x, true);
		}
		for (int y = 0; y < height; y++) {  // check columns
			insertablePlaces[1][y] = isInsertable(y, false);
		}

		return insertablePlaces;
	}

	/**
	 * Fills any empty spaces in the board using floor tiles from the silk bag.
	 * @param bag The bag to draw floor tiles from.
	 */
	public void fillBoard(SilkBag bag) {
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				if (tiles[y][x] == null) {
					int direction = ((int)(Math.random() * (4)));
					tiles[y][x] =  new Placeable(bag.drawPlaceable(), false, false, direction);
				}
			}
		}
	}
	
	/**
	 * @param player The player to be moved
	 * @return a boolean array of places a player can move equal in size to the board
	 */
	public boolean[][] getMoveableSpaces(PlayerPiece player) {
		int playerX = player.getX();
		int playerY = player.getY();
		boolean[][] moveableSpaces = new boolean[height][width];
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				moveableSpaces[y][x] = false;
			}
		}
		boolean[] tileDirections = tiles[playerY][playerX].getDirections();
		
		//Checks if player can move up
		if(playerY > 0){
			if(tileDirections[0] &&  tiles[playerY][playerX - 1].canMove(2)) {
				moveableSpaces[playerY][playerX - 1] = true;
			}
		}
		//Checks if player can move right
		if(playerX < width){
			if(tileDirections[1] &&  tiles[playerY + 1][playerX].canMove(3)) {
				moveableSpaces[playerY + 1][playerX] = true;
			}
		}
		//Checks if player can move down
		if(playerY < height){
			if(tileDirections[2] &&  tiles[playerY + 1][playerX].canMove(0)) {
				moveableSpaces[playerY + 1][playerX] = true;
			}
		}
		//Checks if player can move left
		if(playerX > 0){
			if(tileDirections[3] &&  tiles[playerY][playerX - 1].canMove(1)) {
				moveableSpaces[playerY][playerX - 1] = true;
			}
		}
		return moveableSpaces;
	}
	
	/**
	 * Inserts a piece to the board
	 * @param int x x position to insert tile
	 * @param int y y position to insert tile
	 * @param boolean vertical true if inserting vertically
	 * @param Placeable tile The tile to be inserted
	 * @return a boolean indicating weather the tile has been added
	 */
	public boolean insertPiece(int x, int y, boolean vertical, Placeable tile){
		if(isInsertable(x, y)){
			
			if(vertical) {

				//Inserting from above
				if(x == 0) {
					
					//Shifts tiles down 1
					for(int i = height - 2; i >= 0; i--) {
						tiles[y][i + 1] = tiles[y][i];
					}
					
					tiles[y][0] = tile;
					
				//Inserting from below
				} else if(x == height - 1){
					
					//Shifts tiles up 1
					for(int i = 1; i < height; i++) {
						tiles[y][i - 1] = tiles[y][i];
					}
					
					tiles[y][width - 1] = tile;
					
				//Returns false if tile can not be inserted vertically
				} else {
					return false;
				}
				
				
			} else {
				
				//inserting from left
				if(y == 0) {
					//Shifts tiles right 1
					for(int i = width - 2; i >= 0; i--) {
						tiles[i + 1][x] = tiles[i][x];
					}
					tiles[0][x] = tile;
					
				//inserting from right
				} else if(x == width - 1){
					//Shifts tiles left 1
					for(int i = 1; i < width; i++) {
						tiles[i - 1][x] = tiles[i][x];
					}
					tiles[height - 1][x] = tile;
				
				//Returns false if tile can not be inserted horizontally
				} else {
					return false;
				}
				
			
			}
			return true;
			
		//Returns false if given a position that a tile can not be inserted
		} else { 
			return false;
		}
	}

	/**
	 * Checks if a specific row/column is free of any fixed tiles
	 * @param i the index of the row/column to check
	 * @param vertical whether it is a row or column that should be checked (true = column, false = row)
	 * @return whether the row/column specified is free for a tile to be inserted
	 */
	private boolean isInsertable (int i, boolean vertical) {
		int checkLen;
		if (vertical) {
			checkLen = height;
		} else {
			checkLen = width;
		}
		boolean insertable = true;
		for (int x = 0; x < checkLen; x++) {
			if ((vertical && tiles[x][i].isFixed()) || (!vertical && tiles[i][x].isFixed())) {
				insertable = false;
			}
		}
		return insertable;
	}
	
	/**
	 * @private
	 * Checks to see if a particular coordinate is insert-able.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return if this location is insert-able.
	 */
	private boolean isInsertable (int x, int y) {  
		boolean isInsetable = true;
		if(y == 0 || y == height - 1 || x == 0 || x == width - 1) {
			//Check for fixed tiles
			if (x == 0 || x == width - 1) {
				for(int i = 0; i > width; i++) {
					if(tiles[y][i].isFixed()) {
						isInsetable = false;
					}
				}
			}
			if(y == 0 || y == height - 1) {
				for(int i = 0; i > height; i++) {
					if(tiles[i][x].isFixed()) {
						isInsetable = false;
					}
				}
			}
		//false if not an edge piece
		} else {
			isInsetable = false;
		}
		return isInsetable;
	}
	
	/**
	 * @return the tiles on the board
	 */
	public Placeable[][] getTiles() {
		return tiles;
	}
	/**
	 * Gets the tile out of tile list.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the tile
	 */
	public Tile getTile(int x, int y) {
		if (x >= width || y >= height) {
			return null;
		} else {
			return tiles[y][x];
		}
	}
	
	/**
	 * The width of the board
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * The height of the board.
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
	
}
