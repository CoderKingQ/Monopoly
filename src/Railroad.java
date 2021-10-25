public class Railroad extends Space{
    private int cost = 200;

    /** The constructor for Railroad
     *
     * @param location int, the location of the railroad on the board
     * @param name String, the name of the railroad
     */
    public Railroad(int location, String name) {
        super(location, name);
    }

    /** getCost returns the cost of the Railroad
     *
     * @return int, cost
     */
    public int getCost() {
        return cost;
    }

    /** get rent returns the rent due based on the number of railroads the owner has
     *
     * @return int, cost
     */
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
