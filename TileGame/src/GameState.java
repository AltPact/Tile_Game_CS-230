
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
