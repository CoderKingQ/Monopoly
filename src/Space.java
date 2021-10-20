public class Space {
    private String name;
    private int location;
    private int houses;
    private Player owner;


    public Space(int location, String name){
        this.location = location;
        this.name = name;
        this.owner = null;
        this.houses = 0;
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getHouses() {
        return houses;
    }
}
