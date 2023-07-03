package Sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {

    Clip clip;
    URL soundURL[] = new URL[10];

    public AudioPlayer() {
        soundURL[0] = getClass().getResource("/audio/game_bgm.wav");
        soundURL[1] = getClass().getResource("/audio/game_over.wav");
        soundURL[2] = getClass().getResource("/audio/level_up.wav");
        soundURL[3] = getClass().getResource("/audio/line_clear.wav");
        soundURL[4] = getClass().getResource("/audio/menu_bgm.wav");
        soundURL[5] = getClass().getResource("/audio/move_piece.wav");
        soundURL[6] = getClass().getResource("/audio/piece_landed.wav");
        soundURL[7] = getClass().getResource("/audio/piece_landing_after_falling.wav");
        soundURL[8] = getClass().getResource("/audio/rotate_piece.wav");
        soundURL[9] = getClass().getResource("/audio/tetris_clear.wav");
    }

    public void setClip(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
