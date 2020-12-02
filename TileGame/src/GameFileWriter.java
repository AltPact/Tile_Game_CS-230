import java.io.*;
import java.util.ArrayList;
public class GameFileWriter {

    final static char DEL = ',';

	/*
	 * Does game window have a list of players.
	 */
    public static void writeGameFile(GameState g, String filename, PlayerPiece[] players ) {
        try {
            File f = new File("./data/savedgames" + filename);
            f.createNewFile();
            FileWriter w = new FileWriter(filename); 
            
            /* write game metadata */
            w.write(String.valueOf(g.isGoalHit()) + DEL);
            w.write(g.getCurPlayer() + DEL);
            w.write(g.getMovesLeftForCurrentPlayer() + DEL);
            
            w.write(players.length + DEL);
            
            
            /* write player piece data */
            for (int p = 0; p < players.length; p++) {
            	w.write(players[p].getX() + DEL);
            	w.write(players[p].getY() + DEL);
                w.write(players[p].getColour() + DEL);
                w.write(String.valueOf(players[p].getBacktrack()) + DEL);
                w.write(players[p].getLinkedData().getName() + DEL);
            }
            
            /* write current game state */
            writeCurrentGameState(g, w, players);


            /* write the silk bag */
            writeSilkBag(g.getSilkBag(), w);

            w.close();
            return;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void writeCurrentGameState(GameState s, FileWriter w, PlayerPiece[] players) {
        try {
            
            /* write tile data */
            Placeable[][] tiles = s.getBoard();
            int width = tiles.length;
    		int height = tiles[0].length;
    		
    		w.write(width + DEL);
    		w.write(height+ DEL);
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (tiles[y][x] == null) {  // is the tile full?
                        w.write("false" + DEL);
                    } else {
                        w.write("true" + DEL);
                        w.write(tiles[y][x].getType().toString() + DEL);
                        w.write(String.valueOf(tiles[y][x].isFixed()) + DEL);
                        w.write(tiles[y][x].getOrientation() + DEL);
                        w.write(String.valueOf(tiles[y][x].isOnFire()) + DEL);
                        w.write(String.valueOf(tiles[y][x].isFrozen()) + DEL);
                    }
                }
            }

            /* write action tiles in players hand */
            ArrayList<ActionTile>[] actionTilesOwnedTotal = s.getActionTilesForEachPlayer();
            for(ArrayList<ActionTile> actionTilesOwned : actionTilesOwnedTotal) {
            	w.write(actionTilesOwned.size()+ DEL);
            	for(ActionTile actionTile : actionTilesOwned) {
            		w.write(String.valueOf(actionTile.getType()) + DEL);
            		
            	}
            }
            
            /* Write the Tiles in Action */
            ArrayList<ActionTilePlaceable> tilesInAction = s.getTilesInAction();
            w.write(tilesInAction.size() + DEL);
            for(ActionTilePlaceable tile : tilesInAction) {
            	PlayerPiece owner = tile.getOwner();
            	int i = 0;
            	boolean correctPlayerFound = false;
            	while (i < players.length && !correctPlayerFound) {
            		if(owner == players[i]) {
            			w.write(String.valueOf(tile.getType()) + DEL);
            			w.write(i + DEL);
            			w.write(tile.getTimeRemaining() + DEL);
            		}
            	}
            }
            
            ArrayList<GameState> pastStates = s.getPastStates();
            w.write(pastStates.size() + DEL);
            for(GameState pastState : pastStates) {
            	writePastState(pastState, w);
            }
            
            return;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private static void writePastState(GameState pastState, FileWriter w) throws IOException {
    	w.write(pastState.getCurPlayer() + DEL);
    	w.write(pastState.getMovesLeftForCurrentPlayer() + DEL);
    	int[][] playerPos = pastState.getPlayersPositions();
    	for(int i = 0; i < playerPos.length; i++) {
    		w.write(playerPos[i][0] + DEL);
    		w.write(playerPos[i][1] + DEL);
    	}
    	
    }

    private static void writeSilkBag(SilkBag bag, FileWriter w) {
        try {
            int[] numActionTiles = bag.getNumActionTiles();
            int[] numPlaceableTiles = bag.getNumPlaceableTiles();

            for (int a = 0; a < 4; a++) {
                w.write(numActionTiles[a] + DEL);
            }
            for (int p = 0; p < 3; p++) {
                w.write(numPlaceableTiles[p] + DEL);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
