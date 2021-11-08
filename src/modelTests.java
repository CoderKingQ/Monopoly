import junit.framework.*;

import java.util.ArrayList;

public class modelTests extends TestCase {
    //variable declarations
    private ArrayList<String> players;
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
     MonopolyModel model = new MonopolyModel(players);
     model.getPlayer().setLocation(5);
     model.buyRailroad();
     assertEquals(model.getPlayer(),((Railroad) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
     }

    public void testBuyProperty(){
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        MonopolyModel model = new MonopolyModel(players);
        model.getPlayer().setLocation(8);
        model.buyProperty();
        assertEquals(model.getPlayer(),((Property) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
    }
    public void testBuyUtility(){
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        MonopolyModel model = new MonopolyModel(players);
        model.getPlayer().setLocation(12);
        model.buyUtilities();
        assertEquals(model.getPlayer(),((Utilities) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
    }

}
