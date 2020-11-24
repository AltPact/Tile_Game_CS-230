import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class FileReader {
	
	public static void readBoardFile(String filename) {
		try {
			File f = new File(filename);
			Scanner s = new Scanner(f);
			int width = s.nextInt();
			int height = s.nextInt();
			/* read all fixed tiles */
			int numFixedTiles = s.nextInt();
			Tile[] fixedTiles = new Tile[numFixedTiles];
			int[][] fixedTileCoords = new int[numFixedTiles][2];
			for (int t = 0; t < numFixedTiles; t++) {
				int x = s.nextInt();
				int y = s.nextInt();
				String tileTypeStr = s.next();
				TileType tileTypeEnum = TileType.valueOf(tileTypeStr);
				int orientation = s.nextInt();
				boolean isGoal = s.nextBoolean();
				fixedTiles[t] = new Placeable(tileTypeEnum, isGoal, true);
				fixedTiles[t].setOrientation(orientation);
				fixedTileCoords[t][0] = x;
				fixedTileCoords[t][1] = y;
			}
			/* read all tiles to go in the silk bag */
			/* TODO: Tile is an abstract class, can't be constructed using only tileType. Stored as strings for now */
			int numBagTiles = s.nextInt();
			String[] bagTiles = new String[numBagTiles];
			for (int t = 0; t < numBagTiles; t++) {
				bagTiles[t] = s.next();
			}
			/* read all action tiles on the board */
			/* TODO: these should be read at the same time as fixedTiles. file format should be changed later. */
			int numActionTiles = s.nextInt();
			String[] actionTiles = new String[numActionTiles]; // TODO: this needs to be not a string.
			for (int t = 0; t < numActionTiles; t++) {
				// TODO: much more information will be needed than just tile type, will need to talk about this
				actionTiles[t] = s.next();
			}
			
			s.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
