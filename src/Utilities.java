public class Utilities extends Space{
    private int cost = 150;
    private Player owner;

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
}
