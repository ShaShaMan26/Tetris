package Game;

import Game.Tetrimino.*;
import Main.Instance;
import Sound.AudioPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameInstance extends JComponent implements KeyListener {
    private final Instance instance;
    private int level = 0, score = 0, clearedLines = 0, softDropNum = 0, extraClearedLines = 0, highScore = 0;
    private boolean running = false, gameOver = false, paused = false;
    private double fallTime = 0;
    private int nextTetriminoNum = (int)(Math.random() * 7);
    private final JFrame gameWindow;
    private GameBoard gameBoard;
    private GameDisplay gameDisplay;
    private BufferedImage OTetriminoSprite, JTetriminoSprite, TTetriminoSprite, LTetriminoSprite, STetriminoSprite,
            ZTetriminoSprite, ITetriminoSprite1, ITetriminoSprite2, ITetriminoSprite3;
    private final AudioPlayer audioPlayer = new AudioPlayer();
    private final ArrayList<Integer> pressedKeys = new ArrayList<>();
    private Tetrimino lastActedTetrimino;

    public GameInstance(Instance instance) throws IOException {
        this.instance = instance;
        this.gameWindow = instance.getGameWindow();
        highScore = instance.getHighScore();

        audioPlayer.setBGM(0);

        resizeVisuals();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void resizeVisuals() {
        if (gameBoard != null ) {
            this.remove(gameBoard);
        }
        if (gameDisplay != null) {
            this.remove(gameDisplay);
        }

        int topInset = gameWindow.getInsets().top;
        Dimension dimension = new Dimension(gameWindow.getSize().width, gameWindow.getSize().height - topInset);
        this.setSize(dimension);

        if (gameBoard != null) {
            Tetrimino[] tetriminos = gameBoard.getBoardTetriminos();
            Tetrimino activeTetrimino = gameBoard.getActiveTetrimino();
            gameBoard = new GameBoard(dimension, instance.isGhostEnabled());
            if (activeTetrimino != null) {
                gameBoard.add(activeTetrimino);
                gameBoard.setActiveTetrimino(activeTetrimino);
            }
            for (Tetrimino tetrimino : tetriminos) {
                gameBoard.add(tetrimino);
            }
        } else {
            gameBoard = new GameBoard(dimension, instance.isGhostEnabled());
        }

        gameDisplay = new GameDisplay(dimension, gameBoard.getTileSize());

        this.add(gameBoard);
        this.add(gameDisplay);
    }

    public void loadSprites() throws IOException {
        OTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/O_Tetrimino_Sprite.png")));
        JTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/J_Tetrimino_Sprite.png")));
        TTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/T_Tetrimino_Sprite.png")));
        LTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/L_Tetrimino_Sprite.png")));
        STetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/S_Tetrimino_Sprite.png")));
        ZTetriminoSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/Z_Tetrimino_Sprite.png")));
        ITetriminoSprite1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/I_Tetrimino_Sprite_1.png")));
        ITetriminoSprite2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/I_Tetrimino_Sprite_2.png")));
        ITetriminoSprite3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Tetriminos/I_Tetrimino_Sprite_3.png")));
    }

    public Tetrimino createTerimino(int tetriminoNum) {
        int xSpawn = 4;
        int ySpawn = 1;
        Tetrimino tetrimino = null;

        switch (tetriminoNum) {
            case 0 -> tetrimino = new ITetrimino(xSpawn, ySpawn, ITetriminoSprite1, ITetriminoSprite2, ITetriminoSprite3);
            case 1 -> tetrimino = new OTetrimino(xSpawn, ySpawn, OTetriminoSprite);
            case 2 -> tetrimino = new JTetrimino(xSpawn, ySpawn, JTetriminoSprite);
            case 3 -> tetrimino = new LTetrimino(xSpawn, ySpawn, LTetriminoSprite);
            case 4 -> tetrimino = new STetrimino(xSpawn, ySpawn, STetriminoSprite);
            case 5 -> tetrimino = new ZTetrimino(xSpawn, ySpawn, ZTetriminoSprite);
            case 6 -> tetrimino = new TTetrimino(xSpawn, ySpawn, TTetriminoSprite);
        }

        return tetrimino;
    }

    public void spawnTetrimino() throws IOException {
        gameBoard.setActiveTetrimino(createTerimino(nextTetriminoNum));

        gameBoard.add(gameBoard.getActiveTetrimino());

        lastActedTetrimino = gameBoard.getActiveTetrimino();

        nextTetriminoNum = (int)(Math.random() * 7);

        checkForGameOver();
    }

    public void checkForGameOver() {
        for (TetriminoNode tetriminoNode : gameBoard.getBoardTetriminoNodes()) {
            if (gameBoard.getActiveTetrimino() != null
                    && tetriminoNode.getXPos() == gameBoard.getActiveTetrimino().getXPos()
                    && tetriminoNode.getYPos() == gameBoard.getActiveTetrimino().getYPos()) {
                audioPlayer.playClip(1);
                gameOver();
            }
        }
    }

    public void gameOver() {
        audioPlayer.stopBGM();
        try {
            Thread.sleep(2000);

            audioPlayer.playClip(10);

            gameOver = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {
        gameOver = false;

        this.remove(gameBoard);
        this.remove(gameDisplay);
        gameBoard = new GameBoard(gameWindow.getSize(), instance.isGhostEnabled());
        this.add(gameBoard);
        this.add(gameDisplay);
        instance.setWindow();
        score = 0;
        clearedLines = 0;
        level = 0;

        updateGameDisplay();
    }

    public void togglePause() {
        if (paused) {
            audioPlayer.playBGM();
            gameBoard.setGameBoardImage(null);
            updateGameDisplay();
            paused = false;
        } else {
            try {
                audioPlayer.stopBGM();
                audioPlayer.playClip(11);
                BufferedImage gamePausedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/pause_board.png")));
                gameDisplay.setQueuedTetrimino(null);
                gameBoard.setGameBoardImage(gamePausedImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            paused = true;
        }
    }

    public void attemptToMoveDown(Tetrimino tetrimino) {
        int preMoveYPos = tetrimino.getYPos();
        tetrimino.moveDown();
        fallTime = 0;
        if (gameBoard.outOfVirBounds(tetrimino)
                || gameBoard.blocking(tetrimino)) {
            tetrimino.moveUp();
        }
        if (preMoveYPos == tetrimino.getYPos()) {
            score += softDropNum;
            gameBoard.setActiveTetrimino(null);
            softDropNum = 0;
            audioPlayer.playClip(6);
        }
    }

    public void updateGameDisplay() {
        gameDisplay.setQueuedTetrimino(createTerimino(nextTetriminoNum));
        gameDisplay.setScore(score);
        gameDisplay.setHighScore(highScore);
        gameDisplay.setRowsCleared(clearedLines);
        gameDisplay.setLevel(level);
    }

    public void update() {
        gameWindow.setResizable(false);

        if (!gameOver && !paused) {
            TetriminoNode[] queuedClears = gameBoard.getQueuedClears();

            int numOfRowClears = queuedClears.length / gameBoard.getBoardTileWidth();

            if (numOfRowClears > 0) {
                clearedLines += numOfRowClears;

                int prevLevel = level;
                if (level < 9) {
                    setLevel(clearedLines / 10);
                } else {
                    setLevel((clearedLines + extraClearedLines) / 20);
                }
                if (prevLevel < level) {
                    if (level == 9) {
                        extraClearedLines = clearedLines;
                    }
                    audioPlayer.playClip(2);
                }

                int pointValue;

                if (numOfRowClears == 4) {
                    pointValue = 1200;
                    audioPlayer.playClip(9);
                } else if (numOfRowClears == 3) {
                    pointValue = 300;
                    audioPlayer.playClip(3);
                } else if (numOfRowClears == 2) {
                    pointValue = 100;
                    audioPlayer.playClip(3);
                } else {
                    pointValue = 40;
                    audioPlayer.playClip(3);
                }
                score += pointValue * (level+1);
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
                audioPlayer.playClip(7);
            }

            gameBoard.clearQueuedClears();
            if (gameBoard.getActiveTetrimino() == null) {
                try {
                    if (score > instance.getHighScore()) {
                        instance.setHighScore(score);
                        highScore = instance.getHighScore();
                    }

                    spawnTetrimino();
                    updateGameDisplay();
                } catch (IOException ignored) {
                }
            }

        } else if (gameOver) {
            try {
                BufferedImage gameOverBoard = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/Menu/game_over_board.png")));
                gameBoard.setGameBoardImage(gameOverBoard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        gameBoard.updateTetriminoTileSize();
        gameDisplay.updateTetriminoTileSize();

        gameWindow.setResizable(true);
    }

    public boolean isRunning() {
        return running;
    }

    public void run() {
        gameBoard.setGhostPieceEnabled(instance.isGhostEnabled());
        audioPlayer.setVolume(instance.getVolume());
        audioPlayer.playBGM();
        running = true;
        gameWindow.addKeyListener(this);
        gameWindow.requestFocus();
        try {
            loadSprites();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            spawnTetrimino();
            updateGameDisplay();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                if (gameBoard.getActiveTetrimino() != null
                        && !gameOver
                        && !paused) {
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
        audioPlayer.close();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (running) {
            try {
                if (!pressedKeys.contains(e.getKeyCode())) {
                    pressedKeys.add(e.getKeyCode());
                }

                if (gameOver) {
                    audioPlayer.resetBGM();
                    audioPlayer.playBGM();
                    reset();
                    gameOver = false;
                    pressedKeys.clear();
                }

                Tetrimino activeTetrimino = gameBoard.getActiveTetrimino();

                if (gameBoard.getActiveTetrimino() != lastActedTetrimino) {
                    pressedKeys.clear();
                }
                lastActedTetrimino = null;

                for (int keyCode : pressedKeys) {
                    if (keyCode == KeyEvent.VK_SPACE) {
                        togglePause();
                        pressedKeys.clear();
                    }

                    if (keyCode == KeyEvent.VK_ESCAPE
                            && !paused) {
                        instance.setWantsMainMenu(true);
                        running = false;
                    }

                    if (activeTetrimino != null
                            && !paused
                            && !gameOver) {

                        if (keyCode == KeyEvent.VK_0) {
                            audioPlayer.resetBGM();
                            reset();
                        }
                        if (keyCode == KeyEvent.VK_A
                                || keyCode == KeyEvent.VK_LEFT) {
                            activeTetrimino.moveLeft();
                            if (gameBoard.outOfHorBounds(activeTetrimino)
                                    || gameBoard.blocking(activeTetrimino)) {
                                activeTetrimino.moveRight();
                            } else {
                                audioPlayer.playClip(5);
                            }
                            lastActedTetrimino = gameBoard.getActiveTetrimino();
                        }
                        if (keyCode == KeyEvent.VK_D
                                || keyCode == KeyEvent.VK_RIGHT) {
                            activeTetrimino.moveRight();
                            if (gameBoard.outOfHorBounds(activeTetrimino)
                                    || gameBoard.blocking(activeTetrimino)) {
                                activeTetrimino.moveLeft();
                            } else {
                                audioPlayer.playClip(5);
                            }
                            lastActedTetrimino = gameBoard.getActiveTetrimino();
                        }
                        if (keyCode == KeyEvent.VK_S
                                || keyCode == KeyEvent.VK_DOWN) {
                            attemptToMoveDown(activeTetrimino);
                            softDropNum++;
                            lastActedTetrimino = gameBoard.getActiveTetrimino();
                        }
                        if ((keyCode == KeyEvent.VK_W
                                || keyCode == KeyEvent.VK_UP)
                                && instance.isHardDropEnabled()) {
                            score += gameBoard.hardDrop(activeTetrimino);
                            gameBoard.setActiveTetrimino(null);
                            audioPlayer.playClip(6);
                        }
                        if (keyCode == KeyEvent.VK_Q
                                || keyCode == KeyEvent.VK_X) {
                            activeTetrimino.rotateLeft();
                            if (gameBoard.blocking(activeTetrimino)
                                    || gameBoard.outOfHorBounds(activeTetrimino)
                                    || gameBoard.outOfVirBounds(activeTetrimino)) {
                                activeTetrimino.rotateRight();
                            } else {
                                audioPlayer.playClip(8);
                            }
                        }
                        if (keyCode == KeyEvent.VK_E
                                || keyCode == KeyEvent.VK_C) {
                            activeTetrimino.rotateRight();
                            if (gameBoard.blocking(activeTetrimino)
                                    || gameBoard.outOfHorBounds(activeTetrimino)
                                    || gameBoard.outOfVirBounds(activeTetrimino)) {
                                activeTetrimino.rotateLeft();
                            } else {
                                audioPlayer.playClip(8);
                            }
                        }
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        lastActedTetrimino = gameBoard.getActiveTetrimino();

        if (e.getKeyCode() == KeyEvent.VK_S
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            softDropNum = 0;
        }

        pressedKeys.remove((Integer) e.getKeyCode());
    }
}
