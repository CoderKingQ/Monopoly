import java.util.EventObject;

public class MonopoloyEvent extends EventObject {
    private Player player;

    public MonopoloyEvent(MonopolyModel model, Player player) {
        super(model);

        this.player= player;
    }
}
