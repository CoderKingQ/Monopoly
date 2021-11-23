import java.util.ArrayList;

/*
How to get to jail, location (10)
1) land on go to jail (30):
2) Roll double 3ice,
when player gets go to jail or roll double dice 3 times,
player should go to jail and be disabled to miss 3 turns.
player could also pay a fine and join in the next round.
to disable player, player.getplayer(currentPlayer).setTurn(false)
 */

public class Jail extends Space{
    private ArrayList<Player> prisoner;
    private int fine;

    public Jail(int location, String name, int fine)
    {
        super(location, name);
        this.fine = fine;
    }
    public void addPrisoner(Player player){
        prisoner.add(player);
    }

    public void removePrisoner(Player player){
        prisoner.remove(player);
    }

    public int getFine() {
        return fine;
    }

}




