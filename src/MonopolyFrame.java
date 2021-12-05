import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MonopolyFrame extends JFrame implements MonopolyView{
    private final int BoardLength = 11;


    MonopolyModel model;

    private JPanel top;
    private JPanel bot;
    private JPanel[][] spaces;
    private ArrayList<Integer> locations;
    private ArrayList<Integer> locationsGUI;
    private ArrayList<String> displayName;
    private ArrayList<JLabel> JName;
    private int count2;
    private int count3;
    private int count;
    private int locationGUI;

    /** the construct for MonopolyFrame which draws the initial board
     *
     */
    public MonopolyFrame(){
        super("Monopoly 0.5");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,900);


        //Set up number of players;
        String NOplayers = "1";
        while(Integer.parseInt( NOplayers) < 2 || Integer.parseInt( NOplayers) > 8 ) {
            NOplayers = JOptionPane.showInputDialog(this, "Enter a number of players (2-8): ", 2);
        }


        //set player locations

        int pnum = Integer.parseInt(NOplayers);
        locations = new ArrayList<>();
        while(pnum != 0){
            locations.add(0);
            pnum--;
        }

        //set player locations GUI
        int xnum = Integer.parseInt(NOplayers);
        locationsGUI = new ArrayList<>();
        while(xnum != 0){
            locationsGUI.add(0);
            xnum--;
        }

        //Enter name of each player
        displayName = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> aiNumber = new ArrayList<>();
        for(int i = 0; i < Integer.parseInt( NOplayers); i++){
            String name = JOptionPane.showInputDialog(this, "Enter name of Player " + (i + 1) + ":" );
            //Joption pane yesno for is current player AI?
            int result = JOptionPane.showConfirmDialog(this,"Would you like for this player to be a robot?","Add AI oponent?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                aiNumber.add(1);
            } else{
                aiNumber.add(0);
            }
                //JLabel f = new JLabel(name);
                //f.setFont(new Font("Verdana", 1, 11));
                //JName.add(f);
                displayName.add(name);
                names.add(name);
        }

        model = new MonopolyModel(names,aiNumber);
        model.addMonopolyView(this);
        handleDisplay();
    }


    /** handleDisplay redraws the board with the new location of players
     *
     */
    public void handleDisplay(){

        top = new JPanel(new GridLayout(BoardLength, BoardLength));
        bot = new JPanel(new GridLayout(1,5));
        this.add(top, BorderLayout.CENTER);
        this.add(bot, BorderLayout.PAGE_END);

        MonopolyController mc = new MonopolyController(model);

        spaces = new JPanel[BoardLength][BoardLength];

        //buttons
        JButton rollB = new JButton("Roll");
        rollB.setActionCommand("roll");
        rollB.addActionListener(mc);

        JButton housesB = new JButton("Buy House");
        housesB.setActionCommand("houses");
        housesB.addActionListener(mc);

        JButton statusB = new JButton("Status");
        statusB.setActionCommand("status");
        statusB.addActionListener(mc);

        //TODO remove mod R
        JButton modR = new JButton("ModRoll");
        modR.setActionCommand("modRoll");
        modR.addActionListener(mc);

        bot.add(rollB);
        bot.add(housesB);
        bot.add(new JPanel());
        //TODO remove mod R
        bot.add(modR);
        bot.add(statusB);
        //add grab color

        //loop and create the panels in a grid
        for (int i = 0; i < BoardLength; i++) {
            for (int j = 0; j < BoardLength; j++) {
                JPanel space = new JPanel();
                spaces[i][j] = space;
                space.setLayout(new GridLayout(4, 1));
                top.add(space);
            }
        }
        int flag = 1;
        int flag2 = 1;
        int flag3 = 1;
        int flag4 = 1;
        //for first row
        for(int j = 0; j < BoardLength; j++){
            String house = "";
            if(model.getBoard().get(j) instanceof Property){
                if(((Property) model.getBoard().get(j)).getHouses() < 5) {
                    house = new String(new char[((Property) model.getBoard().get(j)).getHouses()]).replace("\0", "*");
                }
                else{
                    house = "H";
                }
            }

            String temp = house + " " + model.getBoard().get(j).getName();
            JLabel pName = new JLabel(temp, JLabel.CENTER);
            pName.setFont(new Font("Verdana",1,9));
            spaces[0][j].add(pName);

            if(model.getBoard().get(j) instanceof Event){
                if(((Event) model.getBoard().get(j)).getPayment() != 0){
                    JLabel pay = new JLabel("Payment: " + ((Event) model.getBoard().get(j)).getPayment(), JLabel.CENTER);
                    pay.setFont(new Font("Verdana",1,10));
                    spaces[0][j].add(pay);
                }

            }

            if(model.getBoard().get(j) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200", JLabel.CENTER);
                JLabel price = new JLabel("Price: 200", JLabel.CENTER);
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[0][j].add(rent1);
                spaces[0][j].add(price);

            }

            if(model.getBoard().get(j) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(j)).getCost()), JLabel.CENTER);
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(j)).getRent()), JLabel.CENTER);
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[0][j].add(rent);
                spaces[0][j].add(price);
                spaces[0][j].setBackground(Color.decode(((Property) model.getBoard().get(j)).getSet()));


            }
            if(flag == 1) {
                for (int counter = 0; counter < locations.size(); counter++) {

                    if (locations.get(counter) < 11 ) {
                        JLabel test = new JLabel(displayName.get(counter));
                        spaces[0][locationsGUI.get(counter)].add(test);
                    }
                }
                flag = 0;
            }
            spaces[0][j].setBorder(BorderFactory.createLineBorder(Color.black));
        }

        count2 = 30;
        //for last row
        for(int j = 0; j < BoardLength; j++){
            String house = "";
            if(model.getBoard().get(count2) instanceof Property){
                if(((Property) model.getBoard().get(count2)).getHouses() < 5) {
                    house = new String(new char[((Property) model.getBoard().get(count2)).getHouses()]).replace("\0", "*");
                }
                else{
                    house = "H";
                }
            }
            String temp = house + " " + model.getBoard().get(count2).getName();
            JLabel pName = new JLabel(temp, JLabel.CENTER);
            pName.setFont(new Font("Verdana",1,9));
            spaces[10][j].add(pName);


            if(model.getBoard().get(count2) instanceof Utilities){
                JLabel rent1 = new JLabel("Dice Multiplier: 4x, 10x", JLabel.CENTER);
                JLabel price = new JLabel("Price: 150", JLabel.CENTER);
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[10][j].add(rent1);
                spaces[10][j].add(price);
            }

            if(model.getBoard().get(count2) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200", JLabel.CENTER);
                JLabel price = new JLabel("Price: 200", JLabel.CENTER);
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[10][j].add(rent1);
                spaces[10][j].add(price);
            }

            if(model.getBoard().get(count2) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(count2)).getCost()), JLabel.CENTER);
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(count2)).getRent()), JLabel.CENTER);
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[10][j].add(rent);
                spaces[10][j].add(price);
                spaces[10][j].setBackground(Color.decode(((Property) model.getBoard().get(count2)).getSet()));
            }
            if(flag2 == 1) {
                for (int counter = 0; counter < locations.size(); counter++) {
                    if ((locations.get(counter) < 31) && (locations.get(counter) > 19)) {
                        JLabel test = new JLabel(displayName.get(counter), JLabel.CENTER);
                        spaces[10][locationsGUI.get(counter)].add(test);
                    }
                }
                flag2 = 0;
            }

            spaces[10][j].setBorder(BorderFactory.createLineBorder(Color.black));
            count2--;
        }

        int count3 = 39;
        //for first column
        for(int i = 1; i < (BoardLength-1); i++){
            String house = "";
            if(model.getBoard().get(count3) instanceof Property){
                if(((Property) model.getBoard().get(count3)).getHouses() < 5) {
                    house = new String(new char[((Property) model.getBoard().get(count3)).getHouses()]).replace("\0", "*");
                }
                else{
                    house = "H";
                }
            }
            String temp = house + " " + model.getBoard().get(count3).getName();
            JLabel pName = new JLabel(temp, JLabel.CENTER);
            pName.setFont(new Font("Verdana",1,9));
            spaces[i][0].add(pName);


            if(model.getBoard().get(count3) instanceof Event){
                if(((Event) model.getBoard().get(count3)).getPayment() != 0){
                    JLabel pay = new JLabel("Payment: " + ((Event) model.getBoard().get(count3)).getPayment(), JLabel.CENTER);
                    pay.setFont(new Font("Verdana",1,10));
                    spaces[i][0].add(pay);
                }

            }

            if(model.getBoard().get(count3) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200", JLabel.CENTER);
                JLabel price = new JLabel("Price: 200", JLabel.CENTER);
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][0].add(rent1);
                spaces[i][0].add(price);
            }

            if(model.getBoard().get(count3) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(count3)).getCost()), JLabel.CENTER);
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(count3)).getRent()), JLabel.CENTER);
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][0].add(rent);
                spaces[i][0].add(price);
                spaces[i][0].setBackground(Color.decode(((Property) model.getBoard().get(count3)).getSet()));
            }

            if(flag3 == 1) {
                for (int counter = 0; counter < locations.size(); counter++) {
                    if ((locations.get(counter) <= 39) && (locations.get(counter) >= 31)) {
                        JLabel test = new JLabel(displayName.get(counter), JLabel.CENTER);
                        spaces[locationsGUI.get(counter)][0].add(test);
                    }
                }
                flag3 = 0;
            }
            spaces[i][0].setBorder(BorderFactory.createLineBorder(Color.black));
            count3--;

        }
        //for second column
        int count = 11;
        for(int i = 1; i < (BoardLength-1); i++){
            String house = "";
            if(model.getBoard().get(count) instanceof Property){
                if(((Property) model.getBoard().get(count)).getHouses() < 5) {
                    house = new String(new char[((Property) model.getBoard().get(count)).getHouses()]).replace("\0", "*");
                }
                else{
                    house = "H";
                }
            }
            String temp = house + " " + model.getBoard().get(count).getName();
            JLabel a = new JLabel(temp, JLabel.CENTER );
            a.setFont(new Font("Verdana",1,9));
            spaces[i][10].add(a);

            if(model.getBoard().get(count) instanceof Utilities){
                JLabel rent1 = new JLabel("Dice Multiplier: 4x, 10x", JLabel.CENTER);
                JLabel price = new JLabel("Price: 150", JLabel.CENTER);
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][10].add(rent1);
                spaces[i][10].add(price);
            }


            if(model.getBoard().get(count) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200", JLabel.CENTER);
                JLabel price = new JLabel("Price: 200", JLabel.CENTER);
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][10].add(rent1);
                spaces[i][10].add(price);
            }

            if(model.getBoard().get(count) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(count)).getCost()), JLabel.CENTER);
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(count)).getRent()), JLabel.CENTER);
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][10].add(rent);
                spaces[i][10].add(price);
                spaces[i][10].setBackground(Color.decode(((Property) model.getBoard().get(count)).getSet()));
            }
            if(flag4 == 1) {
                for (int counter = 0; counter < locations.size(); counter++) {
                    if ((locations.get(counter) <= 19) && (locations.get(counter) >= 11)) {
                        JLabel test = new JLabel(displayName.get(counter), JLabel.CENTER);
                        spaces[locationsGUI.get(counter)][10].add(test);
                    }
                }
                flag4 = 0;
            }
            spaces[i][10].setBorder(BorderFactory.createLineBorder(Color.black));
            count++;
        }

        //Menu Stuff
        JMenuBar menu = new JMenuBar();
        this.setJMenuBar(menu);

        JMenu file = new JMenu("File");
        menu.add(file);

        JMenuItem load = new JMenuItem("Load");
        file.add(load);
        load.setActionCommand("load");
        load.addActionListener(mc);

        JMenuItem save = new JMenuItem("Save");
        file.add(save);
        save.setActionCommand("save");
        save.addActionListener(mc);



        this.setVisible(true);
    }


    /** handleStatus will display a popup with the all the players information
     *
     * @param status String, the string containing all the status information
     */
    public void handleStatus(StringBuilder status) {
        JOptionPane.showMessageDialog(this, status);

    }

    /** handleDisplayChar updates the location of the player in the array list and the actual board location on the grid
     *
     * @param curPlayer int, the current players index in the array lists
     * @param location int, their location on the arraylist
     * @param locationGUI int, their location on the grid
     */
    public void handleDisplayChar(int curPlayer, int location, int locationGUI){
        locations.set(curPlayer, location);
        locationsGUI.set(curPlayer, locationGUI);
    }

    /** handleBuyProperty prompts the player to buy or pass on the property
     *
     * @param space Space, the purchasable space
     * @return boolean, whether the property has been purchased or not
     */
    public boolean handleBuyProperty(Space space){
        int result = JOptionPane.showConfirmDialog(this,"Hey "+ model.getPlayer().getName() + " you just landed on " + space.getName() + " would you like to buy it?", "Would you like to buy property",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            return true;
        } else return false;
    }

    /**
     * handleJ prompts the player to pay 50 to get out of jail
     * @return boolean, whether they payed 50 or not
     */

    public boolean handleJ(){
        int result = JOptionPane.showConfirmDialog(this,"Do you want to pay 50 to get out of jail?", "pay to get out of jail?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            return true;
        } else return false;
    }



    /** handlePayEvent displays what event the player landed on and how much they pay out
     *
     * @param space Space, which event space the player landed on
     */
    public void handlePayEvent(Space space){
        JOptionPane.showMessageDialog(this, model.getPlayer().getName() + " you just landed on " + model.getBoard().get(model.getPlayer().getLocation()).getName() + " and had to pay $" + ((Event) model.getBoard().get(model.getPlayer().getLocation())).getPayment());
    }

    /** declareWinner displays who won the game
     *
     */
    public void declareWinner(){
        JOptionPane.showMessageDialog(this, model.getPlayer().getName() + " you won!");
    }

    /** declareBankruptPlayer displays the player that just went bankrupt
     *
     */
    public void declareBankruptPlayer(){
        JOptionPane.showMessageDialog(this, model.getPlayer().getName() + " you've gone bankrupt you lose!");
    }

    /** handlePayPlayer displays which player landed on whose property and how much they owe the other player
     *
     * @param p1 Player, the player that landed on the property
     * @param p2 Player, the owner of the property
     * @param payment int, how much money was transacted
     */
    public void handlePayPlayer(Player p1, Player p2, int payment){
        JOptionPane.showMessageDialog(this, p1.getName() + " you just landed on " + model.getBoard().get(model.getPlayer().getLocation()).getName() + " and had to pay " + p2.getName() + " $" + payment);
    }


    /**
     * propertyToAddHouses prompt the user to select which set to add houses to
     * @return String, colourSet the set that the user wishes to add houses to
     */
    public String propertyToAddHouses(){
        String[] options = {"Brown", "Light Blue", "Purple", "Orange", "Red", "Yellow", "Green", "Dark Blue"};
        Object selectionObject = JOptionPane.showInputDialog(this, "Choose the colour set you wish to add a set of houses to:", "House Selection", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        String colourSet = selectionObject.toString();
        return colourSet;
    }

    /**
     * housesAdded checks if the houses were successfully added or not
     * @param housesAdded boolean, if the transaction was a success or not
     */

    public void housesAdded(boolean housesAdded){
        if(housesAdded){
            JOptionPane.showMessageDialog(this, "Houses were successfully added");
        } else {
            JOptionPane.showMessageDialog(this, "Houses could not be added");
        }
    }

    /** modRollValue prompts the user to input a distance they wish to travel, this is only used for testing and not for actual gameplay
     *
     * @return int, the Value the player entered
     */
    public int modRollValue(){
        String rollValue = JOptionPane.showInputDialog(this, "Enter a roll number ", 2);
        int rollV = Integer.parseInt(rollValue);

        return rollV;
    }

    /** alertWhoseTurn alerts a player when it is their turn
     *
     */
    public void alertWhoseTurn() {
        JOptionPane.showMessageDialog(this, model.getPlayer().getName() + " is at " + model.getBoard().get(model.getPlayer().getLocation()).getName() + " it is now your turn");
    }


    public static void main(String[] args) { new MonopolyFrame();}



    public void saveModel() throws IOException {
        String fileName = JOptionPane.showInputDialog(this, "Enter a file name");

        FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(model);
        objectOutputStream.close();
        fileOutputStream.close();

        handleDisplay();
    }

    public void loadModel() throws IOException, ClassNotFoundException {
        String fileName = JOptionPane.showInputDialog(this, "Enter the file name you wish to load");

        FileInputStream fileInputStream = new FileInputStream(fileName + ".ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        MonopolyModel loadedModel = (MonopolyModel) objectInputStream.readObject();

        this.model = loadedModel;

        ArrayList<String> names = new ArrayList<>();
        for(Player player : model.getPlayers()){
            names.add(player.getName());
        }
        this.displayName = names;
        model.setView(this);

        objectInputStream.close();
        fileInputStream.close();

    }

}