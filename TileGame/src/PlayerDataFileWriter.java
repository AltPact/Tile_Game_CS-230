import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File Name: PlayerDataFileWriter.java<br>
 * Purpose: To generate playerData files<br>
 * Created: 01/12/2020<br>
 * 
 * @author Morgan Firkins (852264)
 * @version 1.0
 */
public class PlayerDataFileWriter {

	public static void generateFile(PlayerData playerData) {
		String fileName = "data/playerdata/" + playerData.getName() + "playerdata.txt";
		try {
			File newFile = new File(fileName);
			if (newFile.createNewFile()) {
				FileWriter writer = new FileWriter(fileName);
				String name = playerData.getName() + " ";
				String wins = Integer.toString(playerData.getWins()) + " ";
				String losses = Integer.toString(playerData.getLosses()) + " ";
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
