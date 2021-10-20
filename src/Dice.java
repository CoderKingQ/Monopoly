import java.util.Random;

public class Dice {
    private int currentRoll;
    private boolean doubles;

    public int rollDie(){
        Random rn = new Random();
        return rn.nextInt(6) + 1; // Rolling a single dice
    }

    public int roll(){
        doubles = false;
        int d1 = rollDie();
        int d2 = rollDie();
        if (d1 == d2) doubles = true;
        currentRoll = d1 + d2;
        return getCurrentRoll();
    }

    public int getCurrentRoll() {
        return currentRoll;
    }

    public boolean isDoubles() {
        return doubles;
    }
}
