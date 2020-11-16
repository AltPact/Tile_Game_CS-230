/**
 * This class is the main implementation of "Floor Tile", Placeable.
 * It should be queried by the board to discover what a user can/cannot
 * do with the tile in question.
 * It should be instantiated from the board via silk bag. Inherits from Tile.
 * The orientation defaults to 0.
 * @author 851732 and 851800
 * @version 0.1
 */

public class Placeable extends Tile {
	
	private final int NUM_OF_WAYS_TO_MOVE = 4;
	
	private int orientation = 0; //Orientation of the Tile 0 = North, 1 = East ...
	private boolean isOnFire;
	private boolean isFrozen;
	private boolean[] waysToMove = new boolean[4]; // The position in the array implies movement 
												   //e.g. waysToMove[0] = True means can move north
	private final boolean ISGOAL;
	private final boolean ISFIXED;
	
	/**
	 * The constructor to create the tile. It has the ability to 
	 * interpret ways to move.  
	 * @param tileType The type of the tile from the enumerated values
	 * @param isGoal If the tile is a goal tile
	 * @param isFixed If the tile is fixed.
	 */
	public Placeable(TileType tileType, boolean isGoal, boolean isFixed) {
		super(tileType);
		this.ISGOAL = isGoal;
		this.ISFIXED = isFixed;
		setType(tileType);
	}
	
	/**
	 * This private method sets the tiles ways to move based on the tile type
	 * @param tileType The type of tile being created.
	 */
	private void setType(TileType tileType) {
		if (tileType == TileType.Straight) {
			this.waysToMove[0] = true;
			this.waysToMove[1] = false;
			this.waysToMove[2] = true;
			this.waysToMove[3] = false;
		} else if (tileType == TileType.Corner) {
			this.waysToMove[0] = true;
			this.waysToMove[1] = true;
			this.waysToMove[2] = false;
			this.waysToMove[3] = false;
		} else if (tileType == TileType.TShaped) {
			this.waysToMove[0] = true;
			this.waysToMove[1] = true;
			this.waysToMove[2] = false;
			this.waysToMove[3] = true;
		} else if (tileType == TileType.Goal) {
			this.waysToMove[0] = true;
			this.waysToMove[1] = true;
			this.waysToMove[2] = true;
			this.waysToMove[3] = true;
		}
	}
	
	/**
	 * This method tests if the user can move in a particular direction 
	 * @param direction the direction the user wishes to move
	 * @return a boolean demonstrating if the user can/cannot move.
	 */
	public boolean canMove(int direction) {
		return this.waysToMove[direction];
	}
	
	/**
	 * This method rotates the tile right one place.
	 * It automatically updates ways to move and orientation.
	 */
	public void rotateRight() {
		this.orientation = (this.orientation + 1) % NUM_OF_WAYS_TO_MOVE;
		boolean temp = this.waysToMove[0];
		this.waysToMove[0] = this.waysToMove[1];
		this.waysToMove[1] = this.waysToMove[2];
		this.waysToMove[2] = this.waysToMove[3];
		this.waysToMove[3] = temp;
	}
	
	/**
	 * This method rotates the tile left one place.
	 * It automatically updates ways to move and orientation.
	 */
	
	public void rotateLeft() {
		this.orientation = (NUM_OF_WAYS_TO_MOVE + (this.orientation - 1)) % NUM_OF_WAYS_TO_MOVE;
		boolean temp = this.waysToMove[0];
		this.waysToMove[0] = this.waysToMove[3];
		this.waysToMove[3] = this.waysToMove[2];
		this.waysToMove[2] = this.waysToMove[1];
		this.waysToMove[1] = temp;
	}
	
	/**
	 * Method to freeze the tile 
	 */
	public void freeze() {
		this.isFrozen = true;
	}
	
	/**
	 * Method to melt the tile
	 */
	public void melt() {
		this.isFrozen = false;
	}
	
	/**
	 * Method to set the tile alight
	 */
	public void putOnFire() {
		this.isOnFire = true;
	}
	
	/**
	 * Method to "Put the fire out"
	 */
	public void putOutFire() {
		this.isOnFire = false;
	}
	/**
	 * @return The Orientation of the tile
	 */
	public int getOrientation() {
		return this.orientation;
	}
	/**
	 * @return The ways you can move from the tile
	 */
	public boolean[] getDirections() {
		return this.waysToMove;
	}
	/**
	 * @return If the tile is frozen
	 */
	public boolean isFrozen() {
		return this.isFrozen;
	}
	
	/**
	 * @return If the tile is on fire
	 */
	public boolean isOnFire() {
		return this.isOnFire;
	}
	
	/**
	 * Returns if the tile is a goal.
	 */
	public boolean isGoal() {
		return this.ISGOAL;
	}
	
	/**
	 * @return If the tile is either fixed or if the tile is frozen.
	 */
	public final boolean isFixed() {
		return this.ISFIXED || this.isFrozen;
	}
	
}