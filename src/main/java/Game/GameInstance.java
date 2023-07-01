package Game;

import Game.Tetrimino.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameInstance extends JComponent implements KeyListener {
    private final JFrame gameWindow;
    private final GameBoard gameBoard;
    private BufferedImage JTetriminoSprite, OTetriminoSprite;

    public GameInstance(JFrame gameWindow) throws IOException {
        this.gameWindow = gameWindow;
        this.setSize(gameWindow.getSize());

        gameBoard = new GameBoard(gameWindow.getSize());
        this.add(gameBoard);

        gameWindow.addKeyListener(this);
        requestFocus();

        loadSprites();

        spawnTetrimino();
    }

    public void loadSprites() throws IOException {
        JTetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/J_Tetrimino_Sprite.png"));
        OTetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/O_Tetrimino_Sprite.png"));
    }

    public void spawnTetrimino() throws IOException {
        gameBoard.setActiveTetrimino(new JTetrimino(4, 1, JTetriminoSprite));
    }

    public void update() {
        gameBoard.checkForRowClears();
    }

    public void run() throws IOException {
        long lastTime = System.nanoTime();
        double amountOfTicks = 144.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                gameWindow.repaint();
                update();
                delta--;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        Tetrimino activeTetrimino = gameBoard.getActiveTetrimino();

        if (keyCode == KeyEvent.VK_A
                || keyCode == KeyEvent.VK_LEFT) {
            if (gameBoard.rightOfHorBounds(activeTetrimino)
                    && !gameBoard.isTetriminoToTheLeftOf(activeTetrimino)) {
                activeTetrimino.moveLeft();
            }
        }
        if (keyCode == KeyEvent.VK_D
                || keyCode == KeyEvent.VK_RIGHT) {
            if (gameBoard.leftOfHorBounds(activeTetrimino)
                    && !gameBoard.isTetriminoToTheRightOf(activeTetrimino)) {
                activeTetrimino.moveRight();
            }
        }
        if (keyCode == KeyEvent.VK_S
                || keyCode == KeyEvent.VK_DOWN) {
            int preMoveYPos = activeTetrimino.getYPos();
            if (gameBoard.aboveVirBound(activeTetrimino)) {
                activeTetrimino.moveDown();
            }
            if (preMoveYPos == activeTetrimino.getYPos()) {
                try {
                    spawnTetrimino();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (keyCode == KeyEvent.VK_W
                || keyCode == KeyEvent.VK_UP) {
            gameBoard.hardDrop(activeTetrimino);
            try {
                spawnTetrimino();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (keyCode == KeyEvent.VK_Q
                || keyCode == KeyEvent.VK_Z) {
            try {
                activeTetrimino.rotateLeft();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (keyCode == KeyEvent.VK_E
                || keyCode == KeyEvent.VK_X) {
            try {
                activeTetrimino.rotateRight();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
