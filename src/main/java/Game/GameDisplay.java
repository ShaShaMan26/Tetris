package Game;

import Game.Tetrimino.Tetrimino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class GameDisplay extends JComponent {
    private final int TILE_SIZE, boardLeftSideXPos, boardRightSideXPos;
    private int score, rowsCleared, level;
    private final Dimension DISPLAY_DIMENSION;
    private BufferedImage borderTileRight, borderTileLeft, queueBox, levelBox, linesBox;
    private Tetrimino queuedTetrimino;
    private final Font font;

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

        InputStream stream = getClass().getResourceAsStream("/fonts/tetris-gb.ttf");
        try {
            assert stream != null;
            font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont((float)TILE_SIZE);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSprites() {
        try {
            borderTileRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/border_tile_right.png")));
            borderTileLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/border_tile_left.png")));
            queueBox = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/next_box.png")));
            levelBox = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/level_box.png")));
            linesBox = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/lines_box.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setQueuedTetrimino(Tetrimino queuedTetrimino) {
        queuedTetrimino.setXPos((boardRightSideXPos) / TILE_SIZE + 4);
        queuedTetrimino.setYPos(2);

        this.queuedTetrimino = queuedTetrimino;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRowsCleared(int rowsCleared) {
        this.rowsCleared = rowsCleared;
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
        int queueBoxSize = (int)(TILE_SIZE*4.5);
        int xOffset = TILE_SIZE*2;
        int yOffset = (int)(TILE_SIZE*.25);
        g.drawImage(queueBox, boardRightSideXPos + xOffset, yOffset, queueBoxSize, queueBoxSize, null);

        if (queuedTetrimino != null) {
            queuedTetrimino.paint(g);
        }
    }

    public void paintScoreBox(Graphics g) {
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(""+score, 100, 100);
    }

    public void paintLevelBox(Graphics g) {
        int boxSize = (int)(5.75 * TILE_SIZE);
        int xOffset = boardLeftSideXPos - TILE_SIZE*2 - boxSize;

        int textOffset = (int)(TILE_SIZE*3.5);
        if (level >= 10) {
            textOffset -= TILE_SIZE;
        }

        int yOffset = TILE_SIZE;

        g.drawImage(levelBox, xOffset, yOffset, boxSize, boxSize, null);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(""+level, xOffset + textOffset, yOffset + (int)(TILE_SIZE*2.25));
    }

    public void paintLinesBox(Graphics g) {
        int boxSize = (int)(5.75 * TILE_SIZE);
        int xOffset = boardLeftSideXPos - TILE_SIZE*2 - boxSize;

        int textOffset = (int)(TILE_SIZE*3.5);
        if (rowsCleared >= 1000) {
            textOffset -= TILE_SIZE*3;
        } else if (rowsCleared >= 100) {
            textOffset -= TILE_SIZE*2;
        } else if (rowsCleared >= 10) {
            textOffset -= TILE_SIZE;
        }

        int yOffset = TILE_SIZE*5;

        g.drawImage(linesBox, xOffset, yOffset, boxSize, boxSize, null);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(""+rowsCleared, xOffset + textOffset, yOffset + (int)(TILE_SIZE*2.25));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackground(g);

        paintQueueBox(g);

        paintScoreBox(g);

        paintLevelBox(g);

        paintLinesBox(g);
    }
}
