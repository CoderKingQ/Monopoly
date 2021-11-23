

import junit.framework.*;

import java.util.ArrayList;

public class modelTests extends TestCase {
    //variable declarations
    private ArrayList<String> players;
    private ArrayList<Integer> aiNumber;
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
     aiNumber = new ArrayList<>();
     aiNumber.add(0);
     aiNumber.add(0);
     model = new MonopolyModel(players, aiNumber);
     model.getPlayer().setLocation(5);
     int moneyO = model.getPlayer().getMoney();
     int cost = ((Railroad) model.getBoard().get(model.getPlayer().getLocation())).getCost();
     model.buyRailroad();
     assertEquals(model.getPlayer(),((Railroad) model.getBoard().get(model.getPlayer().getLocation())).getOwner());
     assertEquals(moneyO - cost, model.getPlayer().getMoney());
     }

    public void testBuyProperty(){
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        aiNumber = new ArrayList<>();
        aiNumber.add(0);
        aiNumber.add(0);
        model = new MonopolyModel(players, aiNumber);
        int moneyO = model.getPlayer().getMoney();
        model.getPlayer().setLocation(8);
        int cost = ((Property) model.getBoard().get(model.getPlayer().getLocation())).getCost();
        model.buyProperty();
        assertEquals(model.getPlayer(),((Property) model.getBoard().get(model.getPlayer().getLocation())).getOwner()); //checking ownership
        assertEquals(moneyO - cost, model.getPlayer().getMoney()); // checking money change
    }
    public void testBuyUtility(){
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        aiNumber = new ArrayList<>();
        aiNumber.add(0);
        aiNumber.add(0);
        model = new MonopolyModel(players, aiNumber);
        int moneyO = model.getPlayers().get(1).getMoney();
        model.getPlayer().setLocation(12);
        model.buyUtilities();
        int cost = (((Utilities) model.getBoard().get(model.getPlayer().getLocation())).getCost());
        Player owner = ((Utilities) model.getBoard().get(model.getPlayer().getLocation())).getOwner();
        assertEquals(model.getPlayer(),owner); //tests for ownership
        assertEquals(moneyO - cost,model.getPlayer().getMoney() );
    }


    public void testRentProperty(){
        int rent;
        die = new Dice();
        die.setDie(10);
        aiNumber = new ArrayList<>();
        aiNumber.add(0);
        aiNumber.add(0);
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players, aiNumber);
        model.getPlayer().setLocation(8);
        int moneyO = model.getPlayers().get(1).getMoney();
        int moneyR = model.getPlayers().get(0).getMoney();
        ((Property) model.getBoard().get(model.getPlayer().getLocation())).setOwner(model.getPlayers().get(1));

        rent = ((Property) model.getBoard().get(model.getPlayers().get(0).getLocation())).getRent();
        model.payPropertyRent();

        assertEquals(moneyO + rent, model.getPlayers().get(1).getMoney()); // Owner received money
        assertEquals(moneyR - rent, model.getPlayer().getMoney()); //current player gave money





    }
    public void testRentUtility(){
        int rent;
        die = new Dice();
        die.roll();
        aiNumber = new ArrayList<>();
        aiNumber.add(0);
        aiNumber.add(0);
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players, aiNumber);
        int moneyO = model.getPlayers().get(1).getMoney();
        int moneyR = model.getPlayers().get(0).getMoney();
        model.getPlayer().setLocation(12);

        ((Utilities) model.getBoard().get(model.getPlayer().getLocation())).setOwner(model.getPlayers().get(1));
        rent = ((Utilities) model.getBoard().get(model.getPlayer().getLocation())).getRent(die);

        model.payUtilitiesRent(die);

        assertEquals(moneyR - rent,model.getPlayer().getMoney());
        assertEquals(moneyO + rent, model.getPlayers().get(1).getMoney() );
    }
    public void testRentRailroad(){
        int rent;
        aiNumber = new ArrayList<>();
        aiNumber.add(0);
        aiNumber.add(0);
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Raul");
        model = new MonopolyModel(players, aiNumber);

        model.getPlayer().setLocation(5);
        ((Railroad) model.getBoard().get(model.getPlayer().getLocation())).setOwner(model.getPlayers().get(1));
        rent = ((Railroad) model.getBoard().get(model.getPlayers().get(0).getLocation())).getRent();



        int moneyO = model.getPlayers().get(1).getMoney();
        int moneyR = model.getPlayers().get(0).getMoney();

        model.payRailroadRent();

        assertEquals(moneyO + rent, model.getPlayers().get(1).getMoney());

    }
    public void testAiBuyHouses(){
        aiNumber = new ArrayList<>();
        aiNumber.add(0);
        aiNumber.add(1);
        players = new ArrayList<String>();
        players.add("Paul");
        players.add("Bot");

        model = new MonopolyModel(players, aiNumber);
        model.nextTurn();
        ((Property) model.getBoard().get(3)).setOwner(model.getPlayers().get(0));
        ((Property) model.getBoard().get(1)).setOwner(model.getPlayers().get(0));
        model.getPlayer().addProperty((model.getBoard().get(3)));
        model.getPlayer().addProperty((model.getBoard().get(1)));
        model.getPlayer().setLocation(3);
        model.handleAIHouses();


        assertEquals(1,((Property) model.getBoard().get(3)).getHouses() );

    }
}

