import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MonopolyFrame extends JFrame implements MonopolyView{
    private final int BoardLength = 11;

    MonopolyModel model;

    private JPanel top;
    private JPanel bot;
    private JPanel[][] spaces;

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
        JPanel prop = new JPanel(new GridLayout(2, 0));
        this.add(top, BorderLayout.CENTER);
        this.add(bot, BorderLayout.PAGE_END);

        MonopolyController mc = new MonopolyController(model);

        spaces = new JPanel[BoardLength][BoardLength];

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
        //add grab color

        //loop and create the panels in a grid
        for (int i = 0; i < BoardLength; i++) {
            for (int j = 0; j < BoardLength; j++) {
                JPanel space = new JPanel();
                spaces[i][j] = space;
                space.add(prop);
                top.add(space);
            }
        }
        //for first row
        for(int j = 0; j < BoardLength; j++){
            String temp = model.getBoard().get(j).getName();
            JLabel pName = new JLabel(temp);
            pName.setFont(new Font("Verdana",1,11));
            spaces[0][j].add(pName);
            if(model.getBoard().get(j) instanceof Event){
                if(((Event) model.getBoard().get(j)).getPayment() != 0){
                    JLabel pay = new JLabel("Payment: " + ((Event) model.getBoard().get(j)).getPayment());
                    pay.setFont(new Font("Verdana",1,10));
                    spaces[0][j].add(pay);
                }

            }


            if(model.getBoard().get(j) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200");
                JLabel price = new JLabel("Price: 200");
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[0][j].add(rent1);
                spaces[0][j].add(price);
            }

            if(model.getBoard().get(j) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(j)).getCost()));
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(j)).getRent()));
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[0][j].add(rent);
                spaces[0][j].add(price);
                spaces[0][j].setBackground(Color.decode(((Property) model.getBoard().get(j)).getSet()));


            }
            spaces[0][j].setBorder(BorderFactory.createLineBorder(Color.black));
        }

        int count2 = 30;
        //for last row
        for(int j = 0; j < BoardLength; j++){
            String temp = model.getBoard().get(count2).getName();
            JLabel pName = new JLabel(temp);
            pName.setFont(new Font("Verdana",1,11));
            spaces[10][j].add(pName);


            if(model.getBoard().get(count2) instanceof Utilities){
                JLabel rent1 = new JLabel("Dice Multiplier: 4x, 10x");
                JLabel price = new JLabel("Price: 150");
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[10][j].add(rent1);
                spaces[10][j].add(price);
            }

            if(model.getBoard().get(count2) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200");
                JLabel price = new JLabel("Price: 200");
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[10][j].add(rent1);
                spaces[10][j].add(price);
            }

            if(model.getBoard().get(count2) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(count2)).getCost()));
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(count2)).getRent()));
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[10][j].add(rent);
                spaces[10][j].add(price);
                spaces[10][j].setBackground(Color.decode(((Property) model.getBoard().get(count2)).getSet()));
            }
            spaces[10][j].setBorder(BorderFactory.createLineBorder(Color.black));
            count2--;
        }

        int count3 = 39;
        //for first column
        for(int i = 1; i < (BoardLength-1); i++){
            String temp = model.getBoard().get(count3).getName();
            JLabel pName = new JLabel(temp);
            pName.setFont(new Font("Verdana",1,11));
            spaces[i][0].add(pName);

            if(model.getBoard().get(count3) instanceof Event){
                if(((Event) model.getBoard().get(count3)).getPayment() != 0){
                    JLabel pay = new JLabel("Payment: " + ((Event) model.getBoard().get(count3)).getPayment());
                    pay.setFont(new Font("Verdana",1,10));
                    spaces[i][0].add(pay);
                }

            }

            if(model.getBoard().get(count3) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200");
                JLabel price = new JLabel("Price: 200");
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][0].add(rent1);
                spaces[i][0].add(price);
            }

            if(model.getBoard().get(count3) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(count3)).getCost()));
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(count3)).getRent()));
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][0].add(rent);
                spaces[i][0].add(price);
                spaces[i][0].setBackground(Color.decode(((Property) model.getBoard().get(count3)).getSet()));
            }
            spaces[i][0].setBorder(BorderFactory.createLineBorder(Color.black));
            count3--;

        }
        //for second column
        int count = 11;
        for(int i = 1; i < (BoardLength-1); i++){
            String temp = model.getBoard().get(count).getName();
            JLabel a = new JLabel("<html>" + temp + "</html>");
            a.setFont(new Font("Verdana",1,11));
            spaces[i][10].add(a);
            if(model.getBoard().get(count) instanceof Utilities){
                JLabel rent1 = new JLabel("Dice Multiplier: 4x, 10x");
                JLabel price = new JLabel("Price: 150");
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][10].add(rent1);
                spaces[i][10].add(price);
            }


            if(model.getBoard().get(count) instanceof Railroad){
                JLabel rent1 = new JLabel("Rent: 25, 50, 100, 200");
                JLabel price = new JLabel("Price: 200");
                rent1.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][10].add(rent1);
                spaces[i][10].add(price);
            }

            if(model.getBoard().get(count) instanceof Property){
                JLabel price = new JLabel("Price:  "+ String.valueOf(((Property) model.getBoard().get(count)).getCost()));
                JLabel rent = new JLabel("Rent: "+ String.valueOf(((Property) model.getBoard().get(count)).getRent()));
                rent.setFont(new Font("Verdana",1,10));
                price.setFont(new Font("Verdana",1,10));
                spaces[i][10].add(rent);
                spaces[i][10].add(price);
                spaces[i][10].setBackground(Color.decode(((Property) model.getBoard().get(count)).getSet()));
            }
            spaces[i][10].setBorder(BorderFactory.createLineBorder(Color.black));
            count++;
        }


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