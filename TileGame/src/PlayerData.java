/**
 * File Name: PlayerData.java<br>
 * Created: 14/11/2020<br>
 * Modified: 30/11/2020<br>
 * 
 * @author Morgan Firkins (852264)
 * @version: 1.4(Added compareTo)
 */
public class PlayerData implements Comparable<PlayerData> {
	private String name;
	private int numberOfWins;
	private int numberOfLosses;
	private int numberOfGames;
	private String avatarPath;

	/**
	 * Constructor to create PlayerData
	 * 
	 * @param name           The name of the player
	 * @param numberOfWins   The number of wins the player has had
	 * @param numberOfLosses The number of losses the player has had
	 * @param avatarPath     The path where the player's avatar is stored
	 */
	public PlayerData(String name, int numberOfWins, int numberOfLosses, String avatarPath) {
		this.name = name;
		this.numberOfWins = numberOfWins;
		this.numberOfLosses = numberOfLosses;
		this.numberOfGames = numberOfWins + numberOfLosses;
		this.avatarPath = avatarPath;
	}

	/**
	 * This public method gets the name of the PlayerData object
	 * 
	 * @return The name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * This public method sets the name of the PlayerData object
	 * 
	 * @param name The name of the PlayerData object
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This public method gets the number of wins of the PlayerData object
	 * 
	 * @return The number of wins of the player
	 */
	public int getWins() {
		return this.numberOfWins;
	}

	/**
	 * This public method sets the number of wins of the PlayerData object
	 * 
	 * @param numberOfWins The number of wins of the PlayerData object
	 */
	public void setWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	/**
	 * This public method gets the number of losses of the PlayerData object
	 * 
	 * @return The number of losses of the player
	 */
	public int getLosses() {
		return this.numberOfLosses;
	}

	/**
	 * This public method sets the number of losses of the PlayerData object
	 * 
	 * @param numberOfLosses The number of wins of the PlayerData object
	 */
	public void setLosses(int numberOfLosses) {
		this.numberOfLosses = numberOfLosses;
	}

	/**
	 * This public method gets the number of games of the PlayerData object
	 * 
	 * @return The number of games the player has played
	 */
	public int getGames() {
		return this.numberOfGames;

	}

	/**
	 * This public method gets the path of the avatar of the player
	 *
	 * @return The path of the avatar of the player
	 */
	public String getAvatarPath() {
		return avatarPath;
	}

	/**
	 * This public method sets the avatarPath of the PlayerData object
	 * 
	 * @param newPath The new location path
	 */
	public void setPath(String newPath) {
		this.avatarPath = newPath;
	}

	/**
	 * This public method gets the path of the avatar of the PlayerData object
	 * 
	 * @return String: The path of the avatar of the player
	 */
	public String getPath() {
		return this.avatarPath;

	}

	/**
	 * Adds 1 to the number of wins.
	 */
	public void incrementWins() {
		numberOfWins++;
	}

	/**
	 * Adds 1 to the number of losses.
	 */
	public void incrementLosses() {
		numberOfLosses++;
	}

	@Override
	/**
	 * Converts the playerData object into a string to be able to be able to be read
	 * easily
	 * 
	 * @return result The culmination of the playerData attributes as a string
	 */
	public String toString() {
		String result = "Player Name: " + this.getName() + "\n";
		result += "Amount of Games: " + this.getGames() + "\n";
		result += "Wins: " + this.getWins() + " Losses: " + this.getLosses() + "\n";
		result += "Avatar Location: " + this.avatarPath;
		return result;
	}

	@Override
	/**
	 * Purpose: compares player data objects to each other for leaderboard
	 * 
	 * @param otherPlayer: An arbritary player object to compare
	 * @return int: The priority of the object
	 */
	public int compareTo(PlayerData otherPlayer) {
		/**
		 * if the wins of current player object is greater than the wins of the other
		 * player object then return -1<br>
		 * if the wins of the current player object is less than the wins of the other
		 * player object then return +1<br>
		 * if the wins of both objects are the same then return 0
		 */
		int playerWins = this.getWins();
		int otherWins = otherPlayer.getWins();
		if (playerWins > otherWins) {
			return -1;
		} else if (playerWins < otherWins) {
			return +1;
		} else if (playerWins == otherWins) {
			return 0;
		} else {
			return 99;
		}

	}

}
