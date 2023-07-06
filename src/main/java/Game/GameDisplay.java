package Game;

import Game.Tetrimino.Tetrimino;
import Game.Tetrimino.TetriminoNode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class GameDisplay extends JComponent {
    private int tileSize, boardLeftSideXPos, boardRightSideXPos;
    private int score, rowsCleared, level;
    private final Dimension DISPLAY_DIMENSION;
    private BufferedImage borderTileRight, borderTileLeft, queueBox, levelBox, linesBox, scoreBox;
    private Tetrimino queuedTetrimino;
    private final Font font;

    GameDisplay(Dimension displayDimensions, int tileSize) {
        DISPLAY_DIMENSION = displayDimensions;
        this.setSize(displayDimensions);

        this.tileSize = tileSize;

        int boardWidth = this.tileSize * 10;
        int displayWidth = DISPLAY_DIMENSION.width;
        int displayCenter = displayWidth / 2;

        boardLeftSideXPos = displayCenter - (boardWidth / 2);
        boardRightSideXPos = displayCenter + (boardWidth / 2);

        loadSprites();

        InputStream stream = getClass().getResourceAsStream("/fonts/tetris-gb.ttf");
        try {
            assert stream != null;
            font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont((float) this.tileSize);
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
            scoreBox = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/score_box.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setQueuedTetrimino(Tetrimino queuedTetrimino) {
        queuedTetrimino.setXPos((boardLeftSideXPos) / tileSize - 4);
        queuedTetrimino.setYPos(2);

        this.queuedTetrimino = queuedTetrimino;
    }

    public void updateTetriminoTileSize() {
        if (queuedTetrimino != null) {
            for (TetriminoNode tetriminoNode : queuedTetrimino.getTetriminoNodes()) {
                tetriminoNode.setSideLength(tileSize);
            }
        }
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

        int borderTileSize = (int)(tileSize *.67);
        for (int j = 0; j <= DISPLAY_DIMENSION.height / borderTileSize; j++) {
            g.drawImage(borderTileRight, boardRightSideXPos, borderTileSize*j, tileSize, tileSize, null);
        }

        for (int j = 0; j <= DISPLAY_DIMENSION.height / borderTileSize; j++) {
            g.drawImage(borderTileLeft, boardLeftSideXPos - tileSize, borderTileSize*j, tileSize, tileSize, null);
        }
    }

    public void paintQueueBox(Graphics g) {
        int queueBoxSize = (int)(tileSize *4.75);
        int xOffset = (int)(tileSize *1.4);
        int yOffset = (int)(tileSize *.65);
        g.drawImage(queueBox, boardLeftSideXPos - queueBoxSize - xOffset, yOffset, queueBoxSize, queueBoxSize, null);

        if (queuedTetrimino != null) {
            queuedTetrimino.paint(g);
        }
    }

    public void paintScoreBox(Graphics g) {
        int boxSize = (int)(7.625 * tileSize);
        int xOffset = boardRightSideXPos + (int)(tileSize *1.5);

        int textOffset = (int)(tileSize *5.35);
        if (score >= 1000000) {
            textOffset -= tileSize *4.95;
        } else if (score >= 100000) {
            textOffset -= tileSize *4.5;
        } else if (score >= 10000) {
            textOffset -= tileSize *4;
        } else if (score >= 1000) {
            textOffset -= tileSize *3;
        } else if (score >= 100) {
            textOffset -= tileSize *2;
        } else if (score >= 10) {
            textOffset -= tileSize;
        }

        int yOffset = (int)(tileSize *.25);

        g.drawImage(scoreBox, xOffset, yOffset, boxSize, boxSize, null);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(""+score, xOffset + textOffset, yOffset + (int)(tileSize *3.25));
    }

    public void paintLevelBox(Graphics g) {
        int boxSize = (int)(5.75 * tileSize);
        int xOffset = boardRightSideXPos + (int)(tileSize *3.375);

        int textOffset = (int)(tileSize *3.5);
        if (level >= 10) {
            textOffset -= tileSize;
        }

        int yOffset = (int)(tileSize *4.3);

        g.drawImage(levelBox, xOffset, yOffset, boxSize, boxSize, null);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(""+level, xOffset + textOffset, yOffset + (int)(tileSize *2.25));
    }

    public void paintLinesBox(Graphics g) {
        int boxSize = (int)(5.75 * tileSize);
        int xOffset = boardRightSideXPos + (int)(tileSize *3.375);

        int textOffset = (int)(tileSize *3.5);
        if (rowsCleared >= 1000) {
            textOffset -= tileSize *3;
        } else if (rowsCleared >= 100) {
            textOffset -= tileSize *2;
        } else if (rowsCleared >= 10) {
            textOffset -= tileSize;
        }

        int yOffset = (int)(tileSize *7.35);

        g.drawImage(linesBox, xOffset, yOffset, boxSize, boxSize, null);

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(""+rowsCleared, xOffset + textOffset, yOffset + (int)(tileSize *2.25));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBackground(g);

        paintQueueBox(g);

        paintLevelBox(g);

        paintLinesBox(g);

        paintScoreBox(g);
    }
}
