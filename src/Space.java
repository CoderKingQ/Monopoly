import java.io.Serializable;

public class Space implements Serializable {
    private String name;
    private int location;

    //private static final long serialVersionUID = 6267539486245980019L;

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

    /**
     * A toString method to display the name and location of a space
     * @return
     */
    @Override
    public String toString() {
        return "Space{" +
                "name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
