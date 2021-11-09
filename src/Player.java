import java.util.ArrayList;

public class Player {
    private ArrayList<Space> properties;
    private int location;
    private int money;
    private String name;
    private boolean playing;
    private boolean turn;

    /** Constructor for player
     *
     * @param name String, name of the player
     */

    public Player(String name) {
        this.name = name;
        this.properties = new ArrayList<Space>();
        this.money = 1500;
        this.location = 0;
        this.playing = true;
        this.turn = false;

    }

    /** getNoRailroads returns the number of Railroads the player owns
     *
     * @return int, i the number of railroads
     */
    public int getNoRailroads(){
        int i = 0;
        for(Space property: properties){
            if(property.getClass().equals(Railroad.class)){
                i++;
            }
        }
        return i;
    }

    /** getNoUtilities returns the number of Utilities the player owns
     *
     * @return int, i the number of utilities
     */
    public int getNoUtilities(){
        int i = 0;
        for(Space property: properties){
            if(property.getClass().equals(Utilities.class)){
                i++;
            }
        }
        return i;
    }

    /** getName returns the name of the player
     *
     * @return String, name
     */
    public String getName() {
        return name;
    }

    /** getMoney returns how much money the player has
     *
     * @return String, name
     */
    public int getMoney() {
        return money;
    }
    public void addMoney(int payment){
        money += payment;
    }
    public void removeMoney(int payment){
        money -= payment;
    }

    /** setMoney sets the money of the player
     *
     * @param money int, the new amount of money the player has
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /** getProperties returns the properties the player has
     *
     * @return ArrayList<Space>, properties
     */
    public ArrayList<Space> getProperties() {
        return properties;
    }

    /** getLocation returns the location of the player
     *
     * @return int, location
     */
    public int getLocation() {
        return location;
    }

    /** setLocation sets the location of the player
     *
     * @param location int, the new location of the player
     */
    public void setLocation(int location) {
        if (location > 39){
            System.out.println("You passed GO collect $200");
            setMoney(this.money + 200);
            System.out.println("You currently have: " + this.money);
            this.location =location - 39;
        } else {
            this.location = location;
        }
    }

    /** isPlaying returns wether the player is playing or not
     *
     * @return boolean, playing
     */
    public boolean isPlaying() {
        return playing;
    }

    /** setPlaying sets the status of if the player is playing or not
     *
     * @param playing boolean
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /** isTurn returns wether its the players turn or not
     *
     * @return boolean, turn
     */
    public boolean isTurn() {
        return turn;
    }

    /** addProperty adds a property to the players property holdings
     *
     * @param space Space, the space to be added
     */
    public void addProperty(Space space){
        this.properties.add(space);
    }

    /** setTurn sets the state of wether it is the players turn or not
     *
     * @param turn boolean, turn
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /** getLocationGUI returns the array of the player on the grid bassed on their location in the array list
     *
     * @param locationArrayList int, there location in the arraylist
     * @return int, location on the grid
     */
    public int getLocationGUI(int locationArrayList) {
        if (locationArrayList < 11) {
            return locationArrayList;
        } else if (locationArrayList < 20) {
            return (locationArrayList - 10);
        } else if (locationArrayList < 31) {
            int count = 0;
            for(int i = 30; i > locationArrayList; i--){
                count++;
            }
            return (count);
        } else {
            int count = 1;
            for(int i = 39; i > locationArrayList; i--){
                count++;
            }
            return (count);
        }
    }
}
