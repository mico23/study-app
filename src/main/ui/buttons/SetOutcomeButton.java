package ui.buttons;

import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button that handles rendering the window of setting the grades of a course
 */
public class SetOutcomeButton extends Button implements ActionListener {

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public SetOutcomeButton(GraphicStudyApp frame, JPanel panel) {
        super(frame, panel);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Set Course Grades");
        button.setActionCommand("setCourseOutcome");
        button.addActionListener(this);
        addToPanel(panel);
    }

    // EFFECTS: render the submission menu
    //          set the current page to OUTCOME
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("setCourseOutcome")) {
            frame.getSoundEffect().play();
            frame.setPage(GraphicStudyApp.Page.OUTCOME);
            frame.removeCourseMenu();
            frame.renderSubmissionMenu();
        }
    }
}
