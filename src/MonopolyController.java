import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonopolyController implements ActionListener {

    MonopolyModel model;

    public MonopolyController(MonopolyModel model){this.model = model;}


    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();

        if(button.equals("roll")){
            model.roll();

        }

        if(button.equals("buy")){
            model.buyProperty();
        }

        if(button.equals("status")){
            //MonopolyFrame.printStatus();
        }

        if(button.equals("houses")){

        }
    }
}
