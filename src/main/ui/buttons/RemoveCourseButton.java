package ui.buttons;

import ui.GraphicStudyApp;
import ui.GraphicSubmissionManager;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button that triggers functions to remove a course from an account
 */
public class RemoveCourseButton extends Button implements ActionListener {

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public RemoveCourseButton(GraphicStudyApp frame, JPanel panel, SoundEffect soundEffect) {
        super(frame, panel, soundEffect);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Remove Course");
        button.setActionCommand("removeCourse");
        button.addActionListener(this);
        addToPanel(panel);
    }

    // EFFECTS: remove the current course from an account
    //          render main menu after removing the course
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("removeCourse")) {
            soundEffect.play();
            GraphicSubmissionManager manager = new GraphicSubmissionManager(frame);
            manager.removeCourseFromAccount();
            frame.removeCourseMenu();
            frame.renderMainMenu();
        }
    }
}
