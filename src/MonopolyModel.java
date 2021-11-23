import java.util.ArrayList;
import java.util.List;

public class MonopolyModel {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private Dice die;
    public int currentPlayer;
    private List<MonopolyView> views;

    /**
     * getBoard creates the game board
     * @return ArrayList<Space>, board the monopoly board
     */
    public ArrayList<Space> getBoard() {
        return this.board;
    }

    /**
     * Game's constructor creating a new game
     */
    public MonopolyModel(ArrayList<String> names, ArrayList<Integer> aiNumber) {
        this.die = new Dice();
        this.board = new ArrayList<Space>();
        this.generateBoard();
        this.players = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            players.add(new Player(names.get(i), aiNumber.get(i)));
        }
        views = new ArrayList<>();
        players.get(0).setTurn(true);
        if(players.get(0).isAi()){
            makeDecision();
        }
    }

    /**
     * roll handles a player rolling and their movement
     */
    public void roll() {

        //in jail and dont roll doubles
        if (getPlayer().getLockdown() && (getPlayer().isTurn() == true)) {
            boolean result = views.get(0).handleJ();
            if (result) {
                getPlayer().setMoney(getPlayer().getMoney() - 50);
                checkBankrupt();
                getPlayer().removeLockdown();
            } else {
                if (getPlayer().getLockCount() < 3) {
                    getPlayer().increaseLockCount();
                } else {
                    getPlayer().setMoney(getPlayer().getMoney() - 50);
                    checkBankrupt();
                    getPlayer().removeLockdown();
                    getPlayer().resetLockCount();
                }
            }
        }
        die.roll();

        //double handling
        if (die.getDoubleCount() > 2) {
            this.getPlayer().setLockdown();
            this.getPlayer().setLocation(10);
            this.getPlayer().setTurn(false);
        }


        if ((getPlayer().isTurn()) && (getPlayer().getLockdown()) == false) {
            getPlayer().setLocation(getPlayer().getLocation() + die.getCurrentRoll());


            //check if property is purchasable and what kind it is
            if ((board.get(getPlayer().getLocation()) instanceof Property)) {
                if (((Property) board.get(getPlayer().getLocation())).getOwner() == null) {
                    if (views.get(0).handleBuyProperty(board.get(getPlayer().getLocation()))) { // if they say yes to buying the property
                        if (((Property) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney()) {
                            buyProperty();
                        }
                    }
                } else {
                    payPropertyRent();
                    views.get(0).handlePayPlayer(getPlayer(), (((Property) board.get(getPlayer().getLocation())).getOwner()), (((Property) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if ((board.get(getPlayer().getLocation()) instanceof Railroad)) {
                if (((Railroad) board.get(getPlayer().getLocation())).getOwner() == null) {
                    if (views.get(0).handleBuyProperty(board.get(getPlayer().getLocation()))) {
                        if (((Railroad) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney()) {
                            buyRailroad();
                        }
                    }
                } else {
                    payRailroadRent();
                    views.get(0).handlePayPlayer(getPlayer(), (((Railroad) board.get(getPlayer().getLocation())).getOwner()), (((Railroad) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if ((board.get(getPlayer().getLocation()) instanceof Utilities)) {
                if (((Utilities) board.get(getPlayer().getLocation())).getOwner() == null) {
                    if (views.get(0).handleBuyProperty(board.get(getPlayer().getLocation()))) {
                        if (((Utilities) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney()) {
                            buyUtilities();
                        }
                    }
                } else {
                    payUtilitiesRent(die);
                    views.get(0).handlePayPlayer(getPlayer(), (((Utilities) board.get(getPlayer().getLocation())).getOwner()), (((Utilities) board.get(getPlayer().getLocation())).getRent(die)));
                    checkBankrupt();
                }
            }

            //check if player is on event space
            if ((board.get(getPlayer().getLocation()).getName().equals("Luxury Tax")) || (board.get(getPlayer().getLocation()).getName().equals("Income tax"))) {
                payEvent();
                views.get(0).handlePayEvent(board.get(getPlayer().getLocation()));
                checkBankrupt();
            }

            if ((board.get(getPlayer().getLocation()).getName().equals("Go To Jail"))) {
                this.getPlayer().setLockdown();
                this.getPlayer().setLocation(10);
                this.getPlayer().setTurn(false);
            }

        }

        views.get(0).handleDisplayChar(currentPlayer, getPlayer().getLocation(), getPlayer().getLocationGUI(getPlayer().getLocation()));
        views.get(0).handleDisplay();

        if (die.isDoubles() && (getPlayer().getLockdown() == false)) {
            System.out.println("got doubles");
            getPlayer().setTurn(true);
            //set ai turn to true
            if (getPlayer().isAi()) {
                makeDecision();
            }
        }
        if (die.isDoubles() && (getPlayer().getLockdown()) && (getPlayer().getLockCount() != 0)) {
            System.out.println("got doubles, you are out of jail");
            getPlayer().removeLockdown();
            getPlayer().setTurn(true);
            //set ai turn to true
            if (getPlayer().isAi()) {
                makeDecision();
            }
        } else {
            getPlayer().setTurn(false);
        }

        if (getPlayer().isTurn() == false) {
            nextTurn();
            die.resetDoubles();
        }

        views.get(0).alertWhoseTurn();
    }

    /**
     * handleAiRoll defines the rolling behaviour, their movement and interactions with Spaces
     */
    private void handleAiRoll() {

        if (getPlayer().getLockdown() && (getPlayer().isTurn() == true)) {
            if (getPlayer().getMoney() > 100) {
                getPlayer().setMoney(getPlayer().getMoney() - 50);
                checkBankrupt();
                getPlayer().removeLockdown();
            } else {
                if (getPlayer().getLockCount() < 3) {
                    getPlayer().increaseLockCount();
                } else {
                    getPlayer().setMoney(getPlayer().getMoney() - 50);
                    checkBankrupt();
                    getPlayer().removeLockdown();
                    getPlayer().resetLockCount();
                }
            }
        }
        die.roll();

        //double handling
        if (die.getDoubleCount() > 2) {
            this.getPlayer().setLockdown();
            this.getPlayer().setLocation(10);
            this.getPlayer().setTurn(false);
            views.get(0).handleDisplayChar(currentPlayer, getPlayer().getLocation(), getPlayer().getLocationGUI(getPlayer().getLocation()));
            views.get(0).handleDisplay();
        }
        if (getPlayer().isTurn() && (getPlayer().getLockdown()) == false) {
            getPlayer().setLocation(getPlayer().getLocation() + die.getCurrentRoll());
            System.out.println(getPlayer().getLocation());

            //check if property is purchasable and what kind it is
            if ((board.get(getPlayer().getLocation()) instanceof Property)) {
                if (((Property) board.get(getPlayer().getLocation())).getOwner() == null) {
                    // if they afford property
                    if (((Property) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney() && getPlayer().getMoney() >= 300) {
                        buyProperty();
                    }

                } else {
                    payPropertyRent();
                    views.get(0).handlePayPlayer(getPlayer(), (((Property) board.get(getPlayer().getLocation())).getOwner()), (((Property) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if ((board.get(getPlayer().getLocation()) instanceof Railroad)) {
                if (((Railroad) board.get(getPlayer().getLocation())).getOwner() == null) {
                    if (((Railroad) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney() && getPlayer().getMoney() >= 300) {
                        buyRailroad();
                    }

                } else {
                    payRailroadRent();
                    views.get(0).handlePayPlayer(getPlayer(), (((Railroad) board.get(getPlayer().getLocation())).getOwner()), (((Railroad) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if ((board.get(getPlayer().getLocation()) instanceof Utilities)) {
                if (((Utilities) board.get(getPlayer().getLocation())).getOwner() == null) {

                    if (((Utilities) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney() && getPlayer().getMoney() >= 300) {
                        buyUtilities();
                    }

                } else {
                    payUtilitiesRent(die);
                    views.get(0).handlePayPlayer(getPlayer(), (((Utilities) board.get(getPlayer().getLocation())).getOwner()), (((Utilities) board.get(getPlayer().getLocation())).getRent(die)));
                    checkBankrupt();
                }
            }

            //check if player is on event space
            if ((board.get(getPlayer().getLocation()).getName().equals("Luxury Tax")) || (board.get(getPlayer().getLocation()).getName().equals("Income tax"))) {
                payEvent();
                views.get(0).handlePayEvent(board.get(getPlayer().getLocation()));
                checkBankrupt();
            }

            if ((board.get(getPlayer().getLocation()).getName().equals("Go To Jail"))) {
                this.getPlayer().setLockdown();
                this.getPlayer().setLocation(10);
                this.getPlayer().setTurn(false);
            }

        }

        if (die.isDoubles() && (getPlayer().getLockdown() == false)) {
            System.out.println("got doubles");
            System.out.println(getPlayer().getLocation());
            getPlayer().setTurn(true);
            //set ai turn to true
            makeDecision();
        }
        else if (die.isDoubles() && (getPlayer().getLockdown()) && (getPlayer().getLockCount() != 0)) {
            System.out.println("got doubles, you are out of jail");
            System.out.println(getPlayer().getLocation() + "jail");
            getPlayer().removeLockdown();
            getPlayer().setTurn(true);
            makeDecision();

        } else {
            getPlayer().setTurn(false);
        }

        if(!views.isEmpty()) {
            views.get(0).handleDisplayChar(currentPlayer, getPlayer().getLocation(), getPlayer().getLocationGUI(getPlayer().getLocation()));
            views.get(0).handleDisplay();
        }

        if (getPlayer().isTurn() == false) {
            nextTurn();
            die.resetDoubles();
        }
    }

    /**
     * combine all player info into a string to send to frame
     *
     * @return String, all the info
     */
    public void status() {
        StringBuilder sb = new StringBuilder("");
        for (Player player : players) {
            sb.append("Player: " + player.getName() + " has: \n$" + player.getMoney() + " \nAnd the following properties: \n");
            for (Space property : player.getProperties()) {
                if (property instanceof Property) {
                    sb.append(property.getName() + " with " + ((Property) property).getHouses() + " houses \n");
                } else sb.append(property.getName() + "\n");
            }
            sb.append("\n");
        }

        views.get(0).handleStatus(sb);


    }

    /**
     * checkBankrupt checks if the current player has gone bankrupt
     */
    public void checkBankrupt() {
        if (getPlayer().getMoney() <= 0) {
            getPlayer().setPlaying(false);
        }
    }

    /**
     * payProperty rent handles the property payment between the owner and renter
     */
    public void payPropertyRent() {
        int rent = ((Property) board.get(this.getPlayer().getLocation())).getRent();
        //add money to landlord
        ((Property) board.get(this.getPlayer().getLocation())).getOwner().addMoney(rent);
        //remove rent from current player
        this.getPlayer().removeMoney(rent);
    }

    /**
     * payProperty rent handles the railroad rental payment between the owner and renter
     */
    public void payRailroadRent() {
        //NoOfRailroads = 1,2,3,4
        if (((Railroad) board.get(this.getPlayer().getLocation())).getOwner().getNoRailroads() == 1) {
            //pay Railroad rent to landlord
            ((Railroad) board.get(this.getPlayer().getLocation())).getOwner().addMoney(25);
            //remove rent from current player
            this.getPlayer().removeMoney(25);
        } else if (((Railroad) board.get(this.getPlayer().getLocation())).getOwner().getNoRailroads() == 2) {
            //pay rent to landlord
            ((Railroad) board.get(getPlayer().getLocation())).getOwner().addMoney(50);
            //remove rent from current player
            this.getPlayer().removeMoney(50);

        } else if (((Railroad) board.get(this.getPlayer().getLocation())).getOwner().getNoRailroads() == 3) {
            //pay rent to landlord
            ((Railroad) board.get(this.getPlayer().getLocation())).getOwner().addMoney(100);
            //remove rent from current player
            this.getPlayer().removeMoney(100);

        } else {
            //pay rent to landlord
            ((Railroad) board.get(getPlayer().getLocation())).getOwner().setMoney(((Railroad) board.get(getPlayer().getLocation())).getOwner().getMoney() + 200);
            //remove rent from current player
            this.getPlayer().removeMoney(200);

        }
    }

    /**
     * payProperty rent handles the Utilities' payment between the owner and renter
     */
    public void payUtilitiesRent(Dice d) {
        //cost of rent
        int rent = ((Utilities) board.get(this.getPlayer().getLocation())).getRent(d);
        //((Utilities) board.get(model.getPlayers().get(0).getLocation())).getRent(die);
        //add money to landlord
        ((Utilities) board.get(this.getPlayer().getLocation())).getOwner().addMoney(rent);
        //remove rent from current player
        this.getPlayer().removeMoney(rent);
    }

    /**
     * payEvent handles the player paying the amount due on a property
     */
    public void payEvent() {
        this.getPlayer().removeMoney(((Event) board.get(getPlayer().getLocation())).getPayment());
    }

    /**
     * nextTurn passes the turn off to the next player
     */
    public void nextTurn() {
        for (Player player : players) {
            if (!getPlayer().isPlaying()) {
                views.get(0).declareBankruptPlayer();
                players.remove(getPlayer());
            }
        }

        if (players.size() != 1) {
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
                    if (getPlayer().isAi()) {
                        makeDecision();
                    }
                }

            } else {
                currentPlayer = 0;
                getPlayer().setTurn(true);
            }
        } else { // there is one player left and they are declared the winner
            views.get(0).declareWinner();
        }


    }

    /**
     * makeDecision groups the functions of handleAIHouses and handleAiRoll
     */
    private void makeDecision() {
        //try to buy houses
        handleAIHouses();
        //roll
        handleAiRoll();


    }

    /**
     * handleAIHouses handles the AI behaviour of purchasing houses if the computer owns full sets
     */
    public void handleAIHouses() {
        String colourSet;
        //Checks if AI owns the set he is currently on.
        if ((board.get(getPlayer().getLocation())) instanceof Property) {
            colourSet = (((Property) board.get(getPlayer().getLocation())).getSet());
        } else {
            colourSet = "";
        }
        int setCount = 0;

        for (Space property : getPlayer().getProperties()) {
            if (property instanceof Property) {
                if (((Property) property).getSet().equals(colourSet)) { //make sure not p
                    setCount++;
                }
            }
        }

        boolean addedHouse = false;
        //brown or dark blue set
        if (setCount == 2 && colourSet.equals("#00008B") || setCount == 2 && colourSet.equals("#964B00")) {
            if (colourSet.equals("#00008B") && getPlayer().getMoney() >= (200 * 2)) { //dark blue
                getPlayer().removeMoney(400);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (getPlayer().getMoney() >= (50 * 2)) { //brown
                getPlayer().removeMoney(100);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }

        } else if (setCount == 3) { //all the other sets
            if (colourSet.equals("#add8e6") && getPlayer().getMoney() >= (50 * 3)) { //light blue
                getPlayer().removeMoney(150);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#E36B89") && getPlayer().getMoney() >= (100 * 3)) { //purple
                getPlayer().removeMoney(300);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#FFA500") && getPlayer().getMoney() >= (100 * 3)) { //orange
                getPlayer().removeMoney(300);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#FF0000") && getPlayer().getMoney() >= (150 * 3)) { //red
                getPlayer().removeMoney(450);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#FFFF00") && getPlayer().getMoney() >= (150 * 3)) { //yellow
                getPlayer().removeMoney(450);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#228B22") && getPlayer().getMoney() >= (200 * 3)) { //green
                getPlayer().removeMoney(600);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }
        }
    }


    /**
     * adds a view to the list of views
     *
     * @param view MonopolyView, the view to be added
     */
    public void addMonopolyView(MonopolyView view) {
        views.add(view);
    }

    /**
     * removes a view from the list of views
     *
     * @param view MonopolyView, the view to be removed
     */
    public void removeMonopolyView(MonopolyView view) {
        views.remove(view);
    }

    /**
     * generateBoard generates a new board
     */
    public void generateBoard() {
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
    }

    /**
     * getPLayers returns the arraylist of players
     *
     * @return Arraylist, the arraylist of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * getCurrentPlayer returns the current
     *
     * @return int, the index of the current player in the players array list
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * buyUtilities handles the purchasing of the utilities
     */
    public void buyUtilities() {
        this.getPlayer().removeMoney(((Utilities) board.get(this.getPlayer().getLocation())).getCost()); //update money
        ((Utilities) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer()); //update owner
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation())); //add property to player

    }

    /**
     * buyRailroad handles the purchasing of the railroad
     */
    public void buyRailroad() {
        this.getPlayer().removeMoney(((Railroad) board.get(this.getPlayer().getLocation())).getCost()); //update money
        ((Railroad) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer()); //update owner
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation())); //add property to player

    }

    /**
     * buyProperty handles the purchasing of the properties
     */
    public void buyProperty() {
        //set the properties owner
        this.getPlayer().removeMoney(((Property) board.get(this.getPlayer().getLocation())).getCost());
        ((Property) board.get(this.getPlayer().getLocation())).setOwner(this.getPlayer());
        this.getPlayer().addProperty(board.get(this.getPlayer().getLocation()));
    }

    /**
     * getPlayer returns the object of the current player
     *
     * @return Player, the current player
     */
    public Player getPlayer() {
        return players.get(currentPlayer);
    }

    /**
     * buyHouses prompts the user to choose a set and if they have the set it will add one house to each of them and deduct the corresponding price
     */
    public void buyHouses() {
        String colourSet;


        colourSet = views.get(0).propertyToAddHouses();

        int setCount = 0;

        if (colourSet.equals("Brown")) {
            colourSet = "#964B00";
        } else if (colourSet.equals("Light Blue")) {
            colourSet = "#add8e6";
        } else if (colourSet.equals("Purple")) {
            colourSet = "#E36B89";
        } else if (colourSet.equals("Orange")) {
            colourSet = "#FFA500";
        } else if (colourSet.equals("Red")) {
            colourSet = "#FF0000";
        } else if (colourSet.equals("Yellow")) {
            colourSet = "#FFFF00";
        } else if (colourSet.equals("Green")) {
            colourSet = "#228B22";
        } else if (colourSet.equals("Dark Blue")) {
            colourSet = "#00008B";
        }

        for (Space property : getPlayer().getProperties()) {
            if (((Property) property).getSet().equals(colourSet)) { //make sure not p
                setCount++;
            }
        }

        boolean addedHouse = false;
        //brown or light blue set
        if (setCount == 2 && colourSet.equals("#00008B") || setCount == 2 && colourSet.equals("#964B00")) {
            if (colourSet.equals("#00008B") && getPlayer().getMoney() >= (200 * 2)) { //dark blue
                getPlayer().removeMoney(400);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (getPlayer().getMoney() >= (50 * 2)) { //brown
                getPlayer().removeMoney(100);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }

        } else if (setCount == 3) { //all the other sets
            if (colourSet.equals("#add8e6") && getPlayer().getMoney() >= (50 * 3)) { //light blue
                getPlayer().removeMoney(150);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#E36B89") && getPlayer().getMoney() >= (100 * 3)) { //purple
                getPlayer().removeMoney(300);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#FFA500") && getPlayer().getMoney() >= (100 * 3)) { //orange
                getPlayer().removeMoney(300);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#FF0000") && getPlayer().getMoney() >= (150 * 3)) { //red
                getPlayer().removeMoney(450);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#FFFF00") && getPlayer().getMoney() >= (150 * 3)) { //yellow
                getPlayer().removeMoney(450);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            } else if (colourSet.equals("#228B22") && getPlayer().getMoney() >= (200 * 3)) { //green
                getPlayer().removeMoney(600);
                for (Space property : getPlayer().getProperties()) {
                    if (((Property) property).getSet().equals(colourSet)) {
                        addedHouse = ((Property) property).addHouse(1);
                    }
                }
            }
        }
        if (!getPlayer().isAi()) {
            views.get(0).housesAdded(addedHouse);
        }
    }


    /** modRoll is a function used only for testing purposes, the tester is prompted to enter a distance they wish to travel
     *
     */
    public void modRoll() {
        //setting dice 2
        if (getPlayer().getLockdown() && (getPlayer().isTurn() == true)) {
            boolean result = views.get(0).handleJ();
            if (result) {
                getPlayer().setMoney(getPlayer().getMoney() - 50);
                getPlayer().removeLockdown();
            } else {
                if (getPlayer().getLockCount() < 3) {
                    getPlayer().increaseLockCount();
                } else {
                    getPlayer().setMoney(getPlayer().getMoney() - 50);
                    getPlayer().removeLockdown();
                    getPlayer().resetLockCount();
                }
            }
        }
        die.setDie(views.get(0).modRollValue());

        //double handling
        if (die.getDoubleCount() > 2) {
            this.getPlayer().setLockdown();
            this.getPlayer().setLocation(10);
            this.getPlayer().setTurn(false);
        }


        if ((getPlayer().isTurn()) && (getPlayer().getLockdown()) == false) {
            getPlayer().setLocation(getPlayer().getLocation() + die.getCurrentRoll());


            //check if property is purchasable and what kind it is
            if ((board.get(getPlayer().getLocation()) instanceof Property)) {
                if (((Property) board.get(getPlayer().getLocation())).getOwner() == null) {
                    if (views.get(0).handleBuyProperty(board.get(getPlayer().getLocation()))) { // if they say yes to buying the property
                        if (((Property) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney()) {
                            buyProperty();
                        }
                    }
                } else {
                    payPropertyRent();
                    views.get(0).handlePayPlayer(getPlayer(), (((Property) board.get(getPlayer().getLocation())).getOwner()), (((Property) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if ((board.get(getPlayer().getLocation()) instanceof Railroad)) {
                if (((Railroad) board.get(getPlayer().getLocation())).getOwner() == null) {
                    if (views.get(0).handleBuyProperty(board.get(getPlayer().getLocation()))) {
                        if (((Railroad) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney()) {
                            buyRailroad();
                        }
                    }
                } else {
                    payRailroadRent();
                    views.get(0).handlePayPlayer(getPlayer(), (((Railroad) board.get(getPlayer().getLocation())).getOwner()), (((Railroad) board.get(getPlayer().getLocation())).getRent()));
                    checkBankrupt();
                }
            }
            if ((board.get(getPlayer().getLocation()) instanceof Utilities)) {
                if (((Utilities) board.get(getPlayer().getLocation())).getOwner() == null) {
                    if (views.get(0).handleBuyProperty(board.get(getPlayer().getLocation()))) {
                        if (((Utilities) board.get(getPlayer().getLocation())).getCost() <= getPlayer().getMoney()) {
                            buyUtilities();
                        }
                    }
                } else {
                    payUtilitiesRent(die);
                    views.get(0).handlePayPlayer(getPlayer(), (((Utilities) board.get(getPlayer().getLocation())).getOwner()), (((Utilities) board.get(getPlayer().getLocation())).getRent(die)));
                    checkBankrupt();
                }
            }

            //check if player is on event space
            if ((board.get(getPlayer().getLocation()).getName().equals("Luxury Tax")) || (board.get(getPlayer().getLocation()).getName().equals("Income tax"))) {
                payEvent();
                views.get(0).handlePayEvent(board.get(getPlayer().getLocation()));
                checkBankrupt();
            }

            if ((board.get(getPlayer().getLocation()).getName().equals("Go To Jail"))) {
                this.getPlayer().setLockdown();
                this.getPlayer().setLocation(10);
                this.getPlayer().setTurn(false);
            }

        }

        views.get(0).handleDisplayChar(currentPlayer, getPlayer().getLocation(), getPlayer().getLocationGUI(getPlayer().getLocation()));
        views.get(0).handleDisplay();

        if (die.isDoubles() && (getPlayer().getLockdown() == false)) {
            System.out.println("got doubles");
            getPlayer().setTurn(true);
            //set ai turn to true
            if (getPlayer().isAi()) {
                makeDecision();
            }
        }
        if (die.isDoubles() && (getPlayer().getLockdown()) && (getPlayer().getLockCount() != 0)) {
            System.out.println("got doubles, you are out of jail");
            getPlayer().removeLockdown();
            getPlayer().setTurn(true);
            //set ai turn to true
            if (getPlayer().isAi()) {
                makeDecision();
            }
        } else {
            getPlayer().setTurn(false);
        }

        if (getPlayer().isTurn() == false) {
            nextTurn();
            die.resetDoubles();
        }
    }
}