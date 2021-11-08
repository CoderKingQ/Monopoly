import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MonopolyModel {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private boolean gameNotOver;
    private Dice die;
    private Player winner;

    private boolean buyable;

    public int currentPlayer;

    private List<MonopolyView> views;

    /** main to run the game
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
        die.roll();

        //double handling
        if(die.getDoubleCount() > 2){
            players.get(currentPlayer).setLocation(10); // jail location - to be implemented lock in jail for 3 turns
            players.get(currentPlayer).setTurn(false);
        }


        if(players.get(currentPlayer).isTurn()){
            players.get(currentPlayer).setLocation(players.get(currentPlayer).getLocation() + die.getCurrentRoll());


            //check if property is purchasable
            buyable = false;
            if(board.get(players.get(currentPlayer).getLocation()).getOwner() == null && ((board.get(players.get(currentPlayer).getLocation()) instanceof Property) ||(board.get(players.get(currentPlayer).getLocation()) instanceof Railroad)||(board.get(players.get(currentPlayer).getLocation()) instanceof Utilities))){
                buyable = views.get(0).handleBuyProperty(board.get(players.get(currentPlayer).getLocation()));
            }
            if(buyable){
                buyProperty();
            }

        }else if(players.get(currentPlayer).isTurn() == false) {
            nextTurn();
            die.resetDoubles();
        }
    }

    /** combine all player info into a string to send to frame
     *
     * @return
     */
    public String status(){
        String allinfo = "";

        return allinfo;
    }

    /** get yes or no from frame and do buying operations
     *
     */
    public void buyProperty(){
        players.get(currentPlayer).addProperty(board.get(players.get(currentPlayer).getLocation()));
    }

    /**
     *
     */
    public void payPlayer(){

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

    public boolean isBuyable() {
        return buyable;
    }
}