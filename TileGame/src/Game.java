
public class Game {
	//Since it is an offline game, there would be only 1 game 
	//running at a time(saved game data isn't running, is paused)
	//so I use static here
	
	//2 dimension array that stores the tile objects
	private static Tile gameboard[][];
	//will use the getter of player position method in PlayerPiece
	private static PlayerPiece playerArray[];
	private static int turns;
	
	//This constructor is for create new game
	public Game(PlayerPiece[] playerArray) {
		this.playerArray=playerArray;
		turns=0;
		//intialize game board using game board class object
		//but tile class hasn't completed yet so just type a comment here
	}
	//This constructor is for loading previous game history
	public Game(Tile gameboard[][], PlayerPiece[] playerArray, int turns) {
		this.gameboard=gameboard;
		this.playerArray=playerArray;
		this.turns=turns;
	}

	//method returning the PlayerArray(mostly for game window)
	public static PlayerPiece[] getPlayerArray() {
		return playerArray;
	}
	//method returning the gameboard(mostly for game window)
	public static Tile[][] getGameboard() {
		return gameboard;
	}

	//method for updating the board when tiles change
	public static void setGameboard(Tile[][] gameboard) {
		Game.gameboard = gameboard;
	}

	public static int getTurns() {
		return turns;
	}

	//method that will be called when next turn
	public static void nextTurns() {
		Game.turns += 1;
	}
	
	

}
