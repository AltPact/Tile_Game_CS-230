public class Game {
	/**
	 * File Name: Game.java Created: 14/11/2020 Modified: 15/11/2020
	 * 
	 * @author Wan Fai Tong (1909787) and Adem Arik (850904) Version: 1.0
	 */
	//This should be replaced by Board object 
	private static Tile gameboard[][];
	private static PlayerPiece playerArray[];
	private static int turns;
	
	/**
	 * Constructor of GameWindow
	 * @param playerAray The array reference of player piece object
	 */
	public Game(PlayerPiece[] playerArray) {
		this.playerArray=playerArray;
		turns=0;
		//intialize game board using game board class object
		//but tile class hasn't completed yet so just type a comment here
	}
	
	/**
	 * Constructor of GameWindow
	 * for loading game history
	 * @param gameboard The array reference of player piece object
	 * @param playerAray The array reference of player piece object
	 * @param turns The number of turns
	 */
	//This constructor is for loading previous game history
	public Game(Tile gameboard[][], PlayerPiece[] playerArray, int turns) {
		this.gameboard=gameboard;
		this.playerArray=playerArray;
		this.turns=turns;
	}

	/**
	 * Method for game window
	 * @return The array of player piece
	 */
	public static PlayerPiece[] getPlayerArray() {
		return playerArray;
	}
	
	/**
	 * Method for game window
	 * @return The gameboard
	 */
	public static Tile[][] getGameboard() {
		return gameboard;
	}

	/**
	 * Temporary method for testing the game window
	 * @param gameboard The gameboard
	 */
	public static void setGameboard(Tile[][] gameboard) {
		Game.gameboard = gameboard;
	}

	/**
	 * Method for game window
	 * @return turns
	 */
	public static int getTurns() {
		return turns;
	}

	/**
	 * Method that increase the turns numbers
	 */
	public static void nextTurns() {
		Game.turns += 1;
	}
	
	

}
