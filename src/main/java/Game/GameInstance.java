package Game;

import Game.Tetrimino.OTetrimino;
import Game.Tetrimino.Tetrimino;

import javax.swing.*;

public class GameInstance extends JComponent {
    JFrame gameWindow;
    GameBoard gameBoard;
    KeyHandler keyHandler;

    public GameInstance(JFrame gameWindow) {
        this.gameWindow = gameWindow;
        this.setSize(gameWindow.getSize());

        gameBoard = new GameBoard(gameWindow.getSize());
        this.add(gameBoard);

        keyHandler = new KeyHandler();
        gameWindow.addKeyListener(keyHandler);
        requestFocus();
    }

    public void spawnTetrimino() {
        gameBoard.setActiveTetrimino(new OTetrimino(5, 2));
    }

    public void update() {
        Tetrimino activeTetrimino = gameBoard.getActiveTetrimino();

        if (keyHandler.downPressed
                && activeTetrimino.getYPos() < gameBoard.getBoardHeight() - 1) {
            activeTetrimino.moveDown();
        }
        if (keyHandler.leftPressed
                && activeTetrimino.getXPos() > 1
                && !gameBoard.isTetriminoToTheLeftOf(activeTetrimino)) {
            activeTetrimino.moveLeft();
        }
        if (keyHandler.rightPressed
                && activeTetrimino.getXPos() < gameBoard.getBoardWidth() - 1
                && !gameBoard.isTetriminoToTheRightOf(activeTetrimino)) {
            activeTetrimino.moveRight();
        }
        if (keyHandler.upPressed) {
            activeTetrimino.hardDrop(gameBoard.getLowestDropOf(activeTetrimino));
        }
        if (keyHandler.leftRotatePressed) {
            activeTetrimino.rotateLeft();
        }
        if (keyHandler.rightRotatePressed) {
            activeTetrimino.rotateRight();
        }

        if (activeTetrimino.getYPos() == gameBoard.getBoardHeight() - 1
                || gameBoard.isTetriminoBelow(activeTetrimino)) {
            spawnTetrimino();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 15.0;
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
}
