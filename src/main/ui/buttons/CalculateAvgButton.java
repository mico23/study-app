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
    private Account account;

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    //          set the account as the input account
    public CalculateAvgButton(GraphicStudyApp frame, JPanel panel, SoundEffect soundEffect, Account account) {
        super(frame, panel, soundEffect);
        this.account = account;
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
            soundEffect.play();
            int average = account.calculateAverage();
            JOptionPane.showMessageDialog(null,
                    "Your overall average is " + average + ".");
        }
    }
}
