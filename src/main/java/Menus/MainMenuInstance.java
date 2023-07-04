package Menus;

import Main.Instance;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenuInstance extends JComponent implements KeyListener {
    private final Instance instance;
    private final JFrame gameWindow;
    private final MainMenuBoard mainMenuBoard;

    public MainMenuInstance(Instance instance) {
        this.instance = instance;
        gameWindow = instance.getGameWindow();

        this.setSize(gameWindow.getSize());
        mainMenuBoard = new MainMenuBoard(gameWindow.getSize());
        this.add(mainMenuBoard);

        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> System.exit(69);
            case KeyEvent.VK_SPACE -> instance.setWantsGame(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
