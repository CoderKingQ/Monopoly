import junit.framework.*;

import java.util.ArrayList;

public class modelTests extends TestCase {
    //variable declarations
    private ArrayList<String> players;
    private Dice die;
    MonopolyModel model;
    //optional fixtures

    //testcases
/*
     public void testRoll(){
         players = new ArrayList<String>();
         players.add("Paul");
         players.add("Raul");
     MonopolyModel model = new MonopolyModel(players);

     }
*/
     public void testBuyRailroad(){
         players = new ArrayList<String>();
         players.add("Paul");
         players.add("Raul");
     model = new MonopolyModel(players);
     model.getPlayer().setLocation(5);
     model.buyRailroad();
     assertEquals(model.getPlayer(),((Railroad) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
     }

    public void testBuyProperty(){
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players);
        model.getPlayer().setLocation(8);
        model.buyProperty();
        assertEquals(model.getPlayer(),((Property) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
    }
    public void testBuyUtility(){
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players);
        model.getPlayer().setLocation(12);
        model.buyUtilities();
        assertEquals(model.getPlayer(),((Utilities) model.getBoard().get(model.getPlayer().getLocation())).getOwner()); //tests for ownership
        //add test for money transfers
    }
    public void testRentUtility(){
        int rent =0;
        die = new Dice();
        die.setDie(10);
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players);
        int moneyO = model.getPlayers().get(1).getMoney();
        int moneyR = model.getPlayers().get(0).getMoney();
        if(model.getBoard().get(model.getPlayers().get(0).getLocation()) instanceof Utilities) {
             rent = ((Utilities) model.getBoard().get(model.getPlayers().get(0).getLocation())).getRent(die);
         }
        model.getPlayers().get(0).setLocation(12);
        ((Utilities) model.getBoard().get(model.getPlayer().getLocation())).setOwner(model.getPlayers().get(1));
        model.payUtilitiesRent();
        assertEquals(moneyO + 10*rent, model.getPlayers().get(1).getMoney() );
        assertEquals(model.getPlayers().get(0).getMoney(),moneyR - 10*rent);

    }
}
