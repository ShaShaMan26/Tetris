package Game;

import Game.Tetrimino.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameInstance extends JComponent implements KeyListener {
    private int level = 0;
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

    public void setLevel(int level) {
        this.level = level;
    }

    public void levelUp() {
        level++;
    }

    public void loadSprites() throws IOException {
        OTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/O_Tetrimino_Sprite.png")));
        JTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/J_Tetrimino_Sprite.png")));
        TTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/T_Tetrimino_Sprite.png")));
        LTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/L_Tetrimino_Sprite.png")));
        STetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/S_Tetrimino_Sprite.png")));
        ZTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/Z_Tetrimino_Sprite.png")));
        ITetriminoSprite1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/I_Tetrimino_Sprite_1.png")));
        ITetriminoSprite2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/I_Tetrimino_Sprite_2.png")));
        ITetriminoSprite3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tetriminos/I_Tetrimino_Sprite_3.png")));
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

    public void attemptToMoveDown(Tetrimino tetrimino) {
        int preMoveYPos = tetrimino.getYPos();
        if (gameBoard.aboveVirBound(tetrimino)
                && !gameBoard.isTetriminoBelow(tetrimino)) {
            tetrimino.moveDown();
        }
        if (preMoveYPos == tetrimino.getYPos()) {
            gameBoard.setActiveTetrimino(null);
        }
    }

    public void update() {
        TetriminoNode[] queuedClears = gameBoard.getQueuedClears();

        if (queuedClears.length > 0) {
            for (int i = 5; i > 0; i--) {
                for (TetriminoNode tetriminoNode : queuedClears) {
                    tetriminoNode.toggleDisplayed();
                }
                gameWindow.repaint();
                try {
                    Thread.sleep(i * 100L);
                } catch (InterruptedException ignored) {

                }
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
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        double fallTime = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                if (gameBoard.getActiveTetrimino() != null) {
                    fallTime++;
                    if(fallTime >= 53 - (4*level)) {
                        attemptToMoveDown(gameBoard.getActiveTetrimino());
                        fallTime = 0;
                    }
                }

                gameWindow.repaint();
                update();
                delta = 0;
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
            attemptToMoveDown(activeTetrimino);
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
                if (gameBoard.isTetriminoBlocking(activeTetrimino)) {
                    activeTetrimino.rotateRight();
                }
                correctPlacement(activeTetrimino);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (keyCode == KeyEvent.VK_E
                || keyCode == KeyEvent.VK_X) {
            try {
                activeTetrimino.rotateRight();
                if (gameBoard.isTetriminoBlocking(activeTetrimino)) {
                    activeTetrimino.rotateLeft();
                }
                correctPlacement(activeTetrimino);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void correctPlacement(Tetrimino tetrimino) {
        while (gameBoard.isTetriminoOutOfBounds(tetrimino)) {
            if (tetrimino.getXPos() < (gameBoard.getBoardWidth() / 2)) {
                tetrimino.moveRight();
            } else {
                tetrimino.moveLeft();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
