/**
 * This is a abstract class action tile. All action tiles
 * exist lower in the tile inheritance hierarchy. It inherits from tile.
 * @author Alex Ullman (851732) and Joshua Sinderberry (851800)
 * @version 1.0
 */

public abstract class ActionTile extends Tile {
	private final PlayerPiece OWNER;
	/*
	 * PlayerPiece does not exists yet, 
	 * PlayerPiece has been instantiated as a empty class.
	 * AS OF 13/11/20 
	 */
	/**
	 * This constructor takes a tile type and a owner.
	 * @param owner The owner of a file
	 * @param tileType The type of the tile
	 */
	public ActionTile(PlayerPiece owner, TileType tileType) {
		super(tileType);
		this.OWNER = owner;
	}
	
	/**
	 * Gets the owner of the tile
	 * @return owner
	 */
	public PlayerPiece getOwner() {
		return this.OWNER;
	}

}
