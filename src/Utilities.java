import java.io.Serializable;

public class Utilities extends Space implements Serializable {
    private int cost = 150;
    private Player owner;

    //private static final long serialVersionUID = 6222539486241140019L;

    /** The constructor for Utilities
     *
     * @param location int, the location of the utility on the board
     * @param name String, the name of the Utility
     */
    public Utilities(int location, String name) {
        super(location, name);
        this.owner = null;
    }

    /** getCost returns the cost of the Utility
     *
     * @return int, cost
     */
    public int getCost() {
        return cost;
    }

    /** get rent returns the rent due based on the number of Utilities the owner has
     * and the current roll of the dice
     *
     * @return int, cost
     */
    public int getRent(Dice die){
        int utilities = getOwner().getNoUtilities();
        if (utilities == 1){
            return 4 * die.getCurrentRoll();
        } else return 10 * die.getCurrentRoll();
    }

    /** getOwner returns the player that owns that space
     *
     * @return Player, owner
     */
    public Player getOwner() {
        return owner;
    }

    /** setOwner sets the owner of the space
     *
     * @param owner Player, the player to own the space
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * A toString method to display the name, cost and owner of a utility
     * @return
     */
    @Override
    public String toString() {
        return "Utilities{" + this.getName() +
                "cost=" + cost +
                ", owner=" + owner.getName() +
                '}';
    }
}
