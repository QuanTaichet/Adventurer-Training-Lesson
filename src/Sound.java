import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    String[] soundPath = new String[10];

    public Sound() {
        soundPath[0] = "res\\sound\\jump.wav";
        soundPath[1] = "res\\sound\\time_for_adventure.wav";
    }

    public void setFile(int i) {
        try {
            java.io.File file = new java.io.File(soundPath[i]); 
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void loop() {
        if (clip != null) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
        clip.stop();
        }
    }
}