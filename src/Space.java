public class Space {
    private String name;
    private int location;
    private int houses;
    private Player owner;

    /** The constructor for space
     *
     * @param location int, the location of the space
     * @param name String, the name of the String
     */
    public Space(int location, String name){
        this.location = location;
        this.name = name;
        this.owner = null;
        this.houses = 0;
    }

    /** getName returns the name of the Space
     *
     * @return String, name
     */
    public String getName() {
        return name;
    }

    /** getLocation returns the location of the Space
     *
     * @return int, location
     */
    public int getLocation() {
        return location;
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

    /** getHouses returns the number of houses on the space
     *
     * @return int, houses
     */
    public int getHouses() {
        return houses;
    }
}
