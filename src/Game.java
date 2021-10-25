import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private boolean gameNotOver;
    private Dice die;
    private Player winner;

    public static void main(String[] args) {
        Game g = new Game(2);
        g.generateBoard();
        g.play();
    }

    /** Game's constructor creating a new game
     *
     * @param NOPlayers the number of players playing
     */
    public Game (int NOPlayers){
        this.players = new ArrayList<>();
        for(int i = 0; i < NOPlayers; i++){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Player " + (i +1) + "'s name: " );
            String pName = scanner.nextLine();
            players.add(new Player(pName));
        }
        this.gameNotOver = true;
    }

    /** play is how you play the game
     *
     */
    public void play() {// most work needs to be done here to make the game playable
        System.out.println("----Welcome All to the Game of Monopoly----");
        System.out.println("----You all start with $1500 and on GO ----");
        while (gameNotOver) {
            for (Player player : players) {
                player.setTurn(true);
                Scanner scanner = new Scanner(System.in);
                this.die = new Dice();
                while (player.isTurn() && player.isPlaying() && die.getDoubleCount() <= 2) { //check if its there turn and if they are still in the game

                    System.out.println(player.getName() + " it is your turn and are currently on " + board.get(player.getLocation()).getName() + "\nWhat do you want to do:\n roll buyHouses status");
                    String option = scanner.nextLine();
                    if (option.equals("roll")) {
                        die.roll();
                        if(die.getDoubleCount() > 2){
                            System.out.println("Oh no you rolled three doubles go to jail!");
                            player.setLocation(10); // jail location - to be implemented lock in jail for 3 turns
                            player.setTurn(false);
                            break;
                        }

                        player.setLocation(die.getCurrentRoll() + player.getLocation());

                        System.out.println("You rolled a " + die.getCurrentRoll() + " and landed on " + board.get(player.getLocation()).getName());
                        //land on a property and check if its purchasable
                        if(board.get(player.getLocation()).getOwner() == null && ((board.get(player.getLocation()) instanceof Property) ||(board.get(player.getLocation()) instanceof Railroad)||(board.get(player.getLocation()) instanceof Utilities))){
                            System.out.println("Do you want to buy or pass on this property:\n buy pass");
                            option = scanner.nextLine();
                            if(option.equals("buy") && (board.get(player.getLocation()) instanceof Property)){
                                playerBuyProperty(player);
                            }
                            else if(option.equals("buy") && (board.get(player.getLocation()) instanceof Utilities)){
                                playerBuyUtilities(player);
                            }
                            else if(option.equals("buy") && (board.get(player.getLocation()) instanceof Railroad)){
                                playerBuyRailroad(player);
                                //test
                            }
                            else{
                                player.setTurn(false);
                            }
                        } // You land on an event space
                        else if(board.get(player.getLocation()) instanceof Event){
                            player.setMoney(player.getMoney() - ((Event) board.get(player.getLocation())).getPayment());
                            System.out.println("You now have: " + player.getMoney());

                        } // you land on someone elses railroad
                        else if(board.get(player.getLocation()).getOwner() != null){
                            System.out.println("Someone already owns this");
                            if((board.get(player.getLocation()) instanceof Railroad)){
                                if(board.get(player.getLocation()).getOwner().getNoRailroads() == 1){
                                    player.setMoney(player.getMoney() - 25);
                                    board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + 25);
                                }
                                else if(board.get(player.getLocation()).getOwner().getNoRailroads() == 2){
                                    player.setMoney(player.getMoney() - 50);
                                    board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + 50);
                                }
                                else if(board.get(player.getLocation()).getOwner().getNoRailroads() == 3){
                                    player.setMoney(player.getMoney() - 100);
                                    board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + 100);
                                }
                                else{
                                    player.setMoney(player.getMoney() - 200);
                                    board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + 200);
                                }

                            } // you land on someones property
                            if (board.get(player.getLocation()) instanceof Property){

                                player.setMoney(player.getMoney() - ((Property) board.get(player.getLocation())).getRent());
                                board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + ((Property) board.get(player.getLocation())).getRent());
                                System.out.println("You now have: " + player.getMoney());
                                System.out.println(board.get(player.getLocation()).getOwner().getName() + " You now have: " + board.get(player.getLocation()).getOwner().getMoney());

                            } // you land on someones utility
                            if((board.get(player.getLocation()) instanceof Utilities)){
                                int cost = ((Utilities) board.get(player.getLocation())).getRent(die);
                                player.setMoney(player.getMoney() - cost);
                                board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + cost);
                                System.out.println("You now have: " + player.getMoney());
                                System.out.println(board.get(player.getLocation()).getOwner().getName() + " You now have: " + board.get(player.getLocation()).getOwner().getMoney());
                            }

                            if(player.getMoney() <= 0){ // bankruptcy
                                System.out.println("Oh no " + player.getName() + " you ran out of money! You lose.");
                                player.setPlaying(false);
                            }

                        }


                    }
                    else if (option.equals("pass")){ // pass player turn
                        player.setTurn(false);
                    } else if (option.equals("status")){ // prints the status of each of the players
                        this.printBoardStatus();
                    }

                    if(die.isDoubles()){ // if they get doubles make sure they go again
                        player.setTurn(true);
                    }

                }
            }

            for(Player player: players){ // check if game over by number of still playing players
                int x = 0;
                if(player.isPlaying() == true){
                    winner = player;
                    x ++;
                }
                if(x <= 1){

                    gameNotOver = false;
                }
            }

        }

        System.out.println("Congratulations " + winner.getName() + " you won the game of monopoly");
    }

    private void playerBuyRailroad(Player player) {
        player.setMoney(player.getMoney() - ((Railroad) board.get(player.getLocation())).getCost());
        System.out.println("You now have: " + player.getMoney());
        board.get(player.getLocation()).setOwner(player);
        player.addProperty(board.get(player.getLocation()));
    }

    private void playerBuyUtilities(Player player) {
        player.setMoney(player.getMoney() - ((Utilities) board.get(player.getLocation())).getCost());
        System.out.println("You now have: " + player.getMoney());
        board.get(player.getLocation()).setOwner(player);
        player.addProperty(board.get(player.getLocation()));
    }

    private void playerBuyProperty(Player player) {
        player.setMoney(player.getMoney() - ((Property) board.get(player.getLocation())).getCost());
        System.out.println("You now have: " + player.getMoney());
        board.get(player.getLocation()).setOwner(player);
        player.addProperty(board.get(player.getLocation()));
    }

    /** generateBoard creates a new board using the BoardGenerator class to generate a new board
     *
     */
    public void generateBoard(){
        board = new BoardGenerator().generate();
    }

    /** printBoard prints the entire current board of monopoly

     */
    public void printBoard(){
        for(Space space: board){
            System.out.println(space.getName() + " " + space.getLocation() + "\n");
        }
    }

    /** printBoardStatus prints the status of all players, what they own, how much money they have and where they are

     */
    public void printBoardStatus(){
        System.out.println("----Board Status----");
        for (Player player : players){
            System.out.println("Player: " + player.getName() + " has: \n$" + player.getMoney() + "\n And the following properties: \n" );
            if(player.getProperties().isEmpty()){ // check if player has no properties
                System.out.println("No Properties found");
            }else for(Space property: player.getProperties()){ // go through all players properties
                if(property.getClass().equals(Property.class)){ // print set properties
                    System.out.println(property.getName() + "with "+ property.getHouses() + " houses\n");
                } else { // print utillities and railroads
                    System.out.println(property.getName());
                }

            }
            System.out.println("The Player is currently on " + board.get(player.getLocation()).getName() +"\n--End of Player "+ player.getName() + "--");
        }
        System.out.println("----End of Board Status----");
    }
}
