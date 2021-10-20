import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Space> board;
    private ArrayList<Player> players;

    public void generateBoard(){
        board = new BoardGenerator().generate();
    }

    public static void main(String[] args) {
        Game g = new Game(4);
        Dice d = new Dice();
        g.generateBoard();
        g.printBoard();
        g.printBoardStatus();

        /*
        for(int i = 0; i < 10; i++){
            d.roll();
            System.out.println(d.getCurrentRoll());
        }

         */
    }

    public Game (int NOPlayers){
        this.players = new ArrayList<Player>();
        for(int i = 0; i < NOPlayers; i++){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Player " + (i +1) + "'s name: " );
            String pName = scanner.nextLine();
            players.add(new Player(pName));
        }
    }

    public void printBoard(){
        for(Space space: board){
            System.out.println(space.getName() + " " + space.getLocation() + "\n");
        }
    }

    public void printBoardStatus(){
        System.out.println("----Board Status----");
        for (Player player : players){
            System.out.println("Player: " + player.getName() + " has: \n$" + player.getMoney() + "\n And the following properties: \n" );
            if(player.getProperties().isEmpty()){
                System.out.println("No Properties found");
            }else for(Space property: player.getProperties()){
                if(property.getClass().equals(Property.class)){
                    System.out.println(property.getName() + "with "+ property.getHouses() + " houses\n");
                }

            }
            System.out.println("--End of Player: "+ player.getName() + "--");
        }
        System.out.println("----End of Board Status----");
    }
}
