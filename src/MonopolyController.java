import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

        if(button.equals("load")){
            try {
                model.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        if(button.equals("save")){
            try {
                model.save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
