import javax.swing.*;
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
            JOptionPane.showMessageDialog(null, model.getPlayer().getName() + " is at " + model.getBoard().get(model.getPlayer().getLocation()).getName() + " it is now your turn");

        }

        if(button.equals("status")){
            model.status();
        }

        if(button.equals("houses")){

        }
    }
}
