import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MonopolyModel {
    private ArrayList<Space> board;
    private ArrayList<Player> players;
    private boolean gameNotOver;
    private Dice die;
    private Player winner;

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
    }

    /** how a person will play their turn
     *
     */
    public void play(int player) {



    }

    /** how a person will play their turn
     *
     */
    public void roll(int player) {
        die.roll();
        if(die.getDoubleCount() > 2){
            //System.out.println("Oh no you rolled three doubles go to jail!");
            players.get(player).setLocation(10); // jail location - to be implemented lock in jail for 3 turns
            players.get(player).setTurn(false);
        }

        if(players.get(player).isTurn()){
            players.get(player).setLocation(players.get(player).getLocation() + die.getCurrentRoll());
            if(board.get(players.get(player).getLocation()).getOwner() == null && ((board.get(players.get(player).getLocation()) instanceof Property) ||(board.get(players.get(player).getLocation()) instanceof Railroad)||(board.get(players.get(player).getLocation()) instanceof Utilities))){

            }
        }else if(players.get(player).isTurn() == false) {
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
}