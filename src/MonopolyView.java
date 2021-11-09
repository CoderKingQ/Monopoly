import java.util.ArrayList;

public interface MonopolyView {
    void handleDisplayChar(int curPlayer, int location, int locationGUI);
    void handleDisplay();
    boolean handleBuyProperty(Space space);

    void handlePayEvent(Space space);

    void handleStatus(StringBuilder status);

    void handlePayPlayer(Player p1, Player p2, int payment);

    void declareWinner();

    void declareBankruptPlayer();

}
