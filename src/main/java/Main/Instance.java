package Main;

import Game.GameInstance;
import Menus.MainMenuInstance;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Instance {
    private JFrame gameWindow;
    private GameInstance gameInstance;
    private MainMenuInstance mainMenuInstance;
    private boolean wantsGame = false, wantsMainMenu = false, wantsExit = false, wantsOpenOptions = false,
            wantsCloseOptions = false, optionsOpen = false, wantsChangeFullscreen = false;
    private boolean hardDropEnabled, ghostEnabled, fullscreen;

    public Instance(boolean hardDropEnabled, boolean ghostEnabled, boolean fullscreen) {
        this.hardDropEnabled = hardDropEnabled;
        this.ghostEnabled = ghostEnabled;
        this.fullscreen = fullscreen;

        loadWindow();
    }

    public void loadWindow() {
        gameWindow = new JFrame();
        gameWindow.setTitle("Tetris");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(null);
        gameWindow.setLocationRelativeTo(null);

        if (fullscreen) {
            gameWindow.setUndecorated(true);
            gameWindow.setResizable(false);
            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            gameWindow.setUndecorated(false);
            gameWindow.setResizable(true);
            gameWindow.setSize(500, 500);
        }

        gameWindow.setVisible(true);

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
        if (mainMenuInstance != null){
            gameWindow.remove(mainMenuInstance);
        }
        if (gameInstance != null) {
            gameWindow.remove(gameInstance);
        }

        mainMenuInstance = new MainMenuInstance(this);
        //update gameInstance size

        if (gameInstance != null
                && gameInstance.isRunning()) {
            gameWindow.add(gameInstance);
        } else {
            gameWindow.add(mainMenuInstance);
        }
        mainMenuInstance.requestFocus();
    }

    public void handleRequests() {
        if (wantsGame) {
            try{
                gameInstance = new GameInstance(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            gameInstance.setLevel(0);   // 11 is max
            wantsGame = false;
            startGame();
        } else if (wantsOpenOptions) {
            wantsOpenOptions = false;
            optionsOpen = true;
            openOptions();
        } else if(wantsCloseOptions) {
            wantsCloseOptions = false;
            optionsOpen = false;
            closeOptions();
        } else if (wantsMainMenu) {
            wantsMainMenu = false;
            returnToMainMenu();

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

    public void run() {
        while (true) {
            gameWindow.repaint();
            handleRequests();
        }
    }

    public void save() {
        String localPath = System.getenv("APPDATA") + "\\Sha's-Tetris";
        try {
            PrintWriter writer = new PrintWriter(localPath + "\\SaveData.txt");
            writer.print("");
            writer.close();

            FileWriter fileWriter = new FileWriter(localPath + "\\SaveData.txt");

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
