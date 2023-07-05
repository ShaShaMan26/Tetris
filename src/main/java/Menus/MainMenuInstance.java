package Menus;

import Main.Instance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenuInstance extends JComponent implements KeyListener, ActionListener {
    private final Instance instance;
    private final MainMenuBoard mainMenuBoard;
    private final OptionsBoard optionsBoard;

    public MainMenuInstance(Instance instance) {
        this.instance = instance;
        JFrame gameWindow = instance.getGameWindow();
        this.setSize(gameWindow.getSize());

        optionsBoard = new OptionsBoard(gameWindow.getSize());

        mainMenuBoard = new MainMenuBoard(gameWindow.getSize());
        this.add(mainMenuBoard);

        this.addKeyListener(this);
        mainMenuBoard.getPlayButton().addActionListener(this);
        mainMenuBoard.getOptionsButton().addActionListener(this);
        optionsBoard.getExitButton().addActionListener(this);
    }

    public OptionsBoard getOptionsBoard() {
        return optionsBoard;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> {
                if (instance.isOptionsOpen()) {
                    instance.setWantsCloseOptions(true);
                } else {
                    instance.setWantsExit(true);
                }
            }
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
        } else if (e.getSource() == mainMenuBoard.getOptionsButton()) {
            instance.setWantsOpenOptions(true);
        } else if (e.getSource() == optionsBoard.getExitButton()) {
            instance.setWantsCloseOptions(true);
        }
    }
}
