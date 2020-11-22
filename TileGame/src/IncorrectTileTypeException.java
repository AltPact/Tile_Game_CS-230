
public class IncorrectTileTypeException extends Exception {
	private static final long serialVersionUID = 6443204216714713400L;
	
	public IncorrectTileTypeException(TileType t) {
		super("The type " + t + "does not exist");
	}

}
