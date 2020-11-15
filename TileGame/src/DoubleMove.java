/**
 * This class is used when double move is called. It should provoke a response in 
 * the game class. It inherits from action tile.
 * 
 * @author 851732 and 851800
 * @version 0.1
 *
 */
public class DoubleMove extends ActionTile{
	
	/**
	 * Constructor to construct DoubleMove.
	 * @param owner The owner of the Tile.
	 */
	public DoubleMove(PlayerPiece owner) {
		super(owner, TileType.DoubleMove);
	}

}
