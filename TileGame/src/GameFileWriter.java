import java.io.*;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.stage.Popup;
/**
 * This class is designed to write a game to file.
 * All of its methods are static.
 * @author  Sam Steadman (1910177), Alex Ullman (851732), Joshua Sinderberry (851800) and Adem Arik (850904)
 * @version 2
 */
public class GameFileWriter {

    final static char DEL = ',';

	/**
	 * A public static method that saves a game to file
	 * @param g The current state of the game.
	 * @param filename The file name to be saved.
	 * @param players The player array.
	 */
    public static void writeGameFile(GameState g, String filename) {
        try {
        	String fileAndPath = ("./data/savedgame/" + filename);
            File f = new File(fileAndPath);
            f.createNewFile();
            FileWriter w = new FileWriter(fileAndPath); 
            
            PlayerPiece[] players = g.getPlayers();
            
            /* write game metadata */
            w.write(String.valueOf(g.isGoalHit()) + DEL);
            w.write(String.valueOf(g.getCurPlayer()) + DEL);
	    w.write(String.valueOf(g.getTurns()) + DEL);
            w.write(String.valueOf(g.getMovesLeftForCurrentPlayer()) + DEL);
            w.write(String.valueOf(g.hasPlayerInsertedTile()) + DEL);
            w.write(String.valueOf(players.length) + DEL);
            
            
            /* write player piece data */
            for (int p = 0; p < players.length; p++) {
            	w.write(String.valueOf(players[p].getX()) + DEL);
            	w.write(String.valueOf(players[p].getY()) + DEL);
                w.write(String.valueOf(players[p].getColour()) + DEL);
                w.write(String.valueOf(players[p].getBacktrack()) + DEL);
                w.write(players[p].getLinkedData().getName() + "playerdata.txt" + DEL);
            }
            
            /* write current game state */
            writeCurrentGameState(g, w, players);


            /* write the silk bag */
            writeSilkBag(g.getSilkBag(), w);

            w.close();
            return;
        } catch (IOException e) {
			Label errorLabel = new Label("Game Save Unsuccessful");
			Popup errorDialog = new Popup();
			errorDialog.getContent().add(errorLabel);
        }
    }
    
    /**
     * @private
     * This method writes a game state to file. It is designed to write 
     * all of the data to file that game needs to reconstruct the game. 
     * @param s The state to be written.
     * @param w The file writer 
     * @param players The Players
     */
    private static void writeCurrentGameState(GameState s, FileWriter w, PlayerPiece[] players) {
        try {
            
            /* write tile data */
            Placeable[][] tiles = s.getBoard();
            int width = tiles.length;
    		int height = tiles[0].length;
    		
    		w.write(String.valueOf(width) + DEL);
    		w.write(String.valueOf(height)+ DEL);
            
    		/* Write Tile Data to file */
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (tiles[y][x] == null) {  // is the tile full?
                        w.write("false" + DEL);
                    } else {
                        w.write("true" + DEL);
                        w.write(tiles[y][x].getType().toString() + DEL);
                        w.write(String.valueOf(tiles[y][x].isFixed()) + DEL);
                        w.write(String.valueOf(tiles[y][x].getOrientation()) + DEL);
                        w.write(String.valueOf(tiles[y][x].isOnFire()) + DEL);
                        w.write(String.valueOf(tiles[y][x].isFrozen()) + DEL);
                    }
                }
            }

            /* write action tiles in players hand */
            ArrayList<ActionTile>[] actionTilesOwnedTotal = s.getActionTilesForEachPlayer();
            for(ArrayList<ActionTile> actionTilesOwned : actionTilesOwnedTotal) {
            	w.write(String.valueOf(actionTilesOwned.size())+ DEL);
            	for(ActionTile actionTile : actionTilesOwned) {
            		w.write(String.valueOf(actionTile.getType()) + DEL);
            		
            	}
            }
            
            /* Write the Tiles in Action */
            ArrayList<ActionTilePlaceable> tilesInAction = s.getTilesInAction();
            w.write(String.valueOf(tilesInAction.size()) + DEL);
            for(ActionTilePlaceable tile : tilesInAction) {
            	PlayerPiece owner = tile.getOwner();
            	int i = 0;
            	boolean correctPlayerFound = false;
            	while (i < players.length && !correctPlayerFound) {
            		if(owner == players[i]) {
            			w.write(String.valueOf(tile.getType()) + DEL);
            			w.write(String.valueOf(i) + DEL);
            			w.write(String.valueOf(tile.getTimeRemaining()) + DEL);
				correctPlayerFound = true;
            		}
            	}
            }
            
            /* Write the past states to file */
            ArrayList<GameState> pastStates = s.getPastStates();
            w.write(String.valueOf(pastStates.size()) + DEL);
            for(GameState pastState : pastStates) {
            	writePastState(pastState, w);
            }
            
            return;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /**
     * @private 
     * This method writes past states to file.
     * @param pastState The state to write
     * @param w The file writer
     * @throws IOException
     */
    private static void writePastState(GameState pastState, FileWriter w) throws IOException {
    	w.write(String.valueOf(pastState.getCurPlayer()) + DEL);
    	w.write(String.valueOf(pastState.getMovesLeftForCurrentPlayer()) + DEL);
    	int[][] playerPos = pastState.getPlayersPositions();
    	for(int i = 0; i < playerPos.length; i++) {
    		w.write(String.valueOf(playerPos[i][0]) + DEL);
    		w.write(String.valueOf(playerPos[i][1]) + DEL);
    	}
    	
    }
    /**
     * Writes the silk bag to file. 
     * @param bag the bag to be written
     * @param w The file writer. 
     */
    private static void writeSilkBag(SilkBag bag, FileWriter w) {
        try {
            int[] numActionTiles = bag.getNumActionTiles();
            int[] numPlaceableTiles = bag.getNumPlaceableTiles();
            /* Write action tiles */
            for (int a = 0; a < 4; a++) {
                w.write(String.valueOf(numActionTiles[a]) + DEL);
            }
            /* Write placeable tiles */
            for (int p = 0; p < 3; p++) {
                w.write(String.valueOf(numPlaceableTiles[p]) + DEL);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
