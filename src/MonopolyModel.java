import java.util.ArrayList;
import java.util.List;

public class MonopolyModel {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private boolean gameNotOver;
    private Dice die;
    private Player winner;

    private boolean bought;

    public int currentPlayer;

    private List<MonopolyView> views;

    public ArrayList<Space> getBoard(){
        return this.board;
    }

    /** Game's constructor creating a new game
     *
     *
     */
    public MonopolyModel (ArrayList<String> names){
        this.die = new Dice();
        this.generateBoard();
        this.players = new ArrayList<>();
        for(int i = 0; i < names.size(); i++){
            players.add(new Player(names.get(i)));
        }

        views = new ArrayList<>();
        this.gameNotOver = true;
        players.get(0).setTurn(true);
    }

    /** how a person will play their turn
     *
     */
    public void play(int player) {



    }

    /** how a person will play their turn
     *
     */
    public void roll() {
        //TODO handle doubles and pass it off to another player
        die.roll();

        //double handling
        if(die.getDoubleCount() > 2){
            players.get(currentPlayer).setLocation(10); // jail location - to be implemented lock in jail for 3 turns
            players.get(currentPlayer).setTurn(false);
        }


        if(players.get(currentPlayer).isTurn()){
            players.get(currentPlayer).setLocation(players.get(currentPlayer).getLocation() + die.getCurrentRoll());


            //check if property is purchasable and what kind it is
            bought = false;
            if(((Property) board.get(players.get(currentPlayer).getLocation())).getOwner() == null && (board.get(players.get(currentPlayer).getLocation()) instanceof Property)){
                if(views.get(0).handleBuyProperty(board.get(players.get(currentPlayer).getLocation()))){ // if they say yes to buying the property
                    if(((Property) board.get(players.get(currentPlayer).getLocation())).getCost() <= players.get(currentPlayer).getMoney()) {
                        buyProperty();
                    }
                }
            } else if(((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner() == null && (board.get(players.get(currentPlayer).getLocation()) instanceof Railroad)){
                //TODO
            } else if(((Utilities) board.get(players.get(currentPlayer).getLocation())).getOwner() == null && (board.get(players.get(currentPlayer).getLocation()) instanceof Utilities)){
                //TODO
            }

            if(bought){ // && player has enough money
                buyProperty();
                players.get(currentPlayer).addProperty(board.get(players.get(currentPlayer).getLocation()));
                //printBoardStatus();

            }

        }else if(players.get(currentPlayer).isTurn() == false) {
            nextTurn();
            die.resetDoubles();
        }
    }

    /**
     *
     */
    private void buyProperty() {
            //set the properties owner
            players.get(currentPlayer).setMoney(players.get(currentPlayer).getMoney() - ((Property) board.get(players.get(currentPlayer).getLocation())).getCost());
            ((Property) board.get(players.get(currentPlayer).getLocation())).setOwner(players.get(currentPlayer));
            players.get(currentPlayer).addProperty(board.get(players.get(currentPlayer).getLocation()));
    }

    /** combine all player info into a string to send to frame
     *
     * @return
     */
    public String status(){
        //TODO
        String allinfo = "";

        return allinfo;
    }

    /**
     *
     */
    public void payPlayer(){
        //TODO
    }

    /**
     *
     */
    public void payEvent(){

    }

    private void nextTurn() {
        int i = 0;
        for(Player player : players){
            if(players.equals(currentPlayer)){
                break;
            } else i++;

        }

        //setting the current player
        if(i + 1 < players.size()){
            currentPlayer = i;
        } else {
            currentPlayer = 0;
        }
    }

    public void addMonopolyView(MonopolyView view){
        views.add(view);
    }

    public void removeMonopolyView(MonopolyView view){
        views.remove(view);
    }

    public void generateBoard(){
        board = new BoardGenerator().generate();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isBought() {
        return bought;
    }

    /** remove me after how we used to print status


    public void printBoardStatus(){
        System.out.println("----Board Status----");
        for (Player player : players){
            System.out.println("Player: " + player.getName() + " has: \n$" + player.getMoney() + "\nAnd the following properties: \n" );
            if(player.getProperties().isEmpty()){ // check if player has no properties
                System.out.println("No Properties found");
            }else for(Space property: player.getProperties()){ // go through all players properties
                if(property.getClass().equals(Property.class)){ // print set properties
                    System.out.println(property.getName() + " with "+ ((Property) property.getHouses()) + " houses");
                } else { // print utillities and railroads
                    System.out.println(property.getName());
                }

            }
            System.out.println("The Player is currently on " + board.get(player.getLocation()).getName() +"\n--End of Player "+ player.getName() + "--");
        }
        System.out.println("----End of Board Status----");

    }
     */
}