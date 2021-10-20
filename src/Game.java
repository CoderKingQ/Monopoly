import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private boolean gameNotOver;
    private Dice die;

    public static void main(String[] args) {
        Game g = new Game(2);
        g.generateBoard();
        g.printBoard();
        g.printBoardStatus();

        /*
        for(int i = 0; i < 10; i++){
            d.roll();
            System.out.println(d.getCurrentRoll());
        }

         */
        g.play();
    }

    public Game (int NOPlayers){
        this.players = new ArrayList<Player>();
        for(int i = 0; i < NOPlayers; i++){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Player " + (i +1) + "'s name: " );
            String pName = scanner.nextLine();
            players.add(new Player(pName));
        }
        this.gameNotOver = true;
        this.die = new Dice();
    }

    public void play(){
        System.out.println("----Welcome All to the Game of Monopoly----");
        System.out.println("----You all start with $1500 and on GO ----");
        while(gameNotOver){
            for(Player player: players){
                player.setTurn(true);
                boolean turnStart = true;
                Scanner scanner = new Scanner(System.in);
                while(player.isTurn()) {
                    if (turnStart) {// a players first turn
                        System.out.println(player.getName() + " it is your turn and are currently on " + board.get(player.getLocation()).getName() + "\nWhat do you want to do:\n roll buyHouses pass Command");
                        String option = scanner.nextLine();
                        if (option.equals("roll")){
                            die.roll();
                            player.setLocation(die.getCurrentRoll() + player.getLocation());
                            System.out.println("You rolled a " + die.getCurrentRoll() + " and landed on " + board.get(player.getLocation()).getName());

                            if(die.isDoubles() != true){
                                player.setTurn(false);
                            }
                        } else if (option.equals("pass")){
                            player.setTurn(false);
                        }
                    } else { // a players additional turn if they roll a double
                        System.out.println(player.getName() + " it is your turn what do you want to do:\n roll buyHouses Command");
                        String option = scanner.nextLine();
                    }
                }
            }
        }
    }

    public void generateBoard(){
        board = new BoardGenerator().generate();
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
            System.out.println("The Player is currently on " + board.get(player.getLocation()).getName() +"\n--End of Player "+ player.getName() + "--");
        }
        System.out.println("----End of Board Status----");
    }
}
