package Menus;

import Main.Instance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenuInstance extends JComponent implements KeyListener, ActionListener {
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
        mainMenuBoard.getPlayButton().addActionListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> instance.setWantsExit(true);
            case KeyEvent.VK_SPACE -> instance.setWantsGame(true);
            case KeyEvent.VK_1 -> instance.toggleHardDrop();
            case KeyEvent.VK_2 -> instance.toggleGhost();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuBoard.getPlayButton()) {
            instance.setWantsGame(true);
        }
    }
}