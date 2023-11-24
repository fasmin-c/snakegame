import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    Clip clip;

    public void startBackgroundMusic() {
        try {
            AudioInputStream audioin = AudioSystem.getAudioInputStream(new File("sound/snake-pit-74085.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioin);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endBackground() {
        clip.stop();
    }

    public static void playSound(String soundFile) {

        try {
            AudioInputStream audioin = AudioSystem.getAudioInputStream(new File(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioin);
            clip.start();
            clip.setLoopPoints(0, 3);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
