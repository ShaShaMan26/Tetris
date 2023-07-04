package Main;

import Game.GameInstance;
import Menus.MainMenuInstance;

import javax.swing.*;
import java.io.IOException;

public class Instance {
    private final JFrame gameWindow;
    private GameInstance gameInstance;
    private MainMenuInstance mainMenuInstance;
    private boolean wantsGame = false, wantsMainMenu = false;

    public Instance() {
        gameWindow = new JFrame();
        gameWindow.setTitle("Tetris");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(null);
        gameWindow.setUndecorated(true);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameWindow.setVisible(true);

        try{
            gameInstance = new GameInstance(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gameInstance.setLevel(0);   // 11 is max

        mainMenuInstance = new MainMenuInstance(this);
        gameWindow.add(mainMenuInstance);
        mainMenuInstance.requestFocus();
    }

    public void handleRequests() {
        if (wantsGame) {
            wantsGame = false;
            startGame();
        } else if (wantsMainMenu) {
            wantsMainMenu = false;
            returnToMainMenu();

            try {
                gameInstance = new GameInstance(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startGame() {
        gameWindow.remove(mainMenuInstance);
        gameWindow.add(gameInstance);
        gameInstance.run();
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

    public JFrame getGameWindow() {
        return gameWindow;
    }

    public void run() {
        while (true) {
            gameWindow.repaint();
            handleRequests();
        }
    }
}
