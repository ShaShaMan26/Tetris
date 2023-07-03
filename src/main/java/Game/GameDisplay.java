package Game;

import Game.Tetrimino.Tetrimino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameDisplay extends JComponent {
    private final int TILE_SIZE, boardLeftSideXPos, boardRightSideXPos;
    private final Dimension DISPLAY_DIMENSION;
    private BufferedImage borderTileRight, borderTileLeft, queueBox;
    private Tetrimino queuedTetrimino;

    GameDisplay(Dimension displayDimensions, int tileSize) {
        DISPLAY_DIMENSION = displayDimensions;
        this.setSize(displayDimensions);

        TILE_SIZE = tileSize;

        int boardWidth = TILE_SIZE * 10;
        int displayWidth = DISPLAY_DIMENSION.width;
        int displayCenter = displayWidth / 2;

        boardLeftSideXPos = displayCenter - (boardWidth / 2);
        boardRightSideXPos = displayCenter + (boardWidth / 2);

        loadSprites();
    }

    public void loadSprites() {
        try {
            borderTileRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/border_tile_right.png")));
            borderTileLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/border_tile_left.png")));
            queueBox = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/next_box.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setQueuedTetrimino(Tetrimino queuedTetrimino) {
        queuedTetrimino.setXPos((boardRightSideXPos) / TILE_SIZE + 4);
        queuedTetrimino.setYPos(2);

        this.queuedTetrimino = queuedTetrimino;
    }

    public void paintBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, DISPLAY_DIMENSION.width, DISPLAY_DIMENSION.height);

        int borderTileSize = (int)(TILE_SIZE*.67);
        for (int j = 0; j <= DISPLAY_DIMENSION.height / borderTileSize; j++) {
            g.drawImage(borderTileRight, boardRightSideXPos, borderTileSize*j, TILE_SIZE, TILE_SIZE, null);
        }

        for (int j = 0; j <= DISPLAY_DIMENSION.height / borderTileSize; j++) {
            g.drawImage(borderTileLeft, boardLeftSideXPos - TILE_SIZE, borderTileSize*j, TILE_SIZE, TILE_SIZE, null);
        }
    }

    public void paintQueueBox(Graphics g) {
        int queueBoxSize = TILE_SIZE*5;
        int xOffset = TILE_SIZE + (int)(TILE_SIZE*.6);
        int yOffset = (int)(TILE_SIZE*.25);
        g.drawImage(queueBox, boardRightSideXPos + xOffset, yOffset, queueBoxSize, queueBoxSize, null);

        if (queuedTetrimino != null) {
            queuedTetrimino.paint(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackground(g);

        paintQueueBox(g);
    }
}
