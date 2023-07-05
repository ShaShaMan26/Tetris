package Menus;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class OptionsBoard extends JComponent {
    private final int displayWidth, displayHeight, tileSize;
    private Font font;
    private JButton exitButton;

    OptionsBoard(Dimension displayDimensions) {
        this.setFocusable(false);

        displayWidth = displayDimensions.width;
        displayHeight = displayDimensions.height;
        this.setBounds(0, 0, displayWidth, displayHeight);
        tileSize = displayHeight / 20;

        loadAssets();

        addButtons();
    }

    public void loadAssets(){
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
        exitButton.setBounds(0, 0 , 200, 100);

        exitButton.setVerticalAlignment(SwingConstants.CENTER);
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setBorderPainted(false);
        exitButton.setBorder(null);
        exitButton.setMargin(new Insets(0, 0, 0, 0));
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusable(false);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(font);

        exitButton.setText("exit");

        this.add(exitButton);
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(0, 0, displayWidth, displayHeight);

        int boardSize = (int)(((displayHeight / 9) * 16) * .35);
        int boardXCenter = (displayWidth / 2) - (boardSize / 2);
        int boardYCenter = (displayHeight / 2) - (boardSize / 2);
        g.setColor(new Color(248,248,248));
        g.fillRect(boardXCenter, boardYCenter, boardSize, boardSize);
    }
}
