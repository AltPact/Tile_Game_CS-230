import java.io.*;
import java.util.ArrayList;
public class FileWriter {

   public static void main(String args[]) throws IOException {
      File file = new File("Hello1.txt");
      
      // creates the file
      file.createNewFile();
      
      // creates a FileWriter Object
      FileWriter writer = new FileWriter(file); 
      
    

      // Writes the content to the file
      writer.write("This\n is\n an\n example\n"); 
    writer.write(game.getBoard().getWidth());
    writer.write(game.getBoard().getHeight());
    ArrayList<Integer> fixedTileList = new ArrayList<Integer>(26);
    int fixedCounter = 0;
    for (int x = 0; x < board.getWidth; x++){
        for (int y = 0; y < board.getHeight; y++){
            if (game.getBoard().getTile(x,y).getType() == "fixed"){
                fixedTileList.add(x);
                fixedTileList.add(y);
                fixedCounter++;
            }
        }
    }
    writer.write(fixedCounter);
    for (int pie = 0; pie < fixedCounter; pie++){
        writer.write(fixedTileList[(pie*2)-1]);
        writer.write(fixedTileList[pie*2]);
        writer.write(game.getBoard().getTile(x,y).getType());
        writer.write(game.getBoard().getTile(x,y).getOrientation());
        writer.write(game.getBoard().getTile(x,y).isGoal());
    }
    writer.write(game.getSilkBag().getNumTiles());
    int tileInBag = game.getSilkBag().getNumTiles();
    for (int b; b < tileInBag; b++){
        writer.write(game.silkBag().getTileArray(b));
    }
    // TODO: Silkbag function yet to be created/commited
    ArrayList<Integer> actionTileTypes = new ArrayList<Integer>(26);
    //TODO: Change type of ArrayList
    for (int x = 0; x < board.getWidth; x++){
        for (int y = 0; y < board.getHeight; y++){
            //To-Do: make into var
            if (game.getBoard().getTile(x,y).getType().toString() == "ice" || game.getBoard().getTile(x,y).getType().toString() == "fire"){
                actionTileTypes.add(game.getBoard().getTile(x,y).getType())); 
            }
        }
    }
    writer.write(actionTileTypes.length);
    for (int n = 0; n < actionTileTypes.length; n++){
        writer.write(actionTileTypes[n];
    }


    writer.write();
      //loops through data present in the game function
      //writes in the file based on the status of the game

      writer.flush();
      writer.close();

      // Creates a FileReader Object
      FileReader fr = new FileReader(file); 
      char [] a = new char[50];
      fr.read(a);   // reads the content to the array
      
      for(char c : a)
         System.out.print(c);   // prints the characters one by one
      fr.close();
   }
}