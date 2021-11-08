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
        int moneyO = model.getPlayers().get(1).getMoney();
        model.getPlayer().setLocation(8);
        int cost = ((Property) model.getBoard().get(model.getPlayers().get(0).getLocation())).getCost();
        model.buyProperty();
        assertEquals(model.getPlayer(),((Property) model.getBoard().get(model.getPlayer().getLocation())).getOwner()); //checking ownership
        assertEquals(moneyO - cost, model.getPlayer().getMoney()); // checking money change
    }
    public void testBuyUtility(){
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players);
        int moneyO = model.getPlayers().get(1).getMoney();
        int moneyR = model.getPlayers().get(0).getMoney();
        model.getPlayer().setLocation(12);
        model.buyUtilities();
        assertEquals(model.getPlayer(),((Utilities) model.getBoard().get(model.getPlayer().getLocation())).getOwner()); //tests for ownership
        //add test for money transfers
    }
    public void testRentProperty(){
        int rent;
        die = new Dice();
        die.setDie(10);
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players);
        model.getPlayer().setLocation(8);
        int moneyO = model.getPlayers().get(1).getMoney();
        int moneyR = model.getPlayers().get(0).getMoney();
        ((Property) model.getBoard().get(model.getPlayer().getLocation())).setOwner(model.getPlayers().get(1));
        System.out.println(moneyR);
        System.out.println(moneyO);
        rent = ((Property) model.getBoard().get(model.getPlayers().get(0).getLocation())).getRent();

        model.payPropertyRent();

        assertEquals(moneyO + rent, model.getPlayers().get(1).getMoney());
        assertEquals(moneyR - rent, model.getPlayer().getMoney());





    }
    public void testRentUtility(){
        int rent;
        die = new Dice();
        die.setDie(10);
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players);
        int moneyO = model.getPlayers().get(1).getMoney();
        int moneyR = model.getPlayers().get(0).getMoney();
        System.out.println(moneyR);
        System.out.println(moneyO);
        model.getPlayer().setLocation(12);
        ((Utilities) model.getBoard().get(model.getPlayer().getLocation())).setOwner(model.getPlayers().get(1));
        System.out.println(((Utilities)model.getBoard().get(model.getPlayer().getLocation())).getOwner());
        rent = ((Utilities) model.getBoard().get(model.getPlayers().get(0).getLocation())).getRent(die);
        System.out.println(rent);
        model.payUtilitiesRent();
        System.out.println(moneyR);
        System.out.println(moneyO);
        System.out.println(model.currentPlayer);
        assertEquals(moneyR - rent,model.getPlayer().getMoney());
        assertEquals(moneyO + rent, model.getPlayers().get(1).getMoney() );
        System.out.println(moneyR);
        System.out.println(moneyO);

    }
}
