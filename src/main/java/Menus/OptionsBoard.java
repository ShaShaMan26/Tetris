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
    private Font font;
    private JButton exitButton;
    private BufferedImage optionsBox;

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
        exitButton = new JButton();
        exitButton.setBounds(boardXCenter + (boardSize), boardYCenter, (int) (tileSize*1.35), tileSize);

        exitButton.setVerticalAlignment(SwingConstants.CENTER);
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setBorderPainted(false);
        exitButton.setBorder(null);
        exitButton.setMargin(new Insets(0, 0, 0, 0));
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusable(false);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(font);

        exitButton.setText("X");

        this.add(exitButton);
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(0, 0, displayWidth, displayHeight);

        g.drawImage(optionsBox, boardXCenter, boardYCenter, boardSize, boardSize, null);
    }
}
