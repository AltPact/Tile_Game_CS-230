 
/* Java Doc Not Yet Complete.
 * Class Not Yet Complete.
 * No implementation for backtrack yet as file reader/writer not yet implemented.
 * No working implementation as silk bag not yet complete.
 * 
 * TO DO TODAY:
 * 		- Implement the relevant game state constructors.
 * 			end Game
 * 		- Past States reduce data.
 *  
 *  Board needs method to get all tiles.
 * 
 */





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
	private ArrayList<GameState> pastStates;
	
	
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
	 * @param players The players, the they do not need to have their positions loaded.
	 */
	public Game(GameState curState, ArrayList<GameState> pastStates, SilkBag bag, PlayerPiece[] players) {
		this.bag = bag;
		this.players = players;
		this.pastStates = pastStates;
		reconstructGame(curState);
		
	}
	
	 //TODO now game class has been reprogrammed, get the data that is relevant for the graphics context.
	
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
		this.tilesInAction = curState.getTilesInAction();
		for(int i = 0; i < players.length ; i++) {
			players[i].setX(curState.getPlayersPositions()[i][0]);
			players[i].setX(curState.getPlayersPositions()[i][1]);
			players[i].setBulkActionTiles(curState.getActionTileForPlayer(i));
		}
	}
	public Tile getNewTileForCurrentPlayer() {
		TileType newTileType = bag.draw();
		Tile newTile = null;
		if(newTileType == TileType.BackTrack) {
			newTile = new BackTrack(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.DoubleMove) {
			newTile = new DoubleMove(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.Ice) {
			newTile = new DoubleMove(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else if (newTileType == TileType.Fire) {
			newTile = new DoubleMove(players[curPlayer]);
			players[curPlayer].addActionTile((ActionTile)newTile);
		} else {
			newTile = new Placeable(newTileType);
		}
		return newTile;
	}
	
	
	public GameState insertTile(Placeable tileToBeInserted, int x, int y, boolean vertical) throws IllegalInsertionException {
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
		
		int[] postion = {x,y};
		return tileAfterInsertion(tileToBeInserted, postion);
	}
	
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
	
	public void playDoubleMove(ActionTile doubleMove) {
		players[curPlayer].playActionTile(doubleMove);
		this.movesRemaingForThisPlayer++;
	}
	
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
	
	public GameState playIce(Ice ice, int x, int y) throws IncorrectTileTypeException {
		players[curPlayer].playActionTile(ice);
		Placeable[] tilesToAction= new Placeable[9];
		tilesToAction[0] = (Placeable) board.getTile((x - 1), (y - 1));
		tilesToAction[1] = (Placeable) board.getTile((x - 1), y);
		tilesToAction[2] = (Placeable) board.getTile((x - 1), (y + 1));
		tilesToAction[3] = (Placeable) board.getTile(x, (y - 1));
		tilesToAction[4] = (Placeable) board.getTile(x, y);
		tilesToAction[5] = (Placeable) board.getTile(x, (y + 1));
		tilesToAction[6] = (Placeable) board.getTile((x + 1), (y - 1));
		tilesToAction[7] = (Placeable) board.getTile((x + 1), y);
		tilesToAction[8] = (Placeable) board.getTile((x + 1) , (y + 1));
		ice.instantiateAction(tilesToAction);
		return actionTilePlayed();
	}
	
	public GameState playFire(Fire fire, int x, int y) throws IncorrectTileTypeException {
		players[curPlayer].playActionTile(fire);
		Placeable[] tilesToAction= new Placeable[9];
		tilesToAction[0] = (Placeable) board.getTile((x - 1), (y - 1));
		tilesToAction[1] = (Placeable) board.getTile((x - 1), y);
		tilesToAction[2] = (Placeable) board.getTile((x - 1), (y + 1));
		tilesToAction[3] = (Placeable) board.getTile(x, (y - 1));
		tilesToAction[4] = (Placeable) board.getTile(x, y);
		tilesToAction[5] = (Placeable) board.getTile(x, (y + 1));
		tilesToAction[6] = (Placeable) board.getTile((x + 1), (y - 1));
		tilesToAction[7] = (Placeable) board.getTile((x + 1), y);
		tilesToAction[8] = (Placeable) board.getTile((x + 1) , (y + 1));
		fire.instantiateAction(tilesToAction);
		return actionTilePlayed();
	}
	
	public GameState moveCurrentPlayer(int direction) throws IllegalMove {
		if (movesRemaingForThisPlayer <= 0) {
			throw new IllegalMove("This player has no moves remaining");
		}
		int curX = players[curPlayer].getX();
		int curY = players[curPlayer].getY();
		boolean[][] moveableSpaces = board.getMoveableSpaces(players[curPlayer]);
		int newX = 0;
		int newY = 0;
		if(direction == 0) {
			newX = curX;
			newY = curY - 1;
		} else if(direction == 1) {
			newX = curX + 1;
			newY = curY;
		} else if(direction == 2) {
			newX = curX;
			newY = curY + 1;
		} else if(direction == 3) {
			newX = curX - 1;
			newY = curY;
		}
		if(moveableSpaces[newX][newY]) {
			players[curPlayer].setX(newX);
			players[curPlayer].setY(newY);
			movesRemaingForThisPlayer--;
			Placeable newTile = (Placeable) board.getTile(newX, newY);
			isGoalReached = newTile.isGoal();
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
		
	public GameState endTurn() {
		curPlayer ++;
		if ((curPlayer % players.length) == 0) {
			curPlayer = 0;
		}
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
	
	private GameState actionTilePlayed() {
		GameState newState = new GameState();
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setActionTileApplied();
		return newState;
	}
	
	private int[][] getPlayerPositions(){
		int[][] playerPositions = new int[players.length][2];
		for(int i = 0; i < players.length; i++) {
			playerPositions[i][0] = players[i].getX();
			playerPositions[i][1] = players[i].getY();
		}
		return playerPositions;
	}
	
	private GameState playerMoved() {
		GameState newState = new GameState();
		newState.setChangedPlayerPosition(curPlayer, getPlayerPositions()[curPlayer]);
		newState.setPlayerPositions(getPlayerPositions());
		newState.isGoalHit(isGoalReached);
		return newState;
	}


	private GameState actionTileBackTrack(int playerBackTracked) {
		GameState newState = new GameState();
		newState.setChangedPlayerPosition(playerBackTracked, getPlayerPositions()[playerBackTracked]);
		newState.setPlayerPositions(getPlayerPositions());
		return newState;
	}
	
	private GameState tileAfterInsertion(Tile t, int[] positionOfInsertedTile) {
		GameState newState = new GameState();
		newState.setChangedTile(t, positionOfInsertedTile);
		newState.setPlayerPositions(getPlayerPositions());
		return newState;	
	}
	
	private GameState makeStateEndTurn() {
		GameState newState = new GameState();
		newState.setPlayerPositions(getPlayerPositions());
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setTilesInAction(tilesInAction);
		
		newState.setCurrentPlayer(curPlayer, movesRemaingForThisPlayer);
		newState.setMoveableSpaces(board.getMoveableSpaces(players[curPlayer]));
		return newState;
	}
	private ArrayList<ActionTile>[] getActionTilesForPlayers() {
		@SuppressWarnings("unchecked")
		ArrayList<ActionTile>[] tilesOwnedByPlayers = new ArrayList[players.length];
		for(int i = 0; i < players.length; i++) {
			tilesOwnedByPlayers[i] = players[i].getActionTilesOwned(); 
		}
		return tilesOwnedByPlayers;
	}
	
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
	
	public GameState getEndGameState() {
		GameState newState = new GameState();
		newState.setActionTilesForPlayers(getActionTilesForPlayers());
		newState.setBoard(board.getTiles(), board.getWidth(), board.getHeight());
		newState.setCurrentPlayer(curPlayer, movesRemaingForThisPlayer);
		newState.setPastStates(pastStates);
		newState.setPlayerPositions(getPlayerPositions());
		newState.setSilkBag(bag);
		newState.setTilesInAction(tilesInAction);
		boolean[] backTrackApplied = new boolean[players.length];
		for(int i = 0; i < players.length; i++) {
			backTrackApplied[i] = players[i].getBacktrack();
		}
		newState.setBackTrackApplied(backTrackApplied);
		return newState;
	}
}
