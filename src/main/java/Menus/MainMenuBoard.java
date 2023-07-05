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
    private BufferedImage button, buttonHover, buttonActive;
    private Font font;
    private JButton playButton;

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
        int playButtonWidth = tileSize * 9;
        int playButtonHeight = tileSize*3;
        int buttonXCenter = (boardWidth / 2) - (playButtonWidth / 2);
        int buttonYCenter = (boardHeight / 2) - (playButtonHeight / 2);

        JButton playButton = new JButton();
        playButton.setVerticalAlignment(SwingConstants.CENTER);
        playButton.setHorizontalTextPosition(JButton.CENTER);

        playButton.setIcon(new ImageIcon(button.getScaledInstance(playButtonWidth, playButtonHeight, java.awt.Image.SCALE_SMOOTH)));
        playButton.setRolloverIcon(new ImageIcon(buttonHover.getScaledInstance(playButtonWidth, playButtonHeight, java.awt.Image.SCALE_SMOOTH)));
        playButton.setPressedIcon(new ImageIcon(buttonActive.getScaledInstance(playButtonWidth, playButtonHeight, java.awt.Image.SCALE_SMOOTH)));

        playButton.setBorderPainted(false);
        playButton.setBorder(null);
        playButton.setMargin(new Insets(0, 0, 0, 0));
        playButton.setContentAreaFilled(false);
        playButton.setFocusable(false);

        playButton.setBounds(buttonXCenter, buttonYCenter, playButtonWidth, playButtonHeight);

        playButton.setForeground(Color.BLACK);
        playButton.setFont(font);
        playButton.setText("play game");

        this.playButton = playButton;

        this.add(playButton);
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(248,248,248));
        g.fillRect(0, 0, boardWidth, boardHeight);
    }
}
