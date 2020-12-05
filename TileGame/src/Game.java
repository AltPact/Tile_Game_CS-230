import java.util.ArrayList;
/**
 * Game Class is designed to implement the game.
 * It has the ability for a player to draw a tile, play a action tile,
 * place a placeable tile and move, when each is required.
 * @author Wan Fai Tong (1909787), Alex Ullman (851732) and Joshua Sinderberry (851800)
 * 
 * @version 1.2
 *
 */
public class Game {
	
	private Board board;
	private SilkBag bag;
	private PlayerPiece[] players;
	private int curPlayer;
	private int movesRemaingForThisPlayer = 1;
	private ArrayList<ActionTilePlaceable> tilesInAction;
	private boolean isGoalReached;
	private ArrayList<GameState> pastStates = new ArrayList<GameState>();
	private boolean canPlayerInsertTile;
	
	
	/**
	 * This constructor is designed to be used to construct a new 
	 * game, it should have a silk bag, the players and the board parsed to it.
	 * @param bag The silk bag that should operate this game
	 * @param players The players, they should already have their starting positions loaded.
	 * @param board The game board that should be played. 
	 */
	public Game(SilkBag bag, PlayerPiece[] players, Board board) {
		this.board = board;
		this.bag = bag;
		this.players = players;
		this.curPlayer = 0;
		this.movesRemaingForThisPlayer = 1;
		this.canPlayerInsertTile = true;
		this.tilesInAction = new ArrayList<ActionTilePlaceable>();
		this.isGoalReached = false;
		this.pastStates = new ArrayList<GameState>();
		
	}
	
	/**
	 * This constructor is designed to be used to construct a game that is already in play.
	 * It rebuilds a game based on the curState that has been parsed to it by the caller.
	 * It also takes a array list of past states, a silk bag that is fully populated. 
	 * @param curState The state the game should be loaded into
	 * @param pastStates The past states of the game.
	 * @param bag The silk bag.
	 * @param players The players
	 */
	public Game(GameState curState, ArrayList<GameState> pastStates, SilkBag bag, PlayerPiece[] players) {
		this.bag = bag;
		this.players = players;
		this.pastStates = pastStates;
		reconstructGame(curState);
		
	}
	
	/**
	 * @private
	 * This method is designed to be able to build the game from a current state.
	 * @param curState The state the game should be loaded into.
	 */
	private void reconstructGame(GameState curState) {
		this.board = new Board(curState.getBoard().length, curState.getBoard()[0].length, curState.getBoard());
		this.tilesInAction = curState.getTilesInAction();
		this.curPlayer = curState.getCurPlayer();
		this.movesRemaingForThisPlayer = curState.getMovedPlayer();
		this.canPlayerInsertTile = curState.hasPlayerInsertedTile();
	}
	
