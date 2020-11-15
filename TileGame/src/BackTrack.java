/**
 * This class is the tile doubleMove. It should provoke a response in the game
 * class when it is played. It inherits from action tile.
 * 
 * @author 851732 and 851800
 * @version 0.1
 */

public class BackTrack extends ActionTile{
	
	/**
	 * Constructor to construct backtrack.
	 * @param owner The owner of the Tile.
	 */
	public BackTrack(PlayerPiece owner) {
		super(owner, TileType.BackTrack);
	}

}
