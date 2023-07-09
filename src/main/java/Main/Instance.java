package Main;

import Game.GameInstance;
import Menus.MainMenuInstance;
import Sound.AudioPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class Instance {
    private JFrame gameWindow;
    private GameInstance gameInstance;
    private MainMenuInstance mainMenuInstance;
    private final AudioPlayer audioPlayer =  new AudioPlayer();
    private boolean wantsGame = false, wantsMainMenu = false, wantsExit = false, wantsOpenOptions = false,
            wantsCloseOptions = false, optionsOpen = false, wantsChangeFullscreen = false;
    private boolean hardDropEnabled, ghostEnabled, fullscreen;
    private int highScore;
    private float volume;

    public Instance(boolean hardDropEnabled, boolean ghostEnabled, boolean fullscreen, int highScore, float volume) {
        this.hardDropEnabled = hardDropEnabled;
        this.ghostEnabled = ghostEnabled;
        this.fullscreen = fullscreen;

        this.highScore = highScore;

        setVolume(volume);

        loadWindow();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        audioPlayer.setVolume(volume);
    }

    public float getVolume() {
        return volume;
    }

    public void playBGM() {
        audioPlayer.setClip(4, true);
        audioPlayer.play();
    }

    public void stopBGM() {
        audioPlayer.stopLoopingClips();
    }

    public void loadWindow() {
        gameWindow = new JFrame();
        gameWindow.setTitle("Tetris");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(null);

        try {
            gameWindow.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/icon.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (fullscreen) {
            gameWindow.setUndecorated(true);
            gameWindow.setResizable(false);
            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            gameWindow.setUndecorated(false);
            gameWindow.setResizable(true);

            gameWindow.setSize(1080, 720);
        }

        gameWindow.setVisible(true);
        gameWindow.setLocationRelativeTo(null);

        setWindow();

        try {
            gameInstance = new GameInstance(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gameWindow.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                setWindow();
            }
        });
    }

    public void setWindow() {
        stopBGM();
        if (mainMenuInstance != null){
            gameWindow.remove(mainMenuInstance);
        }

        if (gameInstance != null) {
            gameWindow.remove(gameInstance);
            gameInstance.resizeVisuals();
        }

        mainMenuInstance = new MainMenuInstance(this);
        if (optionsOpen) {
            gameWindow.setResizable(false);
            gameWindow.add(mainMenuInstance.getOptionsBoard());
            mainMenuInstance.updateToggleIcons();
        }

        if (gameInstance != null
                && gameInstance.isRunning()) {
            gameWindow.add(gameInstance);
        } else {
            gameWindow.add(mainMenuInstance);
            playBGM();
        }
        mainMenuInstance.requestFocus();
    }

    public void handleRequests() {
        if (wantsGame) {
            stopBGM();
            gameInstance.setLevel(0);   // 11 is max
            wantsGame = false;
            startGame();
        } else if (wantsOpenOptions) {
            wantsOpenOptions = false;
            optionsOpen = true;
            gameWindow.setResizable(false);
            openOptions();
        } else if(wantsCloseOptions) {
            wantsCloseOptions = false;
            optionsOpen = false;
            gameWindow.setResizable(true);
            closeOptions();
        } else if (wantsMainMenu) {
            wantsMainMenu = false;
            returnToMainMenu();
            playBGM();

            try {
                gameInstance = new GameInstance(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (wantsExit) {
            save();
            System.exit(69);
        } else if (wantsChangeFullscreen) {
            wantsChangeFullscreen = false;
            stopBGM();

            gameWindow.dispose();
            loadWindow();
        }
    }

    public void startGame() {
        gameWindow.remove(mainMenuInstance);
        gameWindow.add(gameInstance);
        gameInstance.run();
    }

    public void openOptions() {
        gameWindow.add(mainMenuInstance.getOptionsBoard());
        gameWindow.remove(mainMenuInstance);
        gameWindow.add(mainMenuInstance);
        mainMenuInstance.grabFocus();
        mainMenuInstance.requestFocus();
    }

    public void closeOptions() {
        gameWindow.remove(mainMenuInstance.getOptionsBoard());
        mainMenuInstance.requestFocus();
    }

    public boolean isOptionsOpen() {
        return optionsOpen;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void returnToMainMenu() {
        gameWindow.remove(gameInstance);
        gameWindow.add(mainMenuInstance);
        mainMenuInstance.requestFocus();
    }

    public void setWantsGame(boolean wantsGame) {
        this.wantsGame = wantsGame;
    }

    public void setWantsMainMenu(boolean wantsMainMenu) {
        this.wantsMainMenu = wantsMainMenu;
    }

    public void setWantsExit(boolean wantsExit) {
        this.wantsExit = wantsExit;
    }

    public void setWantsOpenOptions(boolean wantsOpenOptions) {
        this.wantsOpenOptions = wantsOpenOptions;
    }

    public void setWantsCloseOptions(boolean wantsCloseOptions) {
        this.wantsCloseOptions = wantsCloseOptions;
    }

    public void setWantsChangeFullscreen(boolean wantsChangeFullscreen) {
        this.wantsChangeFullscreen = wantsChangeFullscreen;
    }

    public JFrame getGameWindow() {
        return gameWindow;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void run() {long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                gameWindow.repaint();
                handleRequests();
                delta = 0;
            }
        }
    }

    public void save() {
        String localPath = System.getenv("APPDATA") + "\\Sha's-Tetris";
        try {
            PrintWriter writer = new PrintWriter(localPath + "\\SaveData.txt");
            writer.print("");
            writer.close();

            FileWriter fileWriter = new FileWriter(localPath + "\\SaveData.txt");

            fileWriter.write(String.valueOf(highScore));

            fileWriter.write("\n");

            if (hardDropEnabled) {
                fileWriter.write("1");
            } else {
                fileWriter.write("0");
            }

            fileWriter.write("\n");

            if (ghostEnabled) {
                fileWriter.write("1");
            } else {
                fileWriter.write("0");
            }

            fileWriter.write("\n");

            if (fullscreen) {
                fileWriter.write("1");
            } else {
                fileWriter.write("0");
            }

            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isHardDropEnabled() {
        return hardDropEnabled;
    }

    public boolean isGhostEnabled() {
        return ghostEnabled;
    }

    public void toggleHardDrop() {
        hardDropEnabled = !hardDropEnabled;
    }

    public void toggleGhost() {
        ghostEnabled = !ghostEnabled;
    }

    public void toggleFullscreen() {
        fullscreen = !fullscreen;
    }
}
