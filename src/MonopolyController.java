import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonopolyController implements ActionListener {

    MonopolyModel model;

    /** MonopolyController is the constructor for the class
     *
     * @param model MonopolyModel, the model to be controlled
     */
    public MonopolyController(MonopolyModel model){this.model = model;}


    /** triggers on button press and handles input
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();

        if(button.equals("roll")){
            model.roll();
            JOptionPane.showMessageDialog(null, model.getPlayer().getName() + " is at " + model.getBoard().get(model.getPlayer().getLocation()).getName() + " it is now your turn");

        }

        if(button.equals("status")){
            //JOptionPane.showMessageDialog(null, model.status());
            model.status();
        }

        if(button.equals("houses")){
            model.buyHouses();
        }

        if(button.equals("modRoll")){
            model.modRoll();
        }
    }
}
