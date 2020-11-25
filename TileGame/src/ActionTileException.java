
/**
 * Tile Type Exception
 * Designed to be thrown when a action tile is played and when it should not be. 
 * @author Joshua Sinderberry 851800
 * @version 1.0
 *
 */
public class ActionTileException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ActionTileException(TileType t) {
		super("" + t);
	}

}
