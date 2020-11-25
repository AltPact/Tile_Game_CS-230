
/**
 * Incorrect Tile Type Exception designed to be thrown when 
 * a tile type is either malformed or not the type that is expected.
 * @author Joshua Sinderberry (851800)
 * @version 1.0
 *
 */
public class IncorrectTileTypeException extends Exception {
	private static final long serialVersionUID = 6443204216714713400L;
	
	public IncorrectTileTypeException(TileType t) {
		super("The type " + t + "does not exist");
	}

}
