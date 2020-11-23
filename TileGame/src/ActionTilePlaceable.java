/**
 * An abstract class that has been designed to model a Placeable Action Tile
 * This class models the time remaining for each Tile and ensures that
 * a sub class implements the activation/de-activations by providing 
 * abstract implementations.  
 * @author 851800
 * @version 0.1
 *
 */
public abstract class ActionTilePlaceable extends ActionTile {
	
	protected final int NUM_OF_PLAYERS;
	private int timeRemaing;
	protected Placeable[] tilesToAction;

	/**
	 * A constructor that sets the owner of the tile and the tile type.
	 * It used used at the time the tile is "pulled from the silk bag".
	 * 
	 * @param owner The owner of the tile.
	 * @param tileType The Type of the tile being implemented.
	 * @param numOfPlayers The number of players in the game. 
	 */
	public ActionTilePlaceable(PlayerPiece owner, TileType tileType, int numOfPlayers) {
		super(owner, tileType);
		this.NUM_OF_PLAYERS = numOfPlayers;
	}
	
	/**
	 * Sets the time remaining for this tile. 
	 * It should be used when the tile is played.
	 * @param numOfPlayers The number of players in the game. 
	 * @throws IncorrectTileTypeException 
	 */
	protected void setTimeRemaining() throws IncorrectTileTypeException {
		if (super.getType() == TileType.Ice) {
			this.timeRemaing = NUM_OF_PLAYERS;
		} else if (super.getType() == TileType.Fire) {
			this.timeRemaing = NUM_OF_PLAYERS;
		} else {
			throw new IncorrectTileTypeException(super.getType());
		}
	}
	
	
	/**
	 * This method is used after each turn of the game to decrement the time 
	 * remaining for this tile. 
	 */
	public void decrementTime() {
		this.timeRemaing --;
		if (timeRemaing == 0) {
			deactiveAction();
		}
	}
	
	/**
	 * This method should be used to set the tile that are being used and 
	 * the length of time the tile should be extended to.
	 * 
	 * @param tilesToAction The tiles that need to be effected at placement time.
	 * @param numOfPlayers The Number of players in the game.
	 * @throws Incorrect Tile Type If the tile type does not exist.
	 */
	public abstract void instantiateAction(Placeable[] tilesToAction) throws IncorrectTileTypeException;
	
	/**
	 * Implemented to deactivate the action when timeRemaining is 0.
	 */
	protected abstract void deactiveAction();

}
