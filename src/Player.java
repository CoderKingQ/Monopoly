import java.util.ArrayList;

public class Player {
    private ArrayList<Space> properties;
    private int location;
    private int money;
    private String name;
    private boolean playing;
    private boolean turn;

    public Player(String name) {
        this.name = name;
        this.properties = new ArrayList<Space>();
        this.money = 1500;
        this.location = 0;
        this.playing = true;
        this.turn = false;

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

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        if (location + this.location > 39){
            System.out.println("You passed GO collect $200");
            int around = location + this.location;
            this.location = around - 39;
        } else {
            this.location = location;
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isTurn() {
        return turn;
    }

    public void addProperty(Space space){
        this.properties.add(space);
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
