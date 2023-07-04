package Menus;

import javax.swing.*;
import java.awt.*;

public class MainMenuBoard extends JComponent {
    private int boardWidth, boardHeight;

    public MainMenuBoard(Dimension displayDimensions) {
        boardWidth = displayDimensions.width;
        boardHeight = displayDimensions.height;

        this.setBounds(0, 0, boardWidth, boardHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, boardWidth, boardHeight);
    }
}
