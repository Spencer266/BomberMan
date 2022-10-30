package uet.oop.bomberman.utilities;

import uet.oop.bomberman.BombermanGame;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import java.util.Objects;
import java.util.Set;

public class Sound {
    public static void play(String sound) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Objects.requireNonNull(BombermanGame.class.getResourceAsStream("/sounds/" + sound + ".wav")));
                clip.open(inputStream);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f);
                if (sound.equals("bgm")) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    while (BombermanGame.bgmPlaying);
                    clip.stop();
                } else {
                    clip.start();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}

