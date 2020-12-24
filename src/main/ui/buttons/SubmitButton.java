package ui.buttons;

import model.Account;
import ui.GraphicStudyApp;
import ui.GraphicSubmissionManager;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Button that handles events of submitting user input
 */
public class SubmitButton extends Button implements ActionListener {
    private List<Account> accounts;
    private String userInput;

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    //          set accounts as the input account list
    public SubmitButton(GraphicStudyApp frame, JPanel panel, SoundEffect soundEffect, List<Account> accounts) {
        super(frame, panel, soundEffect);
        this.accounts = accounts;
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Submit");
        button.setActionCommand("submit");
        button.addActionListener(this);
        addToPanel(panel);
    }

    // EFFECTS: process use input based on the current page status
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("submit")) {
            soundEffect.play();
            getStringInput();
            GraphicSubmissionManager manager = new GraphicSubmissionManager(frame, userInput);
            if (frame.getCurrentPage().equals(GraphicStudyApp.Page.SEARCH)) {
                manager.searchAccount();
            } else if (frame.getCurrentPage().equals(GraphicStudyApp.Page.CREATE)) {
                manager.createAccount();
            } else if (frame.getCurrentPage().equals(GraphicStudyApp.Page.ADD)) {
                manager.addCourseToAccount();
            } else if (frame.getCurrentPage().equals(GraphicStudyApp.Page.HOURS)) {
                manager.addHoursToCourse();
            } else if (frame.getCurrentPage().equals(GraphicStudyApp.Page.OUTCOME)) {
                manager.setCourseOutcome();
            }
        }
    }

    // EFFECTS: parse the string from user input
    //          remove spaces
    private void getStringInput() {
        userInput = frame.getTextField().getText();
        userInput = userInput.replaceAll("\\s+", "");
    }
}
