
public class Property extends Space{

    private String set;
    private int cost;
    private int rent;
    private int houses;


    public Property(int location, String name, String set, int cost, int rent) {
        super(location, name);
        this.cost = cost;
        this.set = set;
        this.rent = rent;
        this.houses = 0;
    }

    public String getSet() {
        return set;
    }

    public int getCost() {
        return cost;
    }

    public int getRent() {
        return rent + (rent/2) * houses;
    }

    public boolean addHouse(int houses){
        //max 5 houses so make sure they don't add more than allowed
        if(this.houses + houses >= 5){
            this.houses += houses;
            return true;
        } else return false;
    }

    public int getHouses() {
        return houses;
    }
}
