package Sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AudioPlayer {

    Clip clip;
    ArrayList<Clip> loopingClips = new ArrayList<>();
    URL[] soundURL = new URL[12];

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
        soundURL[10] = getClass().getResource("/audio/game_over_message.wav");
        soundURL[11] = getClass().getResource("/audio/pause.wav");
    }

    public void setClip(int i, boolean looping) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (looping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                loopingClips.add(clip);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void play() {
        clip.start();
    }

    public void stopLoopingClips() {
        for (Clip clip : loopingClips) {
            clip.stop();
        }
    }
}
