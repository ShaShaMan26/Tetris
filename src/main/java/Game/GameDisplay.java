package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameDisplay extends JComponent {
    private final int TILE_SIZE;
    private final Dimension DISPLAY_DIMENSION;
    private BufferedImage borderTile;

    GameDisplay(Dimension displayDimensions, int tileSize) {
        DISPLAY_DIMENSION = displayDimensions;
        this.setSize(displayDimensions);

        TILE_SIZE = tileSize;

        loadSprites();
    }

    public void loadSprites() {
        try {
            borderTile = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/border_tile.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, DISPLAY_DIMENSION.width, DISPLAY_DIMENSION.height);

        int boardWidth = TILE_SIZE * 10;
        int displayWidth = DISPLAY_DIMENSION.width;
        int displayCenter = displayWidth / 2;

        int boardLeftSideXPos = displayCenter - (boardWidth / 2);
        int boardRightSideXPos = displayCenter + (boardWidth / 2);

        int displayWidthInTiles = displayWidth / TILE_SIZE;

        for (int i = 0; i <= 0; i++) {
            for (int j = 0; j <= DISPLAY_DIMENSION.height / TILE_SIZE; j++) {
                g.drawImage(borderTile, boardRightSideXPos + (TILE_SIZE*i), TILE_SIZE*j, TILE_SIZE, TILE_SIZE, null);
            }
        }

        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= DISPLAY_DIMENSION.height / TILE_SIZE; j++) {
                g.drawImage(borderTile, boardLeftSideXPos - (TILE_SIZE*i), TILE_SIZE*j, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }
}
