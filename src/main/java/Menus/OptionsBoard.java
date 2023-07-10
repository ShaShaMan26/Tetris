package Menus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class OptionsBoard extends JComponent {
    private final int displayWidth, displayHeight, tileSize;
    private final int boardSize, boardXCenter, boardYCenter;
    private int volume = 0;
    private Font font;
    private final JButton exitButton = new JButton(), hardDropButton = new JButton(), ghostButton = new JButton(),
            fullscreenButton = new JButton(), volUpButton = new JButton(), volDownButton = new JButton();
    private BufferedImage optionsBox, toggleActive, toggleInactive, plus, plusActive, minus, minusActive;

    OptionsBoard(Dimension displayDimensions) {
        this.setFocusable(false);

        displayWidth = displayDimensions.width;
        displayHeight = displayDimensions.height;
        this.setBounds(0, 0, displayWidth, displayHeight);
        tileSize = displayHeight / 20;

        boardSize = tileSize*15;
        boardXCenter = (displayWidth / 2) - (boardSize / 2);
        boardYCenter = (displayHeight / 2) - (boardSize / 2);

        loadAssets();

        addButtons();
    }

    public void loadAssets(){
        try {
            optionsBox = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/options_box.png")));
            toggleActive = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/toggle_icon_on.png")));
            toggleInactive = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/toggle_icon_off.png")));
            plus = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/plus.png")));
            plusActive = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/plus_active.png")));
            minus = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/minus.png")));
            minusActive = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/minus_active.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream stream = getClass().getResourceAsStream("/fonts/tetris-gb.ttf");
        try {
            assert stream != null;
            font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont((float) (tileSize*.85));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void addButtons() {
        JButton[] buttons = {exitButton, hardDropButton, ghostButton, fullscreenButton, volUpButton, volDownButton};

        for (JButton button : buttons) {
            button.setVerticalAlignment(SwingConstants.CENTER);
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setBorderPainted(false);
            button.setBorder(null);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setContentAreaFilled(false);
            button.setFocusable(false);
            button.setForeground(Color.BLACK);
            button.setFont(font);
        }

        exitButton.setText("X");
        exitButton.setBounds(boardXCenter + (boardSize), boardYCenter, (int) (tileSize*1.35), tileSize);

        volUpButton.setIcon(new ImageIcon(plus.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH)));
        volUpButton.setPressedIcon(new ImageIcon(plusActive.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH)));
        volUpButton.setBounds(boardXCenter + (boardSize/2) + (int)(tileSize*1.25), boardYCenter + boardSize - tileSize*2, tileSize, tileSize);

        volDownButton.setIcon(new ImageIcon(minus.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH)));
        volDownButton.setPressedIcon(new ImageIcon(minusActive.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH)));
        volDownButton.setBounds(boardXCenter+ (boardSize/2) - (int)(tileSize*2.8), boardYCenter + boardSize - tileSize*2, tileSize, tileSize);

        hardDropButton.setText("hard drop");
        ghostButton.setText("ghost piece");
        fullscreenButton.setText("fullscreen");

        JButton[] optionButtons = {hardDropButton, ghostButton, fullscreenButton};

        int yOffset = boardYCenter + tileSize*4;
        int xOffset = (tileSize*2);
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setBounds(boardXCenter + xOffset, tileSize*(i*2) + yOffset, (int) (tileSize*10.5) ,tileSize);
            optionButtons[i].setHorizontalTextPosition(JButton.RIGHT);
            optionButtons[i].setIconTextGap(tileSize/2);
            optionButtons[i].setHorizontalAlignment(JButton.LEFT);
            optionButtons[i].setIcon(new ImageIcon(toggleInactive.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH)));
        }

        this.add(exitButton);
        this.add(volUpButton);
        this.add(volDownButton);
        this.add(hardDropButton);
        this.add(ghostButton);
        this.add(fullscreenButton);
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getVolUpButton() {
        return volUpButton;
    }

    public JButton getVolDownButton() {
        return volDownButton;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public JButton getHardDropButton() {
        return hardDropButton;
    }

    public JButton getGhostButton() {
        return ghostButton;
    }

    public JButton getFullscreenButton() {
        return fullscreenButton;
    }

    public void updateToggleIcon (JButton button, boolean active) {
        if (active) {
            button.setIcon(new ImageIcon(toggleActive.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH)));
        } else {
            button.setIcon(new ImageIcon(toggleInactive.getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH)));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(0, 0, displayWidth, displayHeight);

        g.drawImage(optionsBox, boardXCenter, boardYCenter, boardSize, boardSize, null);

        g.setFont(font);
        g.setColor(Color.BLACK);

        g.drawString("volume", boardXCenter + (boardSize/2) - (int)(tileSize*2.7),
                boardYCenter + boardSize - (int)(tileSize*2.35));

        if (volume > 99) {
            g.drawString(String.valueOf(volume), boardXCenter + (boardSize/2) - (int)(tileSize*1.3),
                    boardYCenter + boardSize - (int)(tileSize*1.2));
        } else {
            g.drawString(String.valueOf(volume), boardXCenter + (boardSize/2) - tileSize,
                    boardYCenter + boardSize - (int)(tileSize*1.2));
        }
    }
}
