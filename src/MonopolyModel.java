import java.util.ArrayList;
import java.util.List;

public class MonopolyModel {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private boolean gameNotOver;
    private Dice die;
    private Player winner;
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
        if (die.getDoubleCount() > 2) {
           // players.get(currentPlayer).setLocation(10); // jail location - to be implemented lock in jail for 3 turns
            players.get(currentPlayer).setTurn(false);
        }


        if (players.get(currentPlayer).isTurn()) {
            players.get(currentPlayer).setLocation(players.get(currentPlayer).getLocation() + die.getCurrentRoll());


                //check if property is purchasable and what kind it is
                if((board.get(players.get(currentPlayer).getLocation()) instanceof Property)){
                    if (((Property) board.get(players.get(currentPlayer).getLocation())).getOwner() == null) {
                        if (views.get(0).handleBuyProperty(board.get(players.get(currentPlayer).getLocation()))) { // if they say yes to buying the property
                            if (((Property) board.get(players.get(currentPlayer).getLocation())).getCost() <= players.get(currentPlayer).getMoney()) {
                                buyProperty();
                            }
                        }
                    } else {
                        payPropertyRent();
                    }
                }
                if((board.get(players.get(currentPlayer).getLocation()) instanceof Railroad)){
                    if(((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner() == null) {
                        if (views.get(0).handleBuyProperty(board.get(players.get(currentPlayer).getLocation()))) {
                            if (((Railroad) board.get(players.get(currentPlayer).getLocation())).getCost() <= players.get(currentPlayer).getMoney()) {
                                buyRailroad();
                            }
                        }
                    } else {
                        payRailroadRent();
                    }
                }
                if((board.get(players.get(currentPlayer).getLocation()) instanceof Utilities)){
                    if (((Utilities) board.get(players.get(currentPlayer).getLocation())).getOwner() == null) {
                        if (views.get(0).handleBuyProperty(board.get(players.get(currentPlayer).getLocation()))) {
                            if (((Utilities) board.get(players.get(currentPlayer).getLocation())).getCost() <= players.get(currentPlayer).getMoney()) {
                                buyUtilities();
                            }
                        }
                    } else {
                        payUtilitiesRent(die);
                    }
                }

                //check if player is on event space
            if((board.get(players.get(currentPlayer).getLocation()).getName().equals("Luxury Tax")) || (board.get(players.get(currentPlayer).getLocation()).getName().equals("Income tax"))){
                payEvent();
                views.get(0).handlePayEvent(board.get(players.get(currentPlayer).getLocation()));
            }



        }
        if(die.isDoubles()){
            System.out.println("got doubles");
            players.get(currentPlayer).setTurn(true);
        }else{players.get(currentPlayer).setTurn(false);}

        if (players.get(currentPlayer).isTurn() == false) {
            nextTurn();
            die.resetDoubles();
        }
    }

    /** combine all player info into a string to send to frame
     *
     * @return
     */
    public void status(){
        StringBuilder sb = new StringBuilder("");
        for (Player player : players){
            sb.append("Player: " + player.getName() + "has: \n$" + player.getMoney() + " \nAnd the following properties: \n");
            for (Space property : player.getProperties()){
                sb.append(property.getName() + "\n");
            }
            sb.append("\n");
        }

        views.get(0).handleStatus(sb);


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

    /**
     *
     */
    public void payPlayer(){
        if(((Property) board.get(players.get(currentPlayer).getLocation())).getOwner() != null && !(players.get(currentPlayer).equals( ((Property) board.get(players.get(currentPlayer).getLocation())).getOwner()))){
            payPropertyRent();
        }

        if(((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner() != null && !(players.get(currentPlayer).equals(((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner()))){
            payRailroadRent();
        }

        if(((Utilities) board.get(players.get(currentPlayer).getLocation())).getOwner() != null && !(players.get(currentPlayer) .equals(((Utilities) board.get(players.get(currentPlayer).getLocation())).getOwner()))){
            payUtilitiesRent(die);
        }
    }

    public void payPropertyRent() {
        int rent = ((Property) board.get(this.getPlayer().getLocation())).getRent();
        //add money to landlord
        ((Property) board.get(this.getPlayer().getLocation())).getOwner().addMoney(rent);
        //remove rent from current player
        this.getPlayer().removeMoney(rent);
    }
    public void payRailroadRent(){
        //NoOfRailroads = 1,2,3,4
        if(((Railroad) board.get(this.getPlayer().getLocation())).getOwner().getNoRailroads() == 1) {
            //pay Railroad rent to landlord
            ((Railroad) board.get(this.getPlayer().getLocation())).getOwner().addMoney(25);
            //remove rent from current player
            this.getPlayer().removeMoney(25);
        } else if(((Railroad) board.get(this.getPlayer().getLocation())).getOwner().getNoRailroads() == 2){
            //pay rent to landlord
            ((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner().addMoney(50);
            //remove rent from current player
            this.getPlayer().removeMoney(50);

        } else if(((Railroad) board.get(this.getPlayer().getLocation())).getOwner().getNoRailroads() == 3){
            //pay rent to landlord
            ((Railroad) board.get(this.getPlayer().getLocation())).getOwner().addMoney(100);
            //remove rent from current player
            this.getPlayer().removeMoney(100);

        }else{
            //pay rent to landlord
            ((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner().setMoney(((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner().getMoney() + 200);
            //remove rent from current player
            this.getPlayer().removeMoney(200);

        }
    }
    public void payUtilitiesRent(Dice d){
        //cost of rent
       int rent = ((Utilities) board.get(this.getPlayer().getLocation())).getRent(d);
       //((Utilities) board.get(model.getPlayers().get(0).getLocation())).getRent(die);
        //add money to landlord
        ((Utilities) board.get(this.getPlayer().getLocation())).getOwner().addMoney(rent);
        //remove rent from current player
        this.getPlayer().removeMoney(rent);
    }

    /**
     *
     */
    public void payEvent(){
        this.getPlayer().removeMoney(((Event) board.get(players.get(currentPlayer).getLocation())).getPayment());
    }

    private void nextTurn() {
        int i = 0;
        for(Player player : players){
            if(player.getName().equals(players.get(currentPlayer).getName())){
                break;
            } else i++;

        }

        //setting the current player
        if(i + 1 < players.size()){
            currentPlayer = i + 1;
            players.get(currentPlayer).setTurn(true);

        } else {
            currentPlayer = 0;
            players.get(currentPlayer).setTurn(true);
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




    public void buyUtilities(){
        this.getPlayer().removeMoney(((Utilities) board.get(this.getPlayer().getLocation())).getCost()); //update money
        ((Utilities) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer()); //update owner
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation())); //add property to player

    }
    public void buyRailroad() {
        this.getPlayer().removeMoney(((Railroad) board.get(this.getPlayer().getLocation())).getCost()); //update money
        ((Railroad) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer()); //update owner
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation())); //add property to player

    }


    public void buyProperty(){
        //set the properties owner
        this.getPlayer().removeMoney(((Property) board.get(this.getPlayer().getLocation())).getCost());
        ((Property) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer());
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation()));
    }
    public Player getPlayer(){
        return players.get(currentPlayer);
    }
}