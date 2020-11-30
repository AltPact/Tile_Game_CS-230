import java.io.*;
import java.util.ArrayList;
public class GameFileWriter {
	
	/*
	 * Does game window have a list of players.
	 */
    public static void writeGameFile(GameState g, String filename, PlayerPiece[] players ) {
        try {
            File f = new File(filename);
            f.createNewFile();
            FileWriter w = new FileWriter(filename);

            /* write game metadata */
            int height = g.getHeight();
            int width = g.getWidth();

            w.write(height);
            w.write(width);
            w.write(players.length);
            w.write(g.getCurPlayer());
            w.write(g.getMovesLeftForCurrentPlayer());
            w.write(String.valueOf(g.isGoalHit()));

            /* write current game state */
            writeCurrentGameState(g, w, width, height);

            /* write past game states */
            ArrayList<GameState> pastGameStates = g.getPastStates();
            for (int stateNum = 0; stateNum < pastGameStates.size(); stateNum++) {
                writePastGameState(pastGameStates.get(stateNum), w);
            }

            /* write the silk bag */
            writeSilkBag(g.getSilkBag(), w);
            return;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    
    private static void writePastGameState(GameState s, FileWriter w) {
    	try {
    		/* write player positions */
    		int[][] positions = s.getPlayersPositions();
        	for (int p = 0; p < positions[0].length; p++) {
            	w.write(positions[p][1]);
				w.write(positions[p][0]);
        	}
        	w.write("ENDPLAYERPOS"); 
		} catch (IOException e) {
			System.out.println("An Error Occured");
			e.printStackTrace();
		}

    }

    private static void writeCurrentGameState(GameState s, FileWriter w, int width, int height) {
        try {
            /* write state metadata */
            w.write(String.valueOf(s.isGoalHit()));
            w.write(s.getCurPlayer());

            /* write tile state data */
            Placeable[][] tiles = s.getBoard();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (tiles[y][x] == null) {  // is the tile full?
                        w.write("false");
                    } else {
                        w.write("true");
                        w.write(tiles[y][x].getType().toString());
                        w.write(String.valueOf(tiles[y][x].isFixed()));
                        w.write(tiles[y][x].getOrientation());
                        w.write(String.valueOf(tiles[y][x].isOnFire()));
                        w.write(String.valueOf(tiles[y][x].isFrozen()));
                    }
                }
            }

            /* write player positions */
            int[][] positions = s.getPlayersPositions();
            for (int p = 0; p < positions[0].length; p++) {
                w.write(positions[p][0]);
                w.write(positions[p][1]);
            }
            w.write("ENDPLAYERPOS");  // marker for end of player position data

            // TODO: doesn't write action tiles in player's hands yet! see TODO in readGameState

            return;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void writeSilkBag(SilkBag bag, FileWriter w) {
        try {
            int[] numActionTiles = bag.getNumActionTiles();
            int[] numPlaceableTiles = bag.getNumPlaceableTiles();

            for (int a = 0; a < 4; a++) {
                w.write(numActionTiles[a]);
            }
            for (int p = 0; p < 3; p++) {
                w.write(numPlaceableTiles[p]);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
