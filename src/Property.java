
public class Property extends Space{

    private String set;
    private int cost;
    private int rent;
    private int houses;
    private Player owner;

    /** The constructor for property
     *
     * @param location int, the property's location on the board
     * @param name String, the name of the property
     * @param set String, what set the property is a part of
     * @param cost int, the cost of the property
     * @param rent int, the initial rent of the property
     */
    public Property(int location, String name, String set, int cost, int rent) {
        super(location, name);
        this.cost = cost;
        this.set = set;
        this.rent = rent;
        this.houses = 0;
        this.owner = null;
    }

    /** getSet returns the set that the property is apart of
     *
     * @return String, set
     */
    public String getSet() {
        return set;
    }

    /** getCost returns the cost of the property
     *
     * @return int, cost
     */
    public int getCost() {
        return cost;
    }

    /** getRent returns the current rent cost for landing on the property
     *
     * @return int, rent
     */
    public int getRent() {
        return rent + (rent/2) * houses;
    }

    /** Adds houses to the current property
     *
     * @param houses int, number of houses to be added
     * @return boolean, if the houses could be added or not
     */
    public boolean addHouse(int houses){
        //max 5 houses so make sure they don't add more than allowed
        if(this.houses + houses >= 5){
            this.houses += houses;
            return true;
        } else return false;
    }

    /** getHouses returns the number of houses on the property
     *
     * @return int, houses
     */
    public int getHouses() {
        return houses;
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
