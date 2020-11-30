import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * File Name: PlayerData.java Created: 14/11/2020 Modified: 16/11/2020
 * 
 * @author Morgan Firkins (852264) Version: 1.0
 */
public class PlayerData {
	private String name;
	private int numberOfWins;
	private int numberOfLosses;
	private String avatarPath;
	private BufferedImage playerAvatar;

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
		this.avatarPath = avatarPath;
		if (avatarPath == null) {
			this.playerAvatar = null;
		} else {
			try {
				this.playerAvatar = ImageIO.read(new File(avatarPath));

			} catch (IOException e) {
			}
		}
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
		return this.getWins() + this.getLosses();

	}

	/**
	 * This public method gets the BufferedImage PlayerAvatar object
	 * 
	 * @return The player avatar of the PlayerData object
	 */
	public BufferedImage getAvatar() {
		return this.playerAvatar;
	}

	/**
	 * This public method sets the playerAvatar of the PlayerData object
	 * 
	 * @param avatarPath The path where the avatar is located on the system
	 */
	public void setAvatar(String avatarPath) {
		if (avatarPath == null) {
			this.playerAvatar = null;
		} else {
			try {
				this.playerAvatar = ImageIO.read(new File(avatarPath));

			} catch (IOException e) {
			}
		}
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
	 * Converts the playerData object into a string to be able to be able to be 
	 * readeasily
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

}
