import java.util.ArrayList;

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
	
	private Placeable[][] boardTiles;
	private int[][] playersPositions;
	private int curPlayer;
	private boolean isGoalHit;
	private ArrayList<ActionTile>[] actionTilesForEachPlayer;
	
	
	public GameState(Placeable[][] boardTiles, int[][] playersPositions, ArrayList<ActionTile>[] actionTilesForEachPlayer, int curPlayer, boolean isGoalHit) {
		this.boardTiles = boardTiles;
		this.playersPositions = playersPositions;
		this.curPlayer = curPlayer;
		this.actionTilesForEachPlayer = actionTilesForEachPlayer;
	}

	public Placeable[][] getBoard() {
		return boardTiles;
	}

	public int[][] getPlayersPositions() {
		return playersPositions;
	}

	public int getCurPlayer() {
		return curPlayer;
	}
	
	public boolean isGoalHit() {
		return isGoalHit;
	}
	
	public ArrayList<ActionTile> getActionTileForPlayer(int playerNumber){
		return actionTilesForEachPlayer[playerNumber];
	}
	
	

}
