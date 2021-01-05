package ui.buttons;

import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button that handles rendering the window of adding hours to a course
 */
public class AddHoursButton extends Button implements ActionListener {

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public AddHoursButton(GraphicStudyApp frame, JPanel panel) {
        super(frame, panel);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Add Hours");
        button.setActionCommand("addHours");
        button.addActionListener(this);
        addToPanel(panel);
    }
    
    // EFFECTS: render the submission menu
    //          set the current page to ADD
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addHours")) {
            frame.getSoundEffect().play();
            frame.setPage(GraphicStudyApp.Page.HOURS);
            frame.removeCourseMenu();
            frame.renderSubmissionMenu();
        }
    }
}
