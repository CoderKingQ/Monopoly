import java.io.FileNotFoundException;
import java.io.IOException;

public interface MonopolyView {
    boolean handleJ();
    void handleDisplayChar(int curPlayer, int location, int locationGUI);
    void handleDisplay();
    boolean handleBuyProperty(Space space);

    void handlePayEvent(Space space);

    void handleStatus(StringBuilder status);

    void handlePayPlayer(Player p1, Player p2, int payment);

    void declareWinner();

    void declareBankruptPlayer();

    String propertyToAddHouses();

    void housesAdded(boolean housesAdded);

    int modRollValue();

    void alertWhoseTurn();

    void loadModel() throws IOException, ClassNotFoundException;

    void saveModel() throws IOException;

}
