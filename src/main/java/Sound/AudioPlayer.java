package Sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AudioPlayer {

    private Clip clip;
    private Clip bgm;
    private final ArrayList<Clip> clips = new ArrayList<>();
    private final URL[] soundURL = new URL[12];
    private float volume;

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

    public void setClip(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(volume);
            clips.add(clip);
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setBGM (int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(volume);
            clips.add(clip);
            bgm = clip;
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void play() {
        clip.start();
    }

    public void playBGM() {
        bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopBGM() {
        bgm.stop();
    }

    public void resetBGM() {
        bgm.setMicrosecondPosition(0);
    }

    public void close() {
        for (Clip clip : clips) {
            clip.close();
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
