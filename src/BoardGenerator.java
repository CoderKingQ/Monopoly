//test
import java.util.ArrayList;

/**
 * Class BoardGenerator represents/creates the monopoly board
 */
public class BoardGenerator {

    /** The board generator generates the board for the game
     *
     * @return Arraylist<Space>, board
     */
    public ArrayList<Space> generate(){ // creates the entire board of monopoly
        ArrayList<Space> board = new ArrayList<Space>();
        board.add(new Event(0, "GO", 0));
        board.add(new Property(1,"Mediterranean Avenue", "#964B00", 60, 2));
        board.add(new Event(2, "Community Chest", 0));
        board.add(new Property(3,"Baltic Avenue", "#964B00", 60, 4));
        board.add(new Event(4, "Income tax", 200));
        board.add(new Railroad(5,"Reading Railroad"));
        board.add(new Property(6,"Oriental Avenue", "#add8e6", 100, 6));
        board.add(new Event(7, "Chance", 0));
        board.add(new Property(8,"Vermont Avenue", "#add8e6", 100, 6));
        board.add(new Property(9,"Connecticut Avenue", "#add8e6", 120, 8));

        board.add(new Event(10, "Jail", 0));
        board.add(new Property(11,"St. Charles Place", "#E36B89", 140, 10));
        board.add(new Utilities(12,"Electric Company"));
        board.add(new Property(13,"States Avenue", "#E36B89", 140, 10));
        board.add(new Property(14,"Virginia Avenue", "#E36B89", 160, 12));
        board.add(new Railroad(15,"Pennsylvania Railroad"));
        board.add(new Property(16,"St. James Place", "#FFA500", 180, 14));
        board.add(new Event(17, "Community Chest", 0));
        board.add(new Property(18,"Tennessee Avenue", "#FFA500", 180, 14));
        board.add(new Property(19,"New York Avenue", "#FFA500", 200, 16));

        board.add(new Event(20, "Free Parking", 0));
        board.add(new Property(21,"Kentucky Avenue", "#FF0000", 220, 18));
        board.add(new Event(22, "Chance", 0));
        board.add(new Property(23,"Indiana Avenue", "#FF0000", 220, 18));
        board.add(new Property(24,"Illinois Avenue", "#FF0000", 240, 20));
        board.add(new Railroad(25,"B. & O. Railroad"));
        board.add(new Property(26,"Atlantic Avenue", "#FFFF00", 260, 22));
        board.add(new Property(27,"Ventnor Avenue", "#FFFF00", 260, 22));
        board.add(new Utilities(28,"Electric Company"));
        board.add(new Property(29,"Marvin Gardens", "#FFFF00", 280, 24));

        board.add(new Event(30, "Go To Jail", 0));
        board.add(new Property(31,"Pacific Avenue", "#228B22", 300, 26));
        board.add(new Property(32,"North Carolina Avenue", "#228B22", 300, 26));
        board.add(new Event(33, "Community Chest", 0));
        board.add(new Property(34,"Pennsylvania Avenue", "#228B22", 320, 28));
        board.add(new Railroad(35,"Short Line"));
        board.add(new Event(36, "Chance", 0));
        board.add(new Property(37,"Park Place", "#00008B", 350, 35));
        board.add(new Event(38, "Luxury Tax", 100));
        board.add(new Property(39,"Boardwalk", "#00008B", 400, 50));

        return board;
    }
}
