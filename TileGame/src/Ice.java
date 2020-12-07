/**
 * This is a implementation of the Ice Class. It extends the Action Tile Placeable 
 * class and implements the instantiate action method and the deactivate action method. 
 * @author Alex Ullman (851732) and Joshua Sinderberry (851800)
 * @version 1.0
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
		try {
			for(Placeable tile : tilesToAction) {
				tile.freeze();
				System.out.println(tile.getType());
			}
		}  catch(NullPointerException e) {
			//This occurs when a action tile is close to the border of the game.
		}
	}
	/**
     * This method re-freezes all tiles that should be frozen - in case they got melted
     * by another ice tile expiring which covered some of the same tiles.
     */
    public void refreshAction() {
        if (super.getTimeRemaining() > 0) {
            for (int i = 0; i < tilesToAction.length; i++) {
                tilesToAction[i].freeze();
            }
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
