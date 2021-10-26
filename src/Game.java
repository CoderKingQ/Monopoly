import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private boolean gameNotOver;
    private Dice die;
    private Player winner;

    /**
     *
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int NOPlayers;
        int minPlayers = 2;
        int maxPlayers = 8;
        System.out.println("Hello, enter number of players:" );
        NOPlayers = scanner.nextInt();
        while (NOPlayers < 2 || NOPlayers > 8 ){
            System.out.println("Number of player must be between 2 and 8 players");
            System.out.println("Enter number of players:" );
            NOPlayers = scanner.nextInt();
        }
        Game g = new Game(NOPlayers);
        g.generateBoard();
        g.play();
    }

    /** Game's constructor creating a new game
     *
     * @param NOPlayers the number of players playing
     */
    public Game (int NOPlayers){
        this.die = new Dice();
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
                die.resetDoubles();

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
                                payRailroadTax(player);

                            } // you land on someones property
                            if (board.get(player.getLocation()) instanceof Property){

                                payPropertyTax(player);

                            } // you land on someones utility
                            if((board.get(player.getLocation()) instanceof Utilities)){
                                payUtilityTax(player);
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
                    } else {
                        player.setTurn(false);
                    }

                }
            }


            int x = 0; // check if game over by number of still playing players
            for(Player player: players){

                if(player.isPlaying() == true){
                    winner = player;
                    x++;
                }
            }
            if(x <= 1){

                gameNotOver = false;
            } // end of check



        }

        System.out.println("Congratulations " + winner.getName() + " you won the game of monopoly");
    }

    /**
     * payRailroadTax Pays owner of Railroad from the current player and sets new values for money.
     * @return void
     * @param player
     */
    private void payRailroadTax(Player player) {
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
    }

    /**
     * payUtilityTax Pays owner of Utility from the current player and sets new values for money.
     * @return void
     * @param player
     */
    private void payUtilityTax(Player player) {
        int cost = ((Utilities) board.get(player.getLocation())).getRent(die);
        player.setMoney(player.getMoney() - cost);
        board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + cost);
        System.out.println("You now have: " + player.getMoney());
        System.out.println(board.get(player.getLocation()).getOwner().getName() + " You now have: " + board.get(player.getLocation()).getOwner().getMoney());

    }

    /**
     * payPropertyTax Pays owner of property from the current player and sets new values for money.
     * @return void
     * @param player
     */
    private void payPropertyTax(Player player) {
        player.setMoney(player.getMoney() - ((Property) board.get(player.getLocation())).getRent());
        board.get(player.getLocation()).getOwner().setMoney(board.get(player.getLocation()).getOwner().getMoney() + ((Property) board.get(player.getLocation())).getRent());
        System.out.println("You now have: " + player.getMoney());
        System.out.println(board.get(player.getLocation()).getOwner().getName() + " You now have: " + board.get(player.getLocation()).getOwner().getMoney());
    }


    /**
     * playerBuyRailroad buys a Railroad for the current player and sets new values for money and ownership.
     * @return void
     * @param player
     */
    private void playerBuyRailroad(Player player) {
        player.setMoney(player.getMoney() - ((Railroad) board.get(player.getLocation())).getCost());
        System.out.println("You now have: " + player.getMoney());
        board.get(player.getLocation()).setOwner(player);
        player.addProperty(board.get(player.getLocation()));
    }

    /**
     * playerBuyUtilities buys a utility for the current player and sets new values for money and ownership.
     * @return void
     * @param player
     */
    private void playerBuyUtilities(Player player) {
        player.setMoney(player.getMoney() - ((Utilities) board.get(player.getLocation())).getCost());
        System.out.println("You now have: " + player.getMoney());
        board.get(player.getLocation()).setOwner(player);
        player.addProperty(board.get(player.getLocation()));
    }

    /**
     * playerBuyProperty buys a property for the current player and sets new values for money and ownership.
     * @return void
     * @param player
     */
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
            System.out.println("Player: " + player.getName() + " has: \n$" + player.getMoney() + "\nAnd the following properties: \n" );
            if(player.getProperties().isEmpty()){ // check if player has no properties
                System.out.println("No Properties found");
            }else for(Space property: player.getProperties()){ // go through all players properties
                if(property.getClass().equals(Property.class)){ // print set properties
                    System.out.println(property.getName() + " with "+ property.getHouses() + " houses");
                } else { // print utillities and railroads
                    System.out.println(property.getName());
                }

            }
            System.out.println("The Player is currently on " + board.get(player.getLocation()).getName() +"\n--End of Player "+ player.getName() + "--");
        }
        System.out.println("----End of Board Status----");
    }
}