	/**
	 * This method gets a new tile for a player
	 * If the drawn tile is action tile, then the tile is
	 * added to their array list. 
	 * @return A game state with a updated list of action tiles and the tile that has been drawn.
	 */
	public GameState getNewTileForCurrentPlayer() {
		TileType newTileType = bag.draw();
		Tile newTile = null;
		if(newTileType == TileType.BackTrack) {
			newTile = new BackTrack(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.DoubleMove) {
			newTile = new DoubleMove(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.Ice) {
			newTile = new Ice(players[curPlayer], players.length);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.Fire) {
			newTile = new Fire(players[curPlayer], players.length);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else {
			newTile = new Placeable(newTileType);
		}
		
		GameState newState = new GameState();
		newState.setTileDrawn(newTile);
		newState.setActionTilesForPlayers(getActionTilesForPlayers());
		newState.setInsertableLocation(board.getInsertablePlaces());
		return newState;
	}
	
	/**
	 * This method allows a tile to be inserted into the board. 
	 * @param tileToBeInserted The tile that is being inserted.
	 * @param x The x coordinate of the tile.
	 * @param y The y coordinate of the tile.
	 * @param vertical If the tile is a vertical insertion or horizontal
	 * @return A new game state: see the tileAfterInsertion method for details
	 * @throws IllegalInsertionException
	 */
	public GameState insertTile(Placeable tileToBeInserted, int x, int y, boolean vertical) throws IllegalInsertionException {
		if(!canPlayerInsertTile) {
			throw new IllegalInsertionException("This player has already inserted a tile");
		}
		boolean isInserted = board.insertPiece(x, y, vertical, tileToBeInserted);
		if(!isInserted) {
			throw new IllegalInsertionException("This tile cannot be inserted");
		}
		
		int directionOfInsertion = 0;
		int affectedRowColumn = 0;
		if(vertical) {
			affectedRowColumn = y;
			if(y == 0) {
				directionOfInsertion = 2;
				
			} else {
				directionOfInsertion = 0;
			}
		} else {
			affectedRowColumn = x;
			if(x == 0) {
				directionOfInsertion = 1;
			} else {
				directionOfInsertion = 3;
			}
		}

		movePlayersOn(directionOfInsertion, affectedRowColumn);
		
		int[] postion = {y,x};
		canPlayerInsertTile = false;
		return tileAfterInsertion(tileToBeInserted, postion);
	}
	
	/**
	 * @private
	 * This method moves a players around if a tile is inserted and their 
	 * position needs to be moved.
	 * @param directionOfInsertion The direction of the insertion e.g. 0 if it is comming from the bottom.
	 * @param affectedRowColumn The affected row or column.
	 */
	private void movePlayersOn(int directionOfInsertion, int affectedRowColumn) {
		for(PlayerPiece player : players) {
			//if the inserted tile effects a vertical column
			if(((directionOfInsertion == 0) || (directionOfInsertion == 2)) && player.getY() == affectedRowColumn) { 
				int testY = player.getY();
				//Makes the appropriate change
				if (directionOfInsertion == 0) {
					testY -= 1;
				} else if (directionOfInsertion == 2) {
					testY += 1;
				}
				
				//If the players position is now illegal, but them back on the board.
				if(testY >= board.getHeight() || testY < 0) {
					if (directionOfInsertion == 0) {
						testY -= (board.getHeight() - 1);
					} else if (directionOfInsertion == 2) {
						testY += 0;
					}
				}
				player.setY(testY);
			//If the inserted tile effects a horizontal row.
			} else if (((directionOfInsertion == 1) || (directionOfInsertion == 1)) && player.getX() == affectedRowColumn) { 
				int testX = player.getX();
				//Makes the appropriate change
				if (directionOfInsertion == 3) {
					testX -= 1;
				} else if (directionOfInsertion == 1) {
					testX += 1;
				}
				
				//If the players position is now illegal, but them back on the board.
				if(testX >= board.getWidth() || testX < 0) {
					if (directionOfInsertion == 3) {
						testX -= (board.getWidth() - 1);
					} else if (directionOfInsertion == 1) {
						testX += 0;
					}
				}
				player.setX(testX);
			} 
		}
	}
	
	/**
	 * Allows a player to play a double move tile
	 * @param doubleMove The tile they wish to play.
	 */
	public void playDoubleMove(ActionTile doubleMove) {
		players[curPlayer].playActionTile(doubleMove);
		this.movesRemaingForThisPlayer++;
	}
	
	/**
	 * Method that allows a player to play a backtrack tile as is described in
	 * the specification.
	 * @param backtrack The tile that they wish to play
	 * @param playerAgainst The player they wish to play against (as a int)
	 * @return A new game state see : actionTileBackTrack for more details.
	 * @throws IllegalBackTrackException
	 */
	public GameState playBackTrack(ActionTile backtrack, int playerAgainst) throws IllegalBackTrackException {
		if (players[playerAgainst].getBacktrack()) {
			throw new IllegalBackTrackException(playerAgainst + " Has already had backtrack applied");
		}
		players[curPlayer].playActionTile(backtrack);
		int i = pastStates.size();
		GameState twoMovesAgo = null;
		try {
			GameState testState = pastStates.get(i);
			while(testState.getCurPlayer() != playerAgainst) {
				i--;
				testState = pastStates.get(i);
			}
			twoMovesAgo = pastStates.get(i - players.length);

			int backTrackedX = twoMovesAgo.getPlayersPositions()[playerAgainst][0];
			int backTrackedY = twoMovesAgo.getPlayersPositions()[playerAgainst][1];
		
			Placeable testTile = (Placeable) board.getTile(backTrackedX, backTrackedY);
			
			if(testTile.isOnFire()) {
				GameState oneMoveAgo = pastStates.get(i);
				backTrackedX = oneMoveAgo.getPlayersPositions()[playerAgainst][0];
				backTrackedY = oneMoveAgo.getPlayersPositions()[playerAgainst][1];
				testTile = (Placeable) board.getTile(backTrackedX, backTrackedY);
				if(testTile.isOnFire()) {
					throw new IllegalBackTrackException("All of the previous places are now on fire");
				}
				else {
					players[playerAgainst].setX(backTrackedX);
					players[playerAgainst].setY(backTrackedY);
				}
			} else {
				players[playerAgainst].setX(backTrackedX);
				players[playerAgainst].setY(backTrackedY);
			}
			players[playerAgainst].setBacktrack(true);
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalBackTrackException("Not enough moves made to conduct backtrack against player " + playerAgainst);
		} finally {
			players[curPlayer].addActionTile(new BackTrack(players[curPlayer]));
		}
		
		return actionTileBackTrack(playerAgainst);

	}
	
	/**
	 * Method that allows the player to play a ice tile.
	 * @param ice The tile to be played.
	 * @param x The x coordinate of it's location.
	 * @param y The y coordinate of it's location.
	 * @return a new Game State: see actionTilePlayed for more details.
	 * @throws IncorrectTileTypeException
	 */
	public GameState playIce(Ice ice, int x, int y) throws IncorrectTileTypeException {
		players[curPlayer].playActionTile(ice);	
		Placeable[] tilesToAction= new Placeable[9];
		try {
			tilesToAction[0] = (Placeable) board.getTile((y - 1), (x - 1));
			tilesToAction[1] = (Placeable) board.getTile((y - 1), x);
			tilesToAction[2] = (Placeable) board.getTile((y - 1), (x + 1));
			tilesToAction[3] = (Placeable) board.getTile(y, (x - 1));
			tilesToAction[4] = (Placeable) board.getTile(y, x);
			tilesToAction[5] = (Placeable) board.getTile(y, (x + 1));
			tilesToAction[6] = (Placeable) board.getTile((y + 1), (x - 1));
			tilesToAction[7] = (Placeable) board.getTile((y + 1), x);
			tilesToAction[8] = (Placeable) board.getTile((y + 1) , (x + 1));
		} catch (IllegalStateException e) {
			//Continues processing as tile does not exists, if the tile is too close to the board.
		} finally {
			ice.instantiateAction(tilesToAction);
		}
		return actionTilePlayed();
	}
	
	/**
	 * Method that allows the player to play a Fire Tile.
	 * @param fire The tile they wish to play.
	 * @param x The x coordinate of it's location
	 * @param y The y coordinate of it's location.
	 * @return a new Game State: see actionTilePlayed for more details.
	 * @throws IncorrectTileTypeException
	 * @throws IllegalFireException 
	 */
	public GameState playFire(Fire fire, int x, int y) throws IncorrectTileTypeException, IllegalFireException {
		players[curPlayer].playActionTile(fire);
		Placeable[] tilesToAction= new Placeable[9];
		try {
			tilesToAction[0] = getTileFire((y - 1), (x - 1));
			tilesToAction[1] = getTileFire((y - 1), x);
			tilesToAction[2] = getTileFire((y - 1), (x + 1));
			tilesToAction[3] = getTileFire(y, (x - 1));
			tilesToAction[4] = getTileFire(y, x);
			tilesToAction[5] = getTileFire(y, (x + 1));
			tilesToAction[6] = getTileFire((y + 1), (x - 1));
			tilesToAction[7] = getTileFire((y + 1), x);
			tilesToAction[8] = getTileFire((y + 1) , (x + 1));
		} catch (IllegalStateException e) {
			//Continues processing as tile does not exists, if the tile is too close to the board.
		} finally {
			fire.instantiateAction(tilesToAction);
		}
		return actionTilePlayed();
	}
	
	/**
	 * This method gets a tile in a specific location.
	 * It does this while checking for players. It ensures 
	 * that a fire tile cannot be called on a tile where a player
	 * currently is.
	 * @param x the x coordinate of the tile.
	 * @param y the y coordinate of the tile
	 * @return the tile if it exists, null if it does not.
	 * @throws IllegalFireException
	 */
	private Placeable getTileFire(int x, int y) throws IllegalFireException {
		for(PlayerPiece p : players) {
			if((p.getX() == x) && (p.getY() == y)){
				throw new IllegalFireException("Player exists on X = " + x + " and Y = " + y);
			}
		}
		return (Placeable) board.getTile(x, y);
	}

	
	/**
	 * Moves the current player. 
	 * @param direction The way in which they wish to move note 0 = North etc.
	 * @return A new game state, see playerMoved for details.
	 * @throws IllegalMove
	 */
	public GameState moveCurrentPlayer(int newX, int newY) throws IllegalMove {
		//If the number of moves left is <= 0.
		if (movesRemaingForThisPlayer <= 0) {
			throw new IllegalMove("This player has no moves remaining");
		}
		boolean[][] moveableSpaces = board.getMoveableSpaces(players[curPlayer]);
		//If the player can move properly
		if(moveableSpaces[newY][newX]) {
			players[curPlayer].setY(newY);
			players[curPlayer].setX(newX);
			movesRemaingForThisPlayer--;
			Placeable newTile = (Placeable) board.getTile(newY, newX);
			isGoalReached = newTile.isGoal();
			//If the goal is reached, end the game. 
			if(isGoalReached) {
				for(int i = 0; i < players.length; i++) {
					if(i == curPlayer) {
						players[i].getLinkedData().incrementWins();
					} else {
						players[i].getLinkedData().incrementLosses();
					}
				}
			}
			return playerMoved();
		} else {
			throw new IllegalMove("Player Cannot move in this Direction");
		}
	}
	
	/**
	 * This method should be called at the end of every turn. 
	 * It is designed to set the game up for the next turn
	 * @return a new Game state, see makeStateEndTurn for details.
	 */
	public GameState endTurn() {
		curPlayer ++;
		if ((curPlayer % players.length) == 0) {
			curPlayer = 0;
		}
		movesRemaingForThisPlayer = 1;
		canPlayerInsertTile = true;
		for(ActionTilePlaceable tile : tilesInAction) {
			tile.decrementTime();
		}
		GameState newState = makeStateEndTurn();
		this.pastStates.add(newState);
		if(pastStates.size() > players.length * 2) {
			pastStates.remove(0);
		}
		
		return newState;
		
	}
	
	/**
	 * @private
	 * A method that makes a GameState after the actionTile has been played.
	 * It contains the board and actionTileApplied is set to true.
	 * @return
	 */
	private GameState actionTilePlayed() {
		GameState newState = new GameState();
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setActionTileApplied();
		return newState;
	}
	
	/**
	 * @private
	 * This method put's the player positions in a way that game states 
	 * can understand 
	 * @return A new Game State 
	 */
	private int[][] getPlayerPositions(){
		int[][] playerPositions = new int[players.length][2];
		for(int i = 0; i < players.length; i++) {
			playerPositions[i][0] = players[i].getX();
			playerPositions[i][1] = players[i].getY();
		}
		return playerPositions;
	}
	
	/**
	 * This method makes a game state after a player has been moved.
	 * It contains all the player positions
	 * The position and number of the specific player that has been moved.
	 * A boolean value to show if the goal has been reached. 
	 * @return A new Game State
	 */
	private GameState playerMoved() {
		GameState newState = new GameState();
		newState.setChangedPlayerPosition(curPlayer, getPlayerPositions()[curPlayer]);
		newState.setPlayerPositions(getPlayerPositions());
		newState.isGoalHit(isGoalReached);
		return newState;
	}

	/**
	 * @private
	 * This produces a gameState after a backtrack tile has been applied.
	 * It shows the positions of all players and the positions of the 
	 * player that has been backtracked.
	 * @param playerBackTracked the player that has been backtracked
	 * @return A new GameState
	 */
	private GameState actionTileBackTrack(int playerBackTracked) {
		GameState newState = new GameState();
		newState.setChangedPlayerPosition(playerBackTracked, getPlayerPositions()[playerBackTracked]);
		newState.setPlayerPositions(getPlayerPositions());
		return newState;
	}
	
	/**
	 * @private 
	 * This method should be called after a tile has been placed.
	 * It produces a game state with specific details about the placed tile and the overall board.
	 * @param t The specific tile that has been placed.
	 * @param positionOfInsertedTile a [x,y]} array showing where the tile has been placed
	 * @return A new game state.
	 */
	private GameState tileAfterInsertion(Tile t, int[] positionOfInsertedTile) {
		GameState newState = new GameState();
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setChangedTile(t, positionOfInsertedTile);
		newState.setPlayerPositions(getPlayerPositions());
		return newState;	
	}
	
	/**
	 * @private 
	 * This method makes a game state, with all of the relevant data.
	 * It contains the all of the player positions, the board, as tiles,
	 * the current player, move-able spaces and insert-able locations,
	 * and the tiles that are in action.
	 * @return  A new game state.
	 */
	private GameState makeStateEndTurn() {
		GameState newState = new GameState();
		newState.setPlayerPositions(getPlayerPositions());
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setTilesInAction(tilesInAction);
		newState.setCurrentPlayer(curPlayer, movesRemaingForThisPlayer);
		newState.setMoveableSpaces(board.getMoveableSpaces(players[curPlayer]));
		newState.setInsertableLocation(board.getInsertablePlaces());
		return newState;
	}
	
	/**
	 * Makes a array of arraylists, containing all of the tiles owned by each player. 
	 * @return A array of ArrayLists containing the action tiles for each player. 
	 */
	private ArrayList<ActionTile>[] getActionTilesForPlayers() {
		@SuppressWarnings("unchecked")
		ArrayList<ActionTile>[] tilesOwnedByPlayers = new ArrayList[players.length];
		for(int i = 0; i < players.length; i++) {
			tilesOwnedByPlayers[i] = players[i].getActionTilesOwned(); 
		}
		return tilesOwnedByPlayers;
	}

	/**
	 * Makes the initial game state of the game.
	 * @return A game state with all of the data to draw the board.
	 */
	public GameState getInitalGameState() {
		GameState newState = new GameState();
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setActionTilesForPlayers(getActionTilesForPlayers());
		newState.setCurrentPlayer(curPlayer, movesRemaingForThisPlayer);
		newState.setMoveableSpaces(board.getMoveableSpaces(players[curPlayer]));
		newState.setPlayerPositions(getPlayerPositions());
		newState.setInsertableLocation(board.getInsertablePlaces());
		this.pastStates.add(newState);
		return newState;
	}
	
	/**
	 * A method that gets all of the data required to rebuild the game 
	 * to save it. 
	 * @return
	 */
	public GameState getEndGameState() {
		GameState newState = new GameState();
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setCurrentPlayer(curPlayer, movesRemaingForThisPlayer);
		newState.setPastStates(pastStates);
		newState.setActionTilesForPlayers(getActionTilesForPlayers());
		newState.setSilkBag(bag);
		newState.setTilesInAction(tilesInAction);
		newState.setPlayers(players);
		return newState;
	}
	
	public GameState getCurrentGameState() {
		GameState newState = new GameState();
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setActionTilesForPlayers(getActionTilesForPlayers());
		newState.setCurrentPlayer(curPlayer, movesRemaingForThisPlayer);
		newState.setMoveableSpaces(board.getMoveableSpaces(players[curPlayer]));
		newState.setPlayerPositions(getPlayerPositions());
		newState.setInsertableLocation(board.getInsertablePlaces());
		return newState;
	}
	
	/**
	 * A toString method to ensure that the game has been created correctly. 
	 */
	public String toString() {
		return "Number of Players: " + players.length + " Current Player: " + curPlayer;
		
	}
}
