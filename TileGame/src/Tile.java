/**
 * The super class of all tiles. 
 * It holds what type of tile is being played.
 * This is a abstract class.
 * @author 851732 and 851800
 * @version 0.1
 */
public abstract class Tile {
	
	private TileType tileType;
	
	/**
	 * Constructor to set the type of the tile
	 * @param tileType The type of the tile from the enumerated types
	 */
	public Tile(TileType tileType) {
		this.tileType = tileType;
	}
	
	/**
	 * @return The type of the tile.
	 */
	public TileType getType() {
		return tileType;
	}

}
