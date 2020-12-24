package ui.buttons;

import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button that handles rendering the window of searching an account
 */
public class LookForAccountButton extends Button implements ActionListener {

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public LookForAccountButton(GraphicStudyApp frame, JPanel panel, SoundEffect soundEffect) {
        super(frame, panel, soundEffect);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Look For User Account");
        button.setActionCommand("lookForAccount");
        button.addActionListener(this);
        addToPanel(panel);
    }
    
    // EFFECTS: render the submission menu
    //          set the current page to SEARCH
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("lookForAccount")) {
            soundEffect.play();
            frame.setPage(GraphicStudyApp.Page.SEARCH);
            frame.removeButtons();
            frame.renderSubmissionMenu();
        }
    }
}
