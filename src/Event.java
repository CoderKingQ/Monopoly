public class Event extends Space{
    private int payment;
    public Event(int location, String name, int payment) {
        super(location, name);
        this.payment = payment;
    }

    public int getPayment() {
        return payment;
    }
}
