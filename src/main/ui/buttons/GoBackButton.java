package ui.buttons;

import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button that handles events of going back to the previous menu
 */

public class GoBackButton extends Button implements ActionListener {

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public GoBackButton(GraphicStudyApp frame, JPanel panel, SoundEffect soundEffect) {
        super(frame, panel, soundEffect);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Back");
        button.setActionCommand("goBack");
        button.addActionListener(this);
        addToPanel(panel);
    }

    // EFFECTS: render the previous menu based on the current page
    //          if the current page is MAIN, render welcome menu
    //          if the current page is CREATE or SEARCH, render welcome menu
    //          if the current page is COURSE, render main menu
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        soundEffect.play();
        if (e.getActionCommand().equals("goBack")) {
            if (frame.getCurrentPage().equals(GraphicStudyApp.Page.MAIN)) {
                frame.removeButtons();
                frame.renderWelcomeButtons();
            } else if (frame.getCurrentPage().equals(GraphicStudyApp.Page.CREATE)
                    || frame.getCurrentPage().equals(GraphicStudyApp.Page.SEARCH)) {
                frame.removeSubmissionMenu();
                frame.renderWelcomeButtons();
            } else if (frame.getCurrentPage().equals(GraphicStudyApp.Page.COURSE)) {
                frame.removeCourseMenu();
                frame.renderMainMenu();
            } else {
                frame.removeSubmissionMenu();
                frame.renderMainMenu();
            }
        }
    }
}
