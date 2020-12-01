import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * File Name: PlayerDataFileReader.java<br>
 * Purpose: To just read player data files<br>
 * @author Morgan Firkins (852264)<br>
 * @version 1.1(Implemented useDelimneter())<br>
 * Created: 30/11/2020
 * Modified: 01/12/2020
 *
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
