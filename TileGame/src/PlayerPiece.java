import java.util.ArrayList;

/**
 * File Name: PlayerPiece.java<br>
 * Created: 20/11/2020<br>
 * Modified: 24/11/2020<br>
 * @author Morgan Firkins (852264)<br>
 * Version: 1.3(Added linkedData to constructor, updated JavaDoc & toString method)<br>
 *
 */
public class PlayerPiece {
	private int x;
	private int y;
	private String colour;
	private boolean backtrackApplied;
	private ArrayList<ActionTile> ownedActionTiles;
	
	private PlayerData linkedData;

	/**
	 * Constructs the player piece object for the board
	 * 
	 * @param x:                x co-ordinate of where the piece is to be placed on
	 *                          the board
	 * @param y:                y co-ordinate of where the piece is to be placed on
	 *                          the board
	 * @param colour:           Colour of the piece
	 * @param backtrackApplied: Used to store state of if the backtrack function has
	 *                          been used
	 * @param linkedData:		Stores the playerData associated with piece
	 */
	public PlayerPiece(int x, int y, String colour, boolean backtrackApplied, PlayerData linkedData) {
		this.x = x;
		this.y = y;
		this.colour = colour;
		this.backtrackApplied = backtrackApplied;
		this.ownedActionTiles = new ArrayList<ActionTile>();
		this.linkedData = linkedData;
		
		
	}
	
	
	/**
	 * Sets X co-ordinate of the player piece
	 * 
	 * @param x: X co-ordinate to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets X co-ordinate of the player piece
	 * 
	 * @return int: Returns X co-ordinate as integer
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Sets Y co-ordinate of the player piece
	 * 
	 * @param y: Y co-ordinate to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets Y co-ordinate of the player piece
	 * 
	 * @return int: Returns Y co-ordinate as integer
	 */
	public int getY() {
		return this.y;

	}

	/**
	 * Sets colour of the player piece
	 * 
	 * @param colour: Colour to be set as
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}

	/**
	 * Gets the current colour of the player piece
	 * 
	 * @return String: Returns the colour as a String
	 */
	public String getColour() {
		return this.colour;
	}

	/**
	 * Sets whether the backtrack has been used
	 * 
	 * @param backtrackApplied: The status of the backtrack
	 */
	public void setBacktrack(boolean backtrackApplied) {
		this.backtrackApplied = backtrackApplied;

	}

	/**
	 * Gets the status of the backtrack
	 * 
	 * @return boolean: Returns the backtrack status as a boolean
	 */
	public boolean getBacktrack() {
		return this.backtrackApplied;

	}
	
	/**
	 * Allows an action tile to be issued to this user
	 * @param tile tile to be added
	 */
	public void addActionTile(ActionTile tile) {
		this.ownedActionTiles.add(tile);
	}
	
	/**
	 * Allows a bulk of actionTiles to be allocated to the player.
	 * @param actionTiles The action tiles to be added to the player.
	 */
	public void setBulkActionTiles(ArrayList<ActionTile> actionTiles) {
		this.ownedActionTiles = actionTiles;
	}
	
	/**
	 * Returns a array of all of the tiles owned by this player.
	 * @return tiles owned by this player
	 */
	public ArrayList<ActionTile> getActionTilesOwned(){
		return this.ownedActionTiles;
	}
	
	/**
	 * Allows a player to play a tile.
	 * @param tile the tile to be played
	 * @return boolean if the operation has been completed correctly. 
	 */
	public boolean playActionTile(ActionTile tile) {
		return this.ownedActionTiles.remove(tile);
	}
	
	/**
	 * Returns the relevant linked data for this player.
	 * @return linked data.
	 */
	public PlayerData getLinkedData() {
		return linkedData;
	}

	@Override
	public String toString() {
		String result = "X co-ordinate: " + this.getX() + "\n";
		result += "Y co-ordinate: " + this.getY() + "\n";
		result += "Colour: " + this.getColour() + "\n";
		result += "BackTrack: " + this.getBacktrack() + "\n";
		result += "Action Tiles Owned: " + "\n";
		for(ActionTile a : this.getActionTilesOwned()) {
			result += a.getType() + "\n";
		}
		result += "playerData: " + "\n";
		result += this.getLinkedData().toString();
		return result;

	}

}
