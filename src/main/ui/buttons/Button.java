package ui.buttons;

import ui.GraphicStudyApp;
import ui.SoundEffect;

import javax.swing.*;

/**
 * Abstract class for buttons in the UI
 * Note: some of the code below are from SimpleDrawingPlayer
 */

public abstract class Button {
    protected JButton button;
    protected GraphicStudyApp frame;
    protected SoundEffect soundEffect;

    // EFFECTS: Constructor of the button
    //          it takes GraphicStudyApp object, JPanel object and SoundEffect object as inputs
    public Button(GraphicStudyApp frame, JPanel panel, SoundEffect soundEffect) {
        createButton(panel);
        this.button = configureButton(this.button);
        this.frame = frame;
        this.soundEffect = soundEffect;
    }

    // MODIFIES: this
    // EFFECTS:  configure the button to show on the JPanel object
    protected JButton configureButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // EFFECTS: return the button object
    public JButton getButton() {
        return this.button;
    }

    // EFFECTS: creates button to JPanel object
    protected abstract void createButton(JPanel panel);

    // MODIFIES: panel
    // EFFECTS:  adds the given button to the panel component
    public void addToPanel(JPanel panel) {
        panel.add(button);
    }
}
