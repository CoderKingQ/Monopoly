public class Utilities extends Space{
    private int cost = 150;

    public Utilities(int location, String name) {
        super(location, name);
    }

    public int getCost() {
        return cost;
    }

    public int getRent(Dice die){
        int utilities = getOwner().getNoUtilities();
        if (utilities == 1){
            return 4 * die.getCurrentRoll();
        } else return 10 * die.getCurrentRoll();
    }
}
