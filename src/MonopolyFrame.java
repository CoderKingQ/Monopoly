import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MonopolyFrame extends JFrame implements MonopolyView{
    private final int BoardLength = 10;

    MonopolyModel model;

    private JPanel top;
    private JPanel bot;


    public MonopolyFrame(){
        super("Monopoly 0.5");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,900);

        //Set up number of players;
        String NOplayers = "1";
        while(Integer.parseInt( NOplayers) < 2 || Integer.parseInt( NOplayers) > 8 ) {
            NOplayers = JOptionPane.showInputDialog(this, "Enter a number of players (2-8): ", 2);
        }

        //Enter name of each player
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < Integer.parseInt( NOplayers); i++){
            String name = JOptionPane.showInputDialog(this, "Enter name of Player " + (i + 1) + ":" );
            names.add(name);
        }

        model = new MonopolyModel(names);
        model.addMonopolyView(this);

        top = new JPanel(new GridLayout(BoardLength, BoardLength));
        bot = new JPanel(new GridLayout(1,5));
        this.add(top, BorderLayout.CENTER);
        this.add(bot, BorderLayout.PAGE_END);

        MonopolyController mc = new MonopolyController(model);

        //buttons
        JButton rollB = new JButton("Roll");
        rollB.setActionCommand("roll");
        rollB.addActionListener(mc);

        JButton housesB = new JButton("Buy House");
        housesB.setActionCommand("houses");
        housesB.addActionListener(mc);

        JButton statusB = new JButton("Status");
        statusB.setActionCommand("status");
        statusB.addActionListener(mc);
        bot.add(rollB);
        bot.add(housesB);
        bot.add(new JPanel());
        bot.add(new JPanel());
        bot.add(statusB);

        this.setVisible(true);
    }

    public void printStatus() {
        //combine strings of player messages and output to the jpane dialog box
        JOptionPane.showMessageDialog(this, "status message");
    }

    public void handleRoll(Game game, Player player) {

    }

    public static void main(String[] args) { new MonopolyFrame();}
}
