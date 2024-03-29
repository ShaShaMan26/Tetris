package Menus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MainMenuBoard extends JComponent {
    private final int boardWidth, boardHeight, tileSize;
    private BufferedImage button, buttonHover, buttonActive, titleSprite;
    private Font font;
    private final JButton playButton = new JButton(), optionsButton = new JButton(), exitButton = new JButton();

    public MainMenuBoard(Dimension displayDimensions) {
        boardWidth = displayDimensions.width;
        boardHeight = displayDimensions.height;
        this.setBounds(0, 0, boardWidth, boardHeight);
        tileSize = boardHeight / 20;

        loadAssets();

        addButtons();
    }

    public void loadAssets() {
        try {
            button = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/button.png")));
            buttonHover = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/button_hover.png")));
            buttonActive = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/button_active.png")));
            titleSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/title_sprite.png")));
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
        int buttonWidth = tileSize * 9;
        int buttonHeight = tileSize*3;
        int buttonXCenter = (boardWidth / 2) - (buttonWidth / 2);
        int buttonYCenter = (boardHeight / 2) - (buttonHeight / 2);

        JButton[] buttons = {playButton, optionsButton, exitButton};

        for (JButton jButton : buttons) {
            jButton.setVerticalAlignment(SwingConstants.CENTER);
            jButton.setHorizontalTextPosition(JButton.CENTER);
            jButton.setIcon(new ImageIcon(button.getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
            jButton.setRolloverIcon(new ImageIcon(buttonHover.getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
            jButton.setPressedIcon(new ImageIcon(buttonActive.getScaledInstance(buttonWidth, buttonHeight, java.awt.Image.SCALE_SMOOTH)));
            jButton.setBorderPainted(false);
            jButton.setBorder(null);
            jButton.setMargin(new Insets(0, 0, 0, 0));
            jButton.setContentAreaFilled(false);
            jButton.setFocusable(false);
            jButton.setForeground(Color.BLACK);
            jButton.setFont(font);
        }

        playButton.setBounds(buttonXCenter, buttonYCenter - (int)(buttonHeight*.1), buttonWidth, buttonHeight);
        playButton.setText("play game");
        this.add(playButton);

        optionsButton.setBounds(buttonXCenter, buttonYCenter + (int)(buttonHeight), buttonWidth, buttonHeight);
        optionsButton.setText("options");
        this.add(optionsButton);

        exitButton.setBounds(buttonXCenter, buttonYCenter + (int)(buttonHeight*2.1), buttonWidth, buttonHeight);
        exitButton.setText("exit");
        this.add(exitButton);
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getOptionsButton() {
        return optionsButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(248,248,248));
        g.fillRect(0, 0, boardWidth, boardHeight);

        int width = tileSize*19;
        int height = tileSize*6;
        int xPos = (boardWidth / 2) - (width / 2);

        g.drawImage(titleSprite, xPos, tileSize, width, height, null);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("sha's", xPos, tileSize-2);
    }
}
