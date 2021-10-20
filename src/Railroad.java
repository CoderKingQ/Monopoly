public class Railroad extends Space{
    private int cost = 200;

    public Railroad(int location, String name) {
        super(location, name);
    }

    public int getCost() {
        return cost;
    }

    public int getRent() {
        int railroads = getOwner().getNoRailroads();
        if(railroads == 1){
            return 25;
        } else if(railroads == 2){
            return 50;
        } else if(railroads == 3){
            return 100;
        } else return 200;
    }

}
