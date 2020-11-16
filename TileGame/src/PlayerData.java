import java.awt.image.BufferedImage;

/**
 * File Name: PlayerData.java
 * Created: 14/11/2020
 * Modified: 16/11/2020
 * @author Morgan Firkins
 * Version: 1.0
 */
public class PlayerData {
	private String name;
	private int numberOfWins;
	private int numberOfLosses;
	private BufferedImage playerAvatar;
	
	public PlayerData(String name, int numberOfWins, int numberOfLosses, BufferedImage playerAvatar) {
		this.name = name;
		this.numberOfWins = numberOfWins;
		this.numberOfLosses = numberOfLosses;
		this.playerAvatar = playerAvatar;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumberOfWins() {
		return this.numberOfWins;
	}
	
	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}
	
	public int getNumberOfLosses() {
		return this.numberOfLosses;
	}
	
	public void setNumberOfLosses(int numberOfLosses) {
		this.numberOfLosses = numberOfLosses;
	}
	
	public BufferedImage getPlayerAvatar() {
		return this.playerAvatar;
	}
	
	public void setPlayerAvatar(BufferedImage playerAvatar) {
		this.playerAvatar = playerAvatar;
	}
	
	
	

}
