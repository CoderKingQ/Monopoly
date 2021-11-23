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
    private int numOfAi;
    private Jail jail;

    /** Game's constructor creating a new game
     *
     *
     */
    public MonopolyModel(ArrayList<String> names, ArrayList<Integer> aiNumber){
        this.die = new Dice();
        this.generateBoard();
        this.players = new ArrayList<>();
        for(int i = 0; i < names.size(); i++){
            players.add(new Player(names.get(i), aiNumber.get(i)));
        }

        views = new ArrayList<>();
        this.gameNotOver = true;
        players.get(0).setTurn(true);
    }

    /** roll handles a player rolling and their movement
     *
     */
    public void roll() {
        die.roll();

        //double handling
        if (die.getDoubleCount()> 2){
            players.get(currentPlayer).setLocation(10);
            players.get(currentPlayer).setTurn(false);
        }
        /*
        if (die.getDoubleCount() > 2) {
            players.get(currentPlayer);
            for(MonopolyView views : views){
                views.handleJailEvent(getPlayers().get(currentPlayer), getPlayer().getLocation());
            }
            players.get(currentPlayer).setLocation(10);
            //players.get(currentPlayer).setTurn(false);
            nextTurn();
        }

         */


        if (players.get(currentPlayer).isTurn()) {
            players.get(currentPlayer).setLocation(players.get(currentPlayer).getLocation() + die.getCurrentRoll());

            views.get(0).handleDisplayChar(currentPlayer, players.get(currentPlayer).getLocation(), players.get(currentPlayer).getLocationGUI(players.get(currentPlayer).getLocation()));
            views.get(0).handleDisplay();

            if ((board.get(players.get(currentPlayer).getLocation()) instanceof Event)){


            }
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
                    views.get(0).handlePayPlayer(players.get(currentPlayer) , (((Property) board.get(players.get(currentPlayer).getLocation())).getOwner()) , (((Property) board.get(players.get(currentPlayer).getLocation())).getRent()));
                    checkBankrupt();
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
                    views.get(0).handlePayPlayer(players.get(currentPlayer) , (((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner()) , (((Railroad) board.get(players.get(currentPlayer).getLocation())).getRent()));
                    checkBankrupt();
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
                    views.get(0).handlePayPlayer(players.get(currentPlayer) , (((Utilities) board.get(players.get(currentPlayer).getLocation())).getOwner()) , (((Utilities) board.get(players.get(currentPlayer).getLocation())).getRent(die)));
                    checkBankrupt();
                }
            }

            //check if player is on event space
            if ((board.get(players.get(currentPlayer).getLocation()).getName().equals("Go To Jail"))){
                //send player to jail and skip turn
                players.get(currentPlayer).setLocation(10);
                players.get(currentPlayer).setTurn(false);
                nextTurn();

            }
            else if ((board.get(players.get(currentPlayer).getLocation()).getName().equals("Luxury Tax")) || (board.get(players.get(currentPlayer).getLocation()).getName().equals("Income tax"))){
                payEvent();
                views.get(0).handlePayEvent(board.get(players.get(currentPlayer).getLocation()));
                checkBankrupt();
            }


        }
        if(die.isDoubles()){
            System.out.println("got doubles");
            players.get(currentPlayer).setTurn(true);
            //set ai turn to true
            if(getPlayer().isAi()){
                makeDecision();
            }
        }else{players.get(currentPlayer).setTurn(false);}

        if (players.get(currentPlayer).isTurn() == false) {
            nextTurn();
            die.resetDoubles();
        }
    }

    private void handleAiRoll() {
        die.roll();

        //double handling
        if (die.getDoubleCount() > 2) {
            getPlayer().setTurn(false);
        }
        if (players.get(currentPlayer).isTurn()) {
            getPlayer().setLocation(getPlayer().getLocation() + die.getCurrentRoll());

            views.get(0).handleDisplayChar(currentPlayer, getPlayer().getLocation(), getPlayer().getLocationGUI(getPlayer().getLocation()));
            views.get(0).handleDisplay();

            //check if property is purchasable and what kind it is
            if((board.get(getPlayer().getLocation()) instanceof Property)){
                if (((Property) board.get(getPlayer().getLocation())).getOwner() == null) {
                    // if they afford property
                        if (((Property) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney() && getPlayer().getMoney()>=300) {
                            buyProperty();
                        }

                } else {
                    payPropertyRent();
                    views.get(0).handlePayPlayer(getPlayer() , (((Property) board.get(getPlayer().getLocation())).getOwner()) , (((Property) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if((board.get(getPlayer().getLocation()) instanceof Railroad)){
                if(((Railroad) board.get(getPlayer().getLocation())).getOwner() == null) {

                        if (((Railroad) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney() && getPlayer().getMoney() >= 300) {
                            buyRailroad();
                        }

                } else {
                    payRailroadRent();
                    views.get(0).handlePayPlayer(getPlayer() , (((Railroad) board.get(getPlayer().getLocation())).getOwner()) , (((Railroad) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if((board.get(getPlayer().getLocation()) instanceof Utilities)){
                if (((Utilities) board.get(getPlayer().getLocation())).getOwner() == null) {

                        if (((Utilities) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney() && getPlayer().getMoney() >= 300) {
                            buyUtilities();
                        }

                } else {
                    payUtilitiesRent(die);
                    views.get(0).handlePayPlayer(getPlayer() , (((Utilities) board.get(getPlayer().getLocation())).getOwner()) , (((Utilities) board.get(getPlayer().getLocation())).getRent(die)));
                    checkBankrupt();
                }
            }

            //check if player is on event space
            if((board.get(getPlayer().getLocation()).getName().equals("Luxury Tax")) || (board.get(getPlayer().getLocation()).getName().equals("Income tax"))){
                payEvent();
                views.get(0).handlePayEvent(board.get(getPlayer().getLocation()));
                checkBankrupt();
            }
            if(die.isDoubles()){
                System.out.println("robot got doubles");
                getPlayer().setTurn(true);
                //set ai turn to true

                makeDecision();

            }else{getPlayer().setTurn(false);}

            if (getPlayer().isTurn() == false) {
                nextTurn();
                die.resetDoubles();
            }
        }
    }

    /** combine all player info into a string to send to frame
     *
     * @return String, all the info
     */
    public void status(){
        StringBuilder sb = new StringBuilder("");
        for (Player player : players){
            sb.append("Player: " + player.getName() + " has: \n$" + player.getMoney() + " \nAnd the following properties: \n");
            for (Space property : player.getProperties()){
                if(property instanceof Property) {
                    sb.append(property.getName() + " with " + ((Property) property).getHouses() + " houses \n");
                } else sb.append(property.getName() +  "\n");
            }
            sb.append("\n");
        }

        views.get(0).handleStatus(sb);


    }

    /** checkBankrupt checks if the current player has gone bankrupt
     *
     */
    public void checkBankrupt(){
        if(getPlayer().getMoney() <= 0 ){
            players.get(currentPlayer).setPlaying(false);
        }
    }


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

    /** payProperty rent handles the property payment between the owner and renter
     *
     */
    public void payPropertyRent() {
        int rent = ((Property) board.get(this.getPlayer().getLocation())).getRent();
        //add money to landlord
        ((Property) board.get(this.getPlayer().getLocation())).getOwner().addMoney(rent);
        //remove rent from current player
        this.getPlayer().removeMoney(rent);
    }

    /** payProperty rent handles the railroad rental payment between the owner and renter
     *
     */
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

    /** payProperty rent handles the Utilities' payment between the owner and renter
     *
     */
    public void payUtilitiesRent(Dice d){
        //cost of rent
       int rent = ((Utilities) board.get(this.getPlayer().getLocation())).getRent(d);
       //((Utilities) board.get(model.getPlayers().get(0).getLocation())).getRent(die);
        //add money to landlord
        ((Utilities) board.get(this.getPlayer().getLocation())).getOwner().addMoney(rent);
        //remove rent from current player
        this.getPlayer().removeMoney(rent);
    }

    /** payEvent handles the player paying the amount due on a property
     *
     */
    public void payEvent(){
        this.getPlayer().removeMoney(((Event) board.get(players.get(currentPlayer).getLocation())).getPayment());
    }

    public void payJailFine(){
        this.getPlayer().removeMoney(jail.getFine());
    }

    /** nextTurn passes the turn off to the next player
     *
     */
    public void nextTurn() {
        for(Player player : players){
            if(!getPlayer().isPlaying()){
                views.get(0).declareBankruptPlayer();
                players.remove(players.get(currentPlayer));
            }
        }

        if(players.size() != 1) {
            int i = 0;
            for (Player player : players) {
                if (player.getName().equals(getPlayer().getName())) {
                    break;
                } else i++;

            }

            //setting the current player
            if (i + 1 < players.size()) {
                currentPlayer = i + 1;
                if (getPlayer().isPlaying()) {
                    getPlayer().setTurn(true);
                    //check if player is AI and start decision logic
                    if(getPlayer().isAi()) {
                        makeDecision();
                        }
                    }

            } else {
                currentPlayer = 0;
                players.get(currentPlayer).setTurn(true);
            }
        } else {
            views.get(0).declareWinner();
        }
    }
/*
AI will attempt to buy a house on already owned property then roll the dice to finally either pay someone rent or buy the next property
after the Roll.
 */
    private void makeDecision() {
        //try to buy houses
        buyHouses();
        //roll
        handleAiRoll();
        

    }

    /*
    Attempts to buy a single house on each of the set properties the AI is currently on
     */
    public void handleAIHouses() {
        String colourSet;
        //Checks if AI owns the set he is currently on.
        if((board.get(getPlayer().getLocation())) instanceof Property) {

            colourSet = (((Property) board.get(getPlayer().getLocation())).getSet());
        }else{colourSet = "";}
        int setCount = 0;

        if(colourSet.equals("Brown")){
            colourSet = "#964B00";
        } else if(colourSet.equals("Light Blue")){
            colourSet = "#add8e6";
        } else if(colourSet.equals("Purple")){
            colourSet = "#E36B89";
        } else if(colourSet.equals("Orange")){
            colourSet = "#FFA500";
        } else if(colourSet.equals("Red")){
            colourSet = "#FF0000";
        } else if(colourSet.equals("Yellow")){
            colourSet = "#FFFF00";
        } else if(colourSet.equals("Green")){
            colourSet = "#228B22";
        } else if(colourSet.equals("Dark Blue")){
            colourSet = "#00008B";
        }

        for(Space property: getPlayer().getProperties()){
            if(((Property) property).getSet().equals(colourSet)){ //make sure not p
                setCount++;
            }
        }

        boolean addedHouse = false;
        //brown or light blue set
        if(setCount == 2 && colourSet.equals("#00008B") || setCount == 2 && colourSet.equals("#964B00")){
            if(colourSet.equals("#00008B") && getPlayer().getMoney() >= (200 * 2)){ //dark blue
                getPlayer().removeMoney(400);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(getPlayer().getMoney() >= (50 * 2)){ //brown
                System.out.println("break 222");
                getPlayer().removeMoney(100);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }

        } else  if(setCount == 3){ //all the other sets
            if(colourSet.equals("#add8e6") && getPlayer().getMoney() >= (50 * 3)){ //light blue
                getPlayer().removeMoney(150);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#E36B89") && getPlayer().getMoney() >= (100 * 3)){ //purple
                getPlayer().removeMoney(300);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#FFA500") && getPlayer().getMoney() >= (100 * 3)){ //orange
                getPlayer().removeMoney(300);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#FF0000") && getPlayer().getMoney() >= (150 * 3)){ //red
                getPlayer().removeMoney(450);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#FFFF00") && getPlayer().getMoney() >= (150 * 3)){ //yellow
                getPlayer().removeMoney(450);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#228B22") && getPlayer().getMoney() >= (200 * 3)){ //green
                getPlayer().removeMoney(600);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }
        }
    }


    /** adds a view to the list of views
     *
     * @param view MonopolyView, the view to be added
     */
    public void addMonopolyView(MonopolyView view){
        views.add(view);
    }

    /** removes a view from the list of views
     *
     * @param view MonopolyView, the view to be removed
     */
    public void removeMonopolyView(MonopolyView view){
        views.remove(view);
    }

    /** generateBoard generates a new board
     *
     */
    public void generateBoard(){
        board = new BoardGenerator().generate();
    }

    /** getPLayers returns the arraylist of players
     *
     * @return Arraylist, the arraylist of players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /** getCurrentPlayer returns the current
     *
     * @return int, the index of the current player in the players array list
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }


    /** buyUtilities handles the purchasing of the utilities
      *
     */
    public void buyUtilities(){
        getPlayer().removeMoney(((Utilities) board.get(this.getPlayer().getLocation())).getCost()); //update money
        ((Utilities) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer()); //update owner
        getPlayer().addProperty(board.get(this.getPlayer().getLocation())); //add property to player

    }

    /** buyRailroad handles the purchasing of the railroad
     *
     */
    public void buyRailroad() {
        this.getPlayer().removeMoney(((Railroad) board.get(this.getPlayer().getLocation())).getCost()); //update money
        ((Railroad) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer()); //update owner
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation())); //add property to player

    }

    /** buyProperty handles the purchasing of the properties
     *
     */
    public void buyProperty(){
        //set the properties owner
        this.getPlayer().removeMoney(((Property) board.get(this.getPlayer().getLocation())).getCost());
        ((Property) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer());
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation()));
    }

    /** getPlayer returns the object of the current player
     *
     * @return Player, the current player
     */
    public Player getPlayer(){
        return players.get(currentPlayer);
    }

    public void buyHouses(){
        String colourSet = views.get(0).propertyToAddHouses();
        int setCount = 0;

        if(colourSet.equals("Brown")){
            colourSet = "#964B00";
        } else if(colourSet.equals("Light Blue")){
            colourSet = "#add8e6";
        } else if(colourSet.equals("Purple")){
            colourSet = "#E36B89";
        } else if(colourSet.equals("Orange")){
            colourSet = "#FFA500";
        } else if(colourSet.equals("Red")){
            colourSet = "#FF0000";
        } else if(colourSet.equals("Yellow")){
            colourSet = "#FFFF00";
        } else if(colourSet.equals("Green")){
            colourSet = "#228B22";
        } else if(colourSet.equals("Dark Blue")){
            colourSet = "#00008B";
        }

        for(Space property: getPlayer().getProperties()){
            if(((Property) property).getSet().equals(colourSet)){ //make sure not possible for game to pass something other than properties IE: railroad util
                setCount++;
            }
        }

        boolean addedHouse = false;
        //brown or light blue set
        if(setCount == 2 && colourSet.equals("#00008B") || setCount == 2 && colourSet.equals("#964B00")){
            if(colourSet.equals("#00008B") && getPlayer().getMoney() >= (200 * 2)){ //dark blue
                getPlayer().removeMoney(400);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(getPlayer().getMoney() >= (50 * 2)){ //brown
                System.out.println("break 222");
                getPlayer().removeMoney(100);
                for(Space property: players.get(currentPlayer).getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }

        } else  if(setCount == 3){ //all the other sets
            if(colourSet.equals("#add8e6") && getPlayer().getMoney() >= (50 * 3)){ //light blue
                getPlayer().removeMoney(150);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#E36B89") && getPlayer().getMoney() >= (100 * 3)){ //purple
                getPlayer().removeMoney(300);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#FFA500") && getPlayer().getMoney() >= (100 * 3)){ //orange
                getPlayer().removeMoney(300);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#FF0000") && getPlayer().getMoney() >= (150 * 3)){ //red
                getPlayer().removeMoney(450);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#FFFF00") && getPlayer().getMoney() >= (150 * 3)){ //yellow
                getPlayer().removeMoney(450);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if(colourSet.equals("#228B22") && getPlayer().getMoney() >= (200 * 3)){ //green
                getPlayer().removeMoney(600);
                for(Space property: getPlayer().getProperties()){
                    if(((Property) property).getSet().equals(colourSet)){
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }
        }

        views.get(0).housesAdded(addedHouse);

    }

    public void modRoll(){
        //setting dice 2
        die.setDie(views.get(0).modRollValue());

        if (die.getDoubleCount() > 2) {
            //players.get(currentPlayer).setLocation(10); // jail location - to be implemented lock in jail for 3 turns
            players.get(currentPlayer).setTurn(false);
        }


        if (players.get(currentPlayer).isTurn()) {
            players.get(currentPlayer).setLocation(players.get(currentPlayer).getLocation() + die.getCurrentRoll());

            views.get(0).handleDisplayChar(currentPlayer, players.get(currentPlayer).getLocation(), players.get(currentPlayer).getLocationGUI(players.get(currentPlayer).getLocation()));
            views.get(0).handleDisplay();

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
                    views.get(0).handlePayPlayer(players.get(currentPlayer) , (((Property) board.get(players.get(currentPlayer).getLocation())).getOwner()) , (((Property) board.get(players.get(currentPlayer).getLocation())).getRent()));
                    checkBankrupt();
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
                    views.get(0).handlePayPlayer(players.get(currentPlayer) , (((Railroad) board.get(players.get(currentPlayer).getLocation())).getOwner()) , (((Railroad) board.get(players.get(currentPlayer).getLocation())).getRent()));
                    checkBankrupt();
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
                    views.get(0).handlePayPlayer(players.get(currentPlayer) , (((Utilities) board.get(players.get(currentPlayer).getLocation())).getOwner()) , (((Utilities) board.get(players.get(currentPlayer).getLocation())).getRent(die)));
                    checkBankrupt();
                }
            }

            //check if player is on event space
            if((board.get(players.get(currentPlayer).getLocation()).getName().equals("Luxury Tax")) || (board.get(players.get(currentPlayer).getLocation()).getName().equals("Income tax"))){
                payEvent();
                views.get(0).handlePayEvent(board.get(players.get(currentPlayer).getLocation()));
                checkBankrupt();
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

    void goToJail (){
        players.get(currentPlayer).setLocation(10);
        jail.addPrisoner(players.get(currentPlayer));
        boolean inJail = true;


        while (inJail && players.get(currentPlayer).getJailCount() <=3){
            if (){

                nextTurn();
                players.get(currentPlayer).jailCount();
            }
            else{
                //pay fine
                payJailFine();
                jail.removePrisoner(players.get(currentPlayer));
                inJail = false;
            }
        }

    }
}