import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads data from playerData files and creates a new PlayerData object
 * @author Morgan Firkins (852264)
 * @version 1.1(Implemented useDelimeter())
 */
public class PlayerDataFileReader {
	/**
	 * Purpose: Reads the file
	 * @param dataFile: The file to be read
	 * @return PlayerData: Returns a constructed PlayerData Object
	 */
	public static PlayerData readFile(File dataFile) {
		Scanner fileIn;
		try {
			fileIn = new Scanner(dataFile).useDelimiter(",");
			String name = fileIn.next();
			int numOfWins = fileIn.nextInt();
			int numOfLosses = fileIn.nextInt();
			String avatarPath = fileIn.next();
			fileIn.close();
			return new PlayerData(name,numOfWins,numOfLosses,avatarPath);
		} catch (FileNotFoundException e) {
			System.out.println("Player Data File Not Found");
			return null;
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
}
