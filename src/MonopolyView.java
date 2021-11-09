import java.util.ArrayList;

public interface MonopolyView {
    void handleRoll( Player player);

    boolean handleBuyProperty(Space space);

    void handlePayEvent(Space space);

    void handleStatus(StringBuilder status);

    void handlePayPlayer(Player p1, Player p2, int payment);

}
