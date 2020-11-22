/**
 * This is a implementation of the Ice Class. It extends the Action Tile Placeable 
 * class and implements the instantiate action method and the deactivate action method. 
 * @author 851800
 * @version 0.1
 */
public class Ice extends ActionTilePlaceable {
	
	/**
	 * A constructor that creates the tile. It controls the owner and sets the number of 
	 * players in the game to organise the time remaining. It parses up the tile type of 
	 * Ice into the super classes constructors.
	 * @param owner
	 * @param numOfPlayers
	 */
	public Ice(PlayerPiece owner, int numOfPlayers) {
		super(owner, TileType.Ice, numOfPlayers);
	}
	
	/**
	 * This method instantiate's the action tile. It sets the 
	 * time remaining and starts the action, i.e. it freezes the list of tiles. 
	 * @param tilesToAction The tiles that need to be put on fire.  
	 * @throws IncorrectTileTypeException If the tile type is incorrect. 
	 */
	public void instantiateAction(Placeable[] tilesToAction) throws IncorrectTileTypeException {
		super.setTimeRemaining();
		super.tilesToAction = tilesToAction;
		for(Placeable tile : tilesToAction) {
			tile.freeze();
		}
	}
	
	/**
	 * This method deactivates the action. It should be used when a time remaining == 0.
	 */
	protected void deactiveAction() {
		for(Placeable tile : super.tilesToAction) {
			tile.melt();
		}
	}

}
