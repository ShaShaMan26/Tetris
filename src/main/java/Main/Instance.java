package Main;

import Game.GameInstance;
import Menus.MainMenuInstance;
import Menus.OptionsBoard;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Instance {
    private final JFrame gameWindow;
    private GameInstance gameInstance;
    private final MainMenuInstance mainMenuInstance;
    private boolean wantsGame = false, wantsMainMenu = false, wantsExit = false, wantsOpenOptions = false,
            wantsCloseOptions = false, optionsOpen = false;
    private boolean hardDropEnabled = false, ghostEnabled = false;

    public Instance(boolean hardDropEnabled, boolean ghostEnabled) {
        this.hardDropEnabled = hardDropEnabled;
        this.ghostEnabled = ghostEnabled;

        gameWindow = new JFrame();
        gameWindow.setTitle("Tetris");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(null);
        gameWindow.setUndecorated(true);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameWindow.setVisible(true);

        mainMenuInstance = new MainMenuInstance(this);
        gameWindow.add(mainMenuInstance);
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
}
