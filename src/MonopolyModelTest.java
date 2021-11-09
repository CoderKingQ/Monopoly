import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MonopolyModelTest {

    private MonopolyModel model;
    private ArrayList<String> players;

    @Before
    public void setUp() throws Exception {
        players = new ArrayList<>();
        players.add("Mike");
        players.add("John");
        players.add("Ese");
        players.add("Iyamu");
        model = new MonopolyModel(players);
    }

    @Test
    public void testGetBoard() {
       //gameBoard  generateBoard();
    }

    @Test
    public void testPlay() {

    }

    @Test
    public void testRoll() {
        int[] rolledValue = new int[2];
        //rolledValue = model.get;
    }

    @Test
    public void testStatus() {

    }

    @Test
    public void testPayPlayer() {
        model.getPlayer().setLocation(8);
        model.buyProperty();
    }

    @Test
    public void testPayEvent() {
        model.getPlayer().setLocation(4);
        int location = model.getPlayer().getLocation();

        //Get money of current player
        int playerMoney = model.getPlayer().getMoney();

        //get amount to be removed
        int amountToBeRemoved = ((Event) model.getBoard().get(model.getPlayer().getLocation())).getPayment();

        //Update players money
        int actualBalance = playerMoney - amountToBeRemoved;
        int expectedBalance = 1300;

       assertEquals(expectedBalance,actualBalance);

    }

    @Test
    public void testPayPropertyRent(){
        model.getPlayer().setLocation(6);
        int location = model.getPlayer().getLocation();

        //Get money of current player
        int playerMoney = model.getPlayer().getMoney();

        //get amount to be removed
        int amountToBeRemoved = ((Property) model.getBoard().get(model.getPlayer().getLocation())).getRent();

        //Update players money
        int actualBalance = playerMoney - amountToBeRemoved;
        int expectedBalance = 1494;

        assertEquals(expectedBalance,actualBalance);
    }

   /* @Test
    public void testPayRailroadRent(){
        model.getPlayer().setLocation(5);
        model.buyRailroad();
        model.get(currentPlayer).isTurn() == false;

        int location = model.getPlayer().getLocation();

        //Get money of current player
        int playerMoney = model.getPlayer().getMoney();

        //get amount to be removed
        int amountToBeRemoved = ((Railroad) model.getBoard().get(model.getPlayer().getLocation())).getRent();

        //Update players money
        int actualBalance = playerMoney - amountToBeRemoved;
        int expectedBalance = 1494;

        assertEquals(expectedBalance,actualBalance);
    }
*/
    @Test
    public void testAddMonopolyView() {
    }

    @Test
    public void testRemoveMonopolyView() {
    }

    @Test
    public void testGenerateBoard() {
    }

    @Test
    public void testGetPlayers() {
        ArrayList<String> playersInGame = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            playersInGame.add(players.get(i));
        }
        assertEquals(players, model.getPlayers());
    }

    @Test
    public void testGetCurrentPlayer() {
        //Mike is the first player in the array list. Since the game has not started, the current player should be the first person in the
        // list. model.getPlayers.get(1) returns mike as the player. model.getCurrentPlayer() is meant to return mike as well.
        assertEquals(model.getPlayers().get(0), model.getCurrentPlayer());
    }

    @Test
    public void testBuyUtilities() {
        model.getPlayer().setLocation(12);
        model.buyUtilities();
        assertEquals(model.getPlayer(),((Utilities) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
    }

    @Test
    public void testBuyRailroad() {
        model.getPlayer().setLocation(5);
        model.buyRailroad();
        assertEquals(model.getPlayer(),((Railroad) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
    }

    @Test
    public void testBuyProperty() {
        model.getPlayer().setLocation(8);
        model.buyProperty();
        assertEquals(model.getPlayer(),((Property) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
    }

}