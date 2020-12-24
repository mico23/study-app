package ui.buttons;

import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button that triggers closing the application
 */
public class ExitButton extends Button implements ActionListener {
//    private static final String ICON_LOCATION = "./data/stopIcon.jpg";

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public ExitButton(GraphicStudyApp frame, JPanel panel, SoundEffect soundEffect) {
        super(frame, panel, soundEffect);
    }

    // MODIFIES: this
    // EFFECTS: render the button and add it to JPanel component
    @Override
    protected void createButton(JPanel panel) {
        button = new JButton("Exit");
        button.setActionCommand("exit");
        button.addActionListener(this);
        addToPanel(panel);
    }

    // EFFECTS: exit the application
    //          prompt a confirmation message before exiting the application
    //          play sound when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("exit")) {
            soundEffect.play();
            String[] objButtons = {"Yes", "No"};
            int result = JOptionPane.showOptionDialog(null, "Have you saved your courses?",
                    null, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, frame.getWarningIcon(), objButtons, objButtons[1]);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
