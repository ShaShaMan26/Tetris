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

        JButton[] buttons = {mainMenuBoard.getPlayButton(), mainMenuBoard.getOptionsButton(),
                optionsBoard.getExitButton(), optionsBoard.getHardDropButton(), optionsBoard.getGhostButton(),
                optionsBoard.getFullscreenButton()};
        for (JButton button : buttons) {
            button.addActionListener(this);
        }
    }

    public OptionsBoard getOptionsBoard() {
        return optionsBoard;
    }

    public void updateToggleIcons() {
        optionsBoard.updateToggleIcon(optionsBoard.getHardDropButton(), instance.isHardDropEnabled());
        optionsBoard.updateToggleIcon(optionsBoard.getGhostButton(), instance.isGhostEnabled());
        optionsBoard.updateToggleIcon(optionsBoard.getFullscreenButton(), instance.isFullscreen());
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (instance.isOptionsOpen()) {
            if (e.getSource() == optionsBoard.getExitButton()) {
                instance.setWantsCloseOptions(true);
            } else if (e.getSource() == optionsBoard.getHardDropButton()) {
                instance.toggleHardDrop();
            } else if (e.getSource() == optionsBoard.getGhostButton()) {
                instance.toggleGhost();
            } else if (e.getSource() == optionsBoard.getFullscreenButton()) {
                instance.toggleFullscreen();
                instance.setWantsChangeFullscreen(true);
            }
        } else {
            if (e.getSource() == mainMenuBoard.getPlayButton()) {
                instance.setWantsGame(true);
            } else if (e.getSource() == mainMenuBoard.getOptionsButton()) {
                instance.setWantsOpenOptions(true);
            }
        }

        updateToggleIcons();
    }
}
