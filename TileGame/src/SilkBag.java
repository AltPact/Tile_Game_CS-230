public class SilkBag {
	private TileType[] actionTiles = {TileType.DoubleMove, TileType.BackTrack, TileType.Ice, TileType.Fire};
	private TileType[] placeableTiles = {TileType.Straight, TileType.Corner, TileType.TShaped};
	private int[] numActionTiles = {1, 1, 1, 1};
	private int[] numPlaceableTiles = {1, 1, 1};
	
	public SilkBag(int[] numActionTiles, int[] numPlaceableTiles) {
		this.numActionTiles = numActionTiles;
		this.numPlaceableTiles = numPlaceableTiles;
	}
	
	/**
	 * Draws a tile from the bag
	 */
	public TileType draw() {
		int numTiles = lstTotal(numActionTiles, numActionTiles.length) + lstTotal(numPlaceableTiles, numPlaceableTiles.length);
		if (numTiles == 0) {
			return null;
		} else {
			int tileNum = randInt(numTiles);
			if (tileNum <= lstTotal(numActionTiles, numActionTiles.length)) {
				int tileType = pickType(numActionTiles, numActionTiles.length);
				numActionTiles[tileType] -= 1;
				return actionTiles[tileType];
			} else {
				int tileType = pickType(numPlaceableTiles, numPlaceableTiles.length);
				numPlaceableTiles[tileType] -= 1;
				return placeableTiles[tileType];
			}
		}
	}
	
	/**
	 * Draws a placeable tile from the bag
	 */
	public TileType drawPlaceable() {
			int tileType = pickType(numPlaceableTiles, numPlaceableTiles.length);
			numPlaceableTiles[tileType] -= 1;
			return placeableTiles[tileType];
	}
	
	public int[] getNumActionTiles() {
		return numActionTiles;
	}
	
	public int[] getNumPlaceableTiles() {
		return numPlaceableTiles;
	}
	
	private static int pickType(int[] tiles, int tileTypes) {
		int tileNum = randInt(lstTotal(tiles, tiles.length));
		int prevTiles = 0;
		for(int i = 0; i < tileTypes ; i++) {
			if(tileNum <= tiles[i] + prevTiles) {
				return i;
			} else {
				prevTiles += tiles[i];
			}
		}
		return 4;
	}
	
	private static int randInt(int a) {
		int x = ((int)(Math.random() * (a))) + 1;
		return x;
	}
	
	private static int lstTotal(int[] tiles, int tileTypes) {
		int total = 0;
		for(int i = 0; i < tileTypes ; i++) {
			total += tiles[i];
		}
		return total;
	}
}
