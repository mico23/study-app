package ui.buttons;

import model.Account;
import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Button that triggers functions to save the account information
 */
public class SaveButton extends Button implements ActionListener {

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public SaveButton(GraphicStudyApp frame, JPanel panel) {
        super(frame, panel);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Save Courses");
        button.setActionCommand("save");
        button.addActionListener(this);
        addToPanel(panel);
    }

    // EFFECTS: saves the accounts to a Json file
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getSoundEffect().play();
        if (e.getActionCommand().equals("save")) {
            saveAccount();
        }
    }

    /**
     * NOTE: some code of this class are from JsonSerializationDemo.
     */
    // EFFECTS: saves the accounts to a Json file
    //          prompt a message when the saving process is complete
    private void saveAccount() {
        frame.getSoundEffect().play();
        frame.saveAccount();
    }
}
