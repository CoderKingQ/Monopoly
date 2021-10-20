import java.util.ArrayList;

public class Player {
    private ArrayList<Space> properties;
    private int location;
    private int money;
    private String name;

    public Player(String name) {
        this.name = name;
        this.properties = new ArrayList<Space>();
        this.money = 1500;
        location = 0;
    }

    //Get number of railroads
    public int getNoRailroads(){
        int i = 0;
        for(Space property: properties){
            if(property.getClass().equals(Railroad.class)){
                i++;
            }
        }
        return i;
    }

    //Get number of Utilities
    public int getNoUtilities(){
        int i = 0;
        for(Space property: properties){
            if(property.getClass().equals(Utilities.class)){
                i++;
            }
        }
        return i;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Space> getProperties() {
        return properties;
    }
}
