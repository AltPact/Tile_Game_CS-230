import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates playerData files and saves them to the relevant directory
 * @author Morgan Firkins (852264)
 * @version 1.1(Delimited values with commas)
 */

public class PlayerDataFileWriter {

	public static void generateFile(PlayerData playerData) {
		String fileName = "data/playerdata/" + playerData.getName() + "playerdata.txt";
		try {
            File oldFile = new File(fileName);
            oldFile.delete();
			File newFile = new File(fileName);
			if (newFile.createNewFile()) {
				FileWriter writer = new FileWriter(fileName);
				String name = playerData.getName() + ",";
				String wins = Integer.toString(playerData.getWins()) + ",";
				String losses = Integer.toString(playerData.getLosses()) + ",";
				String path = playerData.getPath();
				writer.write(name + wins + losses + path);
				writer.close();
			} else {
				System.out.println("PlayerData File Already Exists. Therefore Player Already Exists");
			}

		} catch (IOException e) {
			System.out.println("An Error has Occured with Writing PlayerData to file");
			e.printStackTrace();
		}

	}

}
