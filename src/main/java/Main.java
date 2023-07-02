import Game.GameInstance;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame gameWindow = new JFrame();
        gameWindow.setTitle("Tetris");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(null);
        gameWindow.setUndecorated(true);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameWindow.setVisible(true);

        GameInstance gameInstance = new GameInstance(gameWindow);
        gameWindow.add(gameInstance);

        gameInstance.setLevel(11);

        gameInstance.run();
    }
}
