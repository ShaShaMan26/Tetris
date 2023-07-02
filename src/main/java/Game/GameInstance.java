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
    private BufferedImage OTetriminoSprite, JTetriminoSprite, TTetriminoSprite, LTetriminoSprite, STetriminoSprite,
            ZTetriminoSprite, ITetriminoSprite1, ITetriminoSprite2, ITetriminoSprite3;

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
        OTetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/O_Tetrimino_Sprite.png"));
        JTetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/J_Tetrimino_Sprite.png"));
        TTetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/T_Tetrimino_Sprite.png"));
        LTetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/L_Tetrimino_Sprite.png"));
        STetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/S_Tetrimino_Sprite.png"));
        ZTetriminoSprite = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/Z_Tetrimino_Sprite.png"));
        ITetriminoSprite1 = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/I_Tetrimino_Sprite_1.png"));
        ITetriminoSprite2 = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/I_Tetrimino_Sprite_2.png"));
        ITetriminoSprite3 = ImageIO.read(getClass().getResourceAsStream("/Tetriminos/I_Tetrimino_Sprite_3.png"));
    }

    public void spawnTetrimino() throws IOException {
        int tetriminoNum = (int)(Math.random() * 7);
        int xSpawn = 4;
        int ySpawn = 1;


        switch (tetriminoNum) {
            case 0 -> gameBoard.setActiveTetrimino(new ITetrimino(xSpawn, ySpawn, ITetriminoSprite1, ITetriminoSprite2, ITetriminoSprite3));
            case 1 -> gameBoard.setActiveTetrimino(new OTetrimino(xSpawn, ySpawn, OTetriminoSprite));
            case 2-> gameBoard.setActiveTetrimino(new JTetrimino(xSpawn, ySpawn, JTetriminoSprite));
            case 3 -> gameBoard.setActiveTetrimino(new LTetrimino(xSpawn, ySpawn, LTetriminoSprite));
            case 4 -> gameBoard.setActiveTetrimino(new STetrimino(xSpawn, ySpawn, STetriminoSprite));
            case 5 -> gameBoard.setActiveTetrimino(new ZTetrimino(xSpawn, ySpawn, ZTetriminoSprite));
            case 6 -> gameBoard.setActiveTetrimino(new TTetrimino(xSpawn, ySpawn, TTetriminoSprite));
        }

        gameBoard.add(gameBoard.getActiveTetrimino());
    }

    public void update() {
        TetriminoNode[] queuedClears = gameBoard.getQueuedClears();

        if (queuedClears.length > 0) {
            for (int i = 6; i > 0; i--) {
                try {
                    Thread.sleep(100 * i);
                } catch (InterruptedException ignored) {

                }
                for (TetriminoNode tetriminoNode : queuedClears) {
                    tetriminoNode.toggleDisplayed();
                }
                gameWindow.repaint();
            }
        }

        gameBoard.clearQueuedRows();

        if (gameBoard.getActiveTetrimino() == null) {
            try {
                spawnTetrimino();
            } catch (IOException ignored) {

            }
        }
    }

    public void run() {
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
            if (gameBoard.aboveVirBound(activeTetrimino)
                    && !gameBoard.isTetriminoBelow(activeTetrimino)) {
                activeTetrimino.moveDown();
            }
            if (preMoveYPos == activeTetrimino.getYPos()) {
                gameBoard.setActiveTetrimino(null);
            }
        }
        if (keyCode == KeyEvent.VK_W
                || keyCode == KeyEvent.VK_UP) {
            gameBoard.hardDrop(activeTetrimino);
            gameBoard.setActiveTetrimino(null);
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
