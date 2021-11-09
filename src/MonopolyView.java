public interface MonopolyView {
    void handleRoll( Player player);
    void handleDisplayChar(int curPlayer, int location, int locationGUI);
    void handleDisplay();
    boolean handleBuyProperty(Space space);

}
