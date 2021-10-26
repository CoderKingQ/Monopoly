import java.util.Random;

public class Dice {
    private int currentRoll;
    private boolean doubles;
    private int doubleCount = 0;

    /** rollDie generates and returns a random number from 1-6 inclusive
     *
     * @return int, rn
     */
    public int rollDie(){
        Random rn = new Random();
        return rn.nextInt(6) + 1; // Rolling a single dice
    }

    /** roll rolls two dice summing the results and checking if it rolled doubles
     *
     * @return int, getCurrentRoll()
     */
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

    /** getCurrentRoll returns the current roll of the dice
     *
     * @return int, currentRoll
     */
    public int getCurrentRoll() {
        return currentRoll;
    }

    /** isDoubles checks whether the dice is currently doubles
     *
     * @return boolean, doubles
     */
    public boolean isDoubles() {
        return doubles;
    }

    /** getDoubleCount returns the amount of doubles that have been rolled for the current players turn
     *
     * @return int, doubleCount
     */
    public int getDoubleCount() {
        return doubleCount;
    }

    /** resets the number of doubles back to zero for the next player
     *
     */
    public void resetDoubles(){
        doubleCount = 0;
    }

}
