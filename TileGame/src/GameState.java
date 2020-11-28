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
	private ArrayList<ActionTilePlaceable> tilesInAction;
	
	private int[] positionOfInsertedTile;
	private Tile insertedTile;
	private int movedPlayer;
	private int[] newXYForMovedPlayer;
	private boolean[][] moveableSpaces;
	private int movesLeftForCurrentPlayer;
	private boolean[][] insertableLocations;
	private boolean placeableActionTileApplied;
	
	
	
	public void setBoard(Placeable[][] board) {
		this.boardTiles = board;
	}
	
	public void setPlayerPositions(int[][] playerPositions) { 
		this.playersPositions = playerPositions;
	}
	
	public void setTilesInAction(ArrayList<ActionTilePlaceable> tilesInAction) {
		this.tilesInAction = tilesInAction;
	}
		
	public void setActionTilesForPlayers(ArrayList<ActionTile>[] actionTilesForEachPlayer) {
		this.actionTilesForEachPlayer = actionTilesForEachPlayer;
	}
	
	public void setChangedTile(Tile changedTile, int[] insertedTilePostion) {
		this.insertedTile = changedTile; 
		this.positionOfInsertedTile = insertedTilePostion;
	}
	
	public void setChangedPlayerPosition(int changedPlayer, int[] xY) {
		this.movedPlayer = changedPlayer;
		this.newXYForMovedPlayer = xY;
		
	}
	
	public void isGoalHit(boolean isGoalHit) {
		this.isGoalHit = isGoalHit;
	}
	
	public void setMoveableSpaces(boolean[][] moveAbleSpaces) {
		this.moveableSpaces = moveAbleSpaces;
	}
	
	public void setCurrentPlayer(int currentPlayer, int movesLeftForCurrentPlayer) {
		this.curPlayer = currentPlayer;
		this.movesLeftForCurrentPlayer = movesLeftForCurrentPlayer;
	}
	
	public void setInsertableLocation(boolean[][] insertableLocations) {
		this.insertableLocations = insertableLocations;
	}
	
	public void setActionTileApplied() {
		placeableActionTileApplied = true;
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
	
	public int[] getPositionOfInsertedTile() {
		return positionOfInsertedTile;
	}
	
	public Tile getChangedTile() {
		return insertedTile;
	}
	
	public int getMovedPlayer() {
		return movedPlayer;
	}
	
	public int[] getPositionMovedTo() {
		return newXYForMovedPlayer;
	}
	
	public boolean[][] getMoveableSpaces(){
		return moveableSpaces;
	}
	
	public ArrayList<ActionTilePlaceable> getTilesInAction(){
		return tilesInAction;
	}
	
	public int getMovesLeftForCurrentPlayer() {
		return movesLeftForCurrentPlayer;
	}
	
	public boolean[][] getInsertableLocations(){
		return insertableLocations;
	}
	
	public boolean hasPlaceableActionTileApplied() {
		return placeableActionTileApplied;
	}

}
