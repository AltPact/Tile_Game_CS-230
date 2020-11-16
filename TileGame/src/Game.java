
public class Game {
	//2 dimension array that stores the tile objects
	private static Tile gameboard[][];
	//will use the getter of player position method in PlayerPiece
	private static PlayerPiece playerArray[];
	private static int turns;
	
	public Game(PlayerPiece[] playerArray) {
		this.playerArray=playerArray;
		turns=0;
		//intialize game board using game board class object
		//but tile class hasn't completed yet so just type a comment here
	}

	public static Tile[][] getGameboard() {
		return gameboard;
	}

	public static void setGameboard(Tile[][] gameboard) {
		Game.gameboard = gameboard;
	}

	public static int getTurns() {
		return turns;
	}

	public static void nextTurns() {
		Game.turns += 1;
	}
	
	

}
