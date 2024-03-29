import java.util.ArrayList;

/**
 * Game state, used to hold data relating to the state at any given time. 
 * It is designed as a catch all data type to allow multiple items of data 
 * to be parsed between game and other classes in one go.
 * It's comprised of the series of getters and setters to set the infomation when
 * it's needed.
 * @author Alex Ullman (851732) and Joshua Sinderberry (851800)
 * @version 1.0
 */
public class GameState {
	
	private Placeable[][] boardTiles;
	private int[][] playersPositions;
	private int curPlayer;
	private boolean isGoalHit;
	private ArrayList<ActionTile>[] actionTilesForEachPlayer;
	private ArrayList<ActionTilePlaceable> tilesInAction;
	private Tile tileDrawn;
	private int turns;
	
	private int[] positionOfInsertedTile;
	private Tile insertedTile;
	private int movedPlayer;
	private int[] newXYForMovedPlayer;
	private boolean[][] moveableSpaces;
	private int movesLeftForCurrentPlayer;
	private boolean[][] insertableLocations;
	private boolean placeableActionTileApplied;
	private ArrayList<GameState> pastStates;
	private SilkBag bag;
	private boolean[] backTrackApplied;
	private int width;
	private int height;
	private PlayerPiece[] players;
	private boolean hasPlayerInsertedTile;
	
	public void setTurns(int turns) {
		this.turns = turns;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public void setHasPlayerInsertedTile(boolean b) {
		this.hasPlayerInsertedTile = b;
	}
	
	public boolean hasPlayerInsertedTile() {
		return hasPlayerInsertedTile;
	}
	
	public void setPlayers(PlayerPiece[] players) {
		this.players = players;
	}
	
	public PlayerPiece[] getPlayers() {
		return players;
	}
	
	public void setTileDrawn(Tile tileDrawn) {
		this.tileDrawn = tileDrawn;
	}
	
	public Tile getTileDrawn() {
		return tileDrawn;
	}

	public ArrayList<ActionTile>[] getActionTilesForEachPlayer() {
		return actionTilesForEachPlayer; 
	}
	
	public void setBoard(Placeable[][] board, int width, int height) {
		this.boardTiles = board;
		this.width = width;
		this.height = height;
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
	
	public void setActionTilesByPlayerNumber(ArrayList<ActionTile> actionTiles, int playerNum) {
		this.actionTilesForEachPlayer[playerNum] = actionTiles;
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
	
	public void setPastStates(ArrayList<GameState> pastStates) {
		this.pastStates = pastStates;
	}
	
	public ArrayList<GameState> getPastStates(){
		return pastStates;
	}
	
	public void setSilkBag(SilkBag bag) {
		this.bag = bag;
	}
	
	public SilkBag getSilkBag() {
		return bag;
	}
	
	public boolean[] getBackTrackApplied() {
		return backTrackApplied;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean getIsGoalHit() {
		return isGoalHit;
	}

}
