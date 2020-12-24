package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * SoundEffect is to load the sound file and handle the method that plays the sound effect
 */
public class SoundEffect {
    public String source;
    Clip clip;

    // MODIFIES: this
    // EFFECTS: constructor of SoundEffect; takes the string of the file path as an input and stores it to source
    public SoundEffect(String source) {
        this.source = source;
    }

    // EFFECTS: load the sound file from source
    //          print error message to console if the file cannot be read
    public void loadSound() {
        try {
            File file = new File(this.source);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception exception) {
            System.out.println("Cannot find audio file.");
        }
    }

    // EFFECTS: play the sound
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }
}
