import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This class is designed to allow the message of the day to be be obtained from the 
 * website, as is prescribed in the design document. 
 * It should be instanced and then the get result method can be called from the class.
 * 
 * @author Joshua Sinderberry (851800) 
 * @version 1.0
 */
public class MessageOfTheDay {
	
	/**
	 * @private 
	 * This method gets the puzzle in it's raw state. 
	 * @return The puzzle from the web.
	 * @throws IOException
	 */
	private static String getPuzzle() throws IOException {
		URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputLine = in.readLine();
		in.close();
		return inputLine;
		
	}
	
	/**
	 * @private 
	 * This method gets the final message from the web. 
	 * @param param the solution to the puzzle
	 * @return The final message from the web. 
	 * @throws IOException
	 */
	private static String getMessage(String param) throws IOException {
		URL url = new URL("http://cswebcat.swansea.ac.uk/message?solution=" + param);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String outputline = in.readLine();
		in.close();
		outputline = outputline.replace("(", "\n(");
		return outputline;
	}
	
	/**
	 * This method gets the message of the day at at any particular time.
	 * @return The message. 
	 * @throws IOException
	 */
	public static String getResult() throws IOException {
		String paramater = solvePuzzle(getPuzzle());
		return getMessage(paramater);
	}
	
	/**
	 * @private
	 * This method solves the puzzle as is prescribed in API.
	 * @param input
	 * @return
	 */
	private static String solvePuzzle(String input) {
		String output = "CS-230";
		for(int i = 1; i <= input.length(); i++) {
			char c = input.charAt((i-1));
			int charValue = (int) c;
			if(i % 2 == 0) {
				for(int k = i; k > 0; k--) {
					if(charValue == 90){
						charValue = 65;
					} else {
						charValue++;
					}
				}
			} else {
				for(int k = i; k > 0; k--) {
					if(charValue == 65){
						charValue = 90;
					} else {
						charValue--;
					}
				}
				
			}
			output += (char) charValue;
		}
		
		return output + output.length(); 
	}
}
