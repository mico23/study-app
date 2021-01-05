package ui.buttons;

import model.Account;
import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button that handles average calculation of the courses on an account
 */
public class CalculateAvgButton extends Button implements ActionListener {

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    //          set the account as the input account
    public CalculateAvgButton(GraphicStudyApp frame, JPanel panel) {
        super(frame, panel);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Calculate Average");
        button.setActionCommand("calculateAverage");
        button.addActionListener(this);
        addToPanel(panel);
    }

    // EFFECTS: prompt the average of the courses on the account
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("calculateAverage")) {
            frame.getSoundEffect().play();
            int average = frame.getCurrentAccount().calculateAverage();
            JOptionPane.showMessageDialog(null,
                    "Your overall average is " + average + ".");
        }
    }
}
