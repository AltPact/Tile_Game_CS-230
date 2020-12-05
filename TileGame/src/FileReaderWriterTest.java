import java.io.File;

public class FileReaderWriterTest {
    /*public static void main(String[] args) {

        System.out.println("Writing testplayer1....");
        PlayerDataFileReader.writeFile("testplayer1", 4, 1, "testpath");
        System.out.println("Reading testplayer1....");
        File f = new File("testplayer1");
        PlayerData p = PlayerDataFileReader.readFile(f);

        System.out.printf("%s:\n %d\n %d\n %s", p.getName(), p.getWins(), p.getLosses(), p.getAvatarPath());

        Game testGame = GameFileReader.readBoardFile("/home/shimshar/atom-workspace/Tile_Game_CS-230/TileGame/boardexample1", new String[] {"testplayer1"});

        System.out.println("Done.");
    } */

    /**
     * This is a funciton to generate a hard-coded game to use for testing while we figure out our file reader.
     * TODO: DO NOT INCLUDE THIS IN FINAL SUBMISSION
     */
    public static Game generateTestGame() {
        SilkBag bag = new SilkBag(new int[] {0, 0, 0, 0}, new int[] {10, 10, 10});
        int width = 5;
        int height = 5;
        Placeable[][] tiles = new Placeable[height][width];
        tiles[0][0] = new Placeable(TileType.TShaped, false, true);
        tiles[0][1] = new Placeable(TileType.Straight, false, true);
        tiles[0][3] = new Placeable(TileType.Goal, true, true);
        Board board = new Board(width, height, tiles);
        board.fillBoard(bag);

        PlayerData data1 = new PlayerData("name1", 5, 5, "avatarpath1");
        PlayerData data2 = new PlayerData("name2", 10, 10, "avatarpath2");
        PlayerPiece piece1 = new PlayerPiece(0, 0, "Red", false, data1);
        PlayerPiece piece2 = new PlayerPiece(1, 0, "Blue", false, data2);

        return new Game(bag, new PlayerPiece[] {piece1, piece2}, board);
    }
}
