public class Space {
    private String name;
    private int location;

    /** The constructor for space
     *
     * @param location int, the location of the space
     * @param name String, the name of the String
     */
    public Space(int location, String name){
        this.location = location;
        this.name = name;
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


}
