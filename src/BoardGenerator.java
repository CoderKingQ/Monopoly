//test
import java.util.ArrayList;

public class BoardGenerator {

    public ArrayList<Space> generate(){
        ArrayList<Space> board = new ArrayList<Space>();
        board.add(new Event(0, "GO", 0));
        board.add(new Property(1,"Mediterranean Avenue", "Brown", 60, 2));
        board.add(new Event(2, "Community Chest", 0));
        board.add(new Property(3,"Baltic Avenue", "Brown", 60, 4));
        board.add(new Event(4, "Income tax", 200));
        board.add(new Railroad(5,"Reading Railroad"));
        board.add(new Property(6,"Oriental Avenue", "Light Blue", 100, 6));
        board.add(new Event(7, "Chance", 0));
        board.add(new Property(8,"Vermont Avenue", "Light Blue", 100, 6));
        board.add(new Property(9,"Connecticut Avenue", "Light Blue", 120, 8));

        board.add(new Event(10, "Jail", 0));
        board.add(new Property(11,"St. Charles Place", "Pink", 140, 10));
        board.add(new Utilities(12,"Electric Company"));
        board.add(new Property(13,"States Avenue", "Pink", 140, 10));
        board.add(new Property(14,"Virginia Avenue", "Pink", 160, 12));
        board.add(new Railroad(15,"Pennsylvania Railroad"));
        board.add(new Property(16,"St. James Place", "Orange", 180, 14));
        board.add(new Event(17, "Community Chest", 0));
        board.add(new Property(18,"Tennessee Avenue", "Orange", 180, 14));
        board.add(new Property(19,"New York Avenue", "Orange", 200, 16));

        board.add(new Event(20, "Free Parking", 0));
        board.add(new Property(21,"Kentucky Avenue", "Red", 220, 18));
        board.add(new Event(22, "Chance", 0));
        board.add(new Property(23,"Indiana Avenue", "Red", 220, 18));
        board.add(new Property(24,"Illinois Avenue", "Red", 240, 20));
        board.add(new Railroad(25,"B. & O. Railroad"));
        board.add(new Property(26,"Atlantic Avenue", "Yellow", 260, 22));
        board.add(new Property(27,"Ventnor Avenue", "Yellow", 260, 22));
        board.add(new Utilities(28,"Electric Company"));
        board.add(new Property(29,"Marvin Gardens", "Yellow", 280, 24));

        board.add(new Event(30, "Go To Jail", 0));
        board.add(new Property(31,"Pacific Avenue", "Green", 300, 26));
        board.add(new Property(32,"North Carolina Avenue", "Green", 300, 26));
        board.add(new Event(33, "Community Chest", 0));
        board.add(new Property(34,"Pennsylvania Avenue", "Green", 320, 28));
        board.add(new Railroad(35,"Short Line"));
        board.add(new Event(36, "Chance", 0));
        board.add(new Property(37,"Park Place", "Dark Blue", 350, 35));
        board.add(new Event(38, "Luxury Tax", 100));
        board.add(new Property(39,"Boardwalk", "Dark Blue", 400, 50));

        return board;
    }
}
