package Game;

import Game.Tetrimino.*;

import javax.swing.*;
import java.io.IOException;

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

    public void spawnTetrimino() throws IOException {
        gameBoard.setActiveTetrimino(new JTetrimino(4, 1));
    }

    public void update() throws IOException {
        Tetrimino activeTetrimino = gameBoard.getActiveTetrimino();

        if (keyHandler.downPressed
                && gameBoard.aboveVirBound(activeTetrimino)) {
            activeTetrimino.moveDown();
        }
        if (keyHandler.leftPressed
                && gameBoard.rightOfHorBounds(activeTetrimino)
                && !gameBoard.isTetriminoToTheLeftOf(activeTetrimino)) {
            activeTetrimino.moveLeft();
        }
        if (keyHandler.rightPressed
                && gameBoard.leftOfHorBounds(activeTetrimino)
                && !gameBoard.isTetriminoToTheRightOf(activeTetrimino)) {
            activeTetrimino.moveRight();
        }
        if (keyHandler.upPressed) {
            gameBoard.hardDrop(activeTetrimino);
        }
        if (keyHandler.leftRotatePressed) {
            activeTetrimino.rotateLeft();
        }
        if (keyHandler.rightRotatePressed) {
            activeTetrimino.rotateRight();
        }

        if (!gameBoard.aboveVirBound(activeTetrimino)
                || gameBoard.isTetriminoBelow(activeTetrimino)) {
            spawnTetrimino();
        }

        gameBoard.checkForRowClears();
    }

    public void run() throws IOException {
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
