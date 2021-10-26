import java.util.Random;

/** This class represents the dice used in the game
 *
 */
public class Dice {
    private int currentRoll;
    private boolean doubles;
    private int doubleCount = 0;

    /** Rolls the dice
     *
     * @return int
     */
    public int rollDie(){
        Random rn = new Random();
        return rn.nextInt(6) + 1; // Rolling a single dice
    }

    public int roll(){
        doubles = false;
        int d1 = rollDie();
        int d2 = rollDie();
        if (d1 == d2){
            doubles = true;
            doubleCount++;
        }
        System.out.println(d1);
        System.out.println(d2);
        currentRoll = d1 + d2;
        return getCurrentRoll();
    }

    /**Returns currentRoll
     *
     * @return int
     */
    public int getCurrentRoll() {
        return currentRoll;
    }

    /** Returns if we have a double or not
     *
     * @return  boolean
     */
    public boolean isDoubles() {
        return doubles;
    }

    /**
     *
     * @return int
     */
    public int getDoubleCount() {
        return doubleCount;
    }

}
