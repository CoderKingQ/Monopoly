import java.io.Serializable;

public class Event extends Space implements Serializable {
    private int payment;

    private static final long serialVersionUID = 8197539486241140019L;

    /** The constructor for Event
     *
     * @param location int, the location for the even space on the board
     * @param name String, the name of the event space
     * @param payment int, the amount needed to pay upon landing on the event space
     */
    public Event(int location, String name, int payment) {
        super(location, name);
        this.payment = payment;
    }

    /** getPayment returns the amount needed to be payed
     *
     * @return int, payment
     */
    public int getPayment() {
        return payment;
    }
}
