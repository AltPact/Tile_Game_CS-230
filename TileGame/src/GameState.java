/*
 * Class not yet complete, can be added to with what ever game window 
 * requires to display the game.
 */

/**
 * Game state, used to hold data relating to the state at any given time. 
 * @author Alex Ullman (851732) and Joshua Sinderberry (851800)
 * @version 1
 *
 */
public class GameState {
	
	private Board board;
	private PlayerPiece[] players;
	private int curPlayer;
	
	public GameState(Board board, PlayerPiece[] players, int curPlayer) {
		this.board = board;
		this.players = players;
		this.curPlayer = curPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public PlayerPiece[] getPlayers() {
		return players;
	}

	public int getCurPlayer() {
		return curPlayer;
	}

}
