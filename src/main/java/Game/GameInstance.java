package Game;

import Game.Tetrimino.*;
import Sound.AudioPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameInstance extends JComponent implements KeyListener {
    private int level = 0;
    private int score = 0;
    private int clearedLines = 0;
    private int softDropNum = 0;
    private boolean hardDropEnabled = false;
    private double fallTime = 0;
    private int nextTetriminoNum = (int)(Math.random() * 7);
    private final JFrame gameWindow;
    private GameBoard gameBoard;
    private GameDisplay gameDisplay;
    private BufferedImage OTetriminoSprite, JTetriminoSprite, TTetriminoSprite, LTetriminoSprite, STetriminoSprite,
            ZTetriminoSprite, ITetriminoSprite1, ITetriminoSprite2, ITetriminoSprite3;
    private AudioPlayer audioPlayer = new AudioPlayer();
    private final ArrayList<Integer> pressedKeys = new ArrayList<>();

    public GameInstance(JFrame gameWindow) throws IOException {
        this.gameWindow = gameWindow;
        this.setSize(gameWindow.getSize());

        startBGM();

        gameBoard = new GameBoard(gameWindow.getSize());
        gameDisplay = new GameDisplay(gameWindow.getSize(), gameBoard.getTileSize());

        this.add(gameBoard);
        this.add(gameDisplay);

        gameWindow.addKeyListener(this);
        requestFocus();

        loadSprites();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void levelUp() {
        if (level <= 20) {
            level++;
        }
        playSFX(2);
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

    public void startBGM() {
        audioPlayer.setClip(0);
        audioPlayer.play();
        audioPlayer.loop();
    }

    public void playSFX(int i) {
        audioPlayer.setClip(i);
        audioPlayer.play();
    }

    public void toggleHardDrop() {
        hardDropEnabled = !hardDropEnabled;
    }

    public void toggleGhostPiece() {
        gameBoard.toggleGhostPiece();
    }

    public Tetrimino createTerimino(int tetriminoNum) {
        int xSpawn = 4;
        int ySpawn = 1;
        Tetrimino tetrimino = null;

        switch (tetriminoNum) {
            case 0 -> {
                tetrimino = new ITetrimino(xSpawn, ySpawn, ITetriminoSprite1, ITetriminoSprite2, ITetriminoSprite3);
            }
            case 1 -> {
                tetrimino = new OTetrimino(xSpawn, ySpawn, OTetriminoSprite);
            }
            case 2 -> {
                tetrimino = new JTetrimino(xSpawn, ySpawn, JTetriminoSprite);
            }
            case 3 -> {
                tetrimino = new LTetrimino(xSpawn, ySpawn, LTetriminoSprite);
            }
            case 4 -> {
                tetrimino = new STetrimino(xSpawn, ySpawn, STetriminoSprite);
            }
            case 5 -> {
                tetrimino = new ZTetrimino(xSpawn, ySpawn, ZTetriminoSprite);
            }
            case 6 -> {
                tetrimino = new TTetrimino(xSpawn, ySpawn, TTetriminoSprite);
            }
        }

        return tetrimino;
    }

    public void spawnTetrimino() throws IOException {
        gameBoard.setActiveTetrimino(createTerimino(nextTetriminoNum));

        gameBoard.add(gameBoard.getActiveTetrimino());

        nextTetriminoNum = (int)(Math.random() * 7);
    }

    public void attemptToMoveDown(Tetrimino tetrimino) {
        int preMoveYPos = tetrimino.getYPos();
        tetrimino.moveDown();
        fallTime = 0;
        if (gameBoard.outOfVirBounds(tetrimino)
                || gameBoard.blocking(tetrimino)) {
            tetrimino.moveUp();
            softDropNum--;
        }
        if (preMoveYPos == tetrimino.getYPos()) {
            score += softDropNum;
            gameBoard.setActiveTetrimino(null);
            softDropNum = 0;
            playSFX(6);
        }
    }

    public void updateGameDisplay() {
        gameDisplay.setQueuedTetrimino(createTerimino(nextTetriminoNum));
    }

    public void update() {
        TetriminoNode[] queuedClears = gameBoard.getQueuedClears();

        int numOfRowClears = queuedClears.length / gameBoard.getBoardTileWidth();

        if (numOfRowClears > 0) {
            clearedLines += numOfRowClears;

            if (level < 9) {
                if ((clearedLines - (level*10)) % 10 == 0) {
                    levelUp();
                }
            } else {
                if ((clearedLines - (level*20)) % 20 == 0) {
                    levelUp();
                }
            }

            int pointValue;

            if (numOfRowClears == 4 * gameBoard.getBoardTileWidth()) {
                pointValue = 1200;
                playSFX(9);
            } else if (numOfRowClears == 3) {
                pointValue = 300;
                playSFX(3);
            } else if (numOfRowClears == 2) {
                pointValue = 100;
                playSFX(3);
            } else {
                pointValue = 40;
                playSFX(3);
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
            playSFX(7);
        }

        gameBoard.clearQueuedClears();

        if (gameBoard.getActiveTetrimino() == null) {
            try {
                spawnTetrimino();
            } catch (IOException ignored) {
            }
        }

        updateGameDisplay();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
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
    public synchronized void keyPressed(KeyEvent e) {
        if (!pressedKeys.contains(e.getKeyCode())) {
            pressedKeys.add(e.getKeyCode());
        }

        Tetrimino activeTetrimino = gameBoard.getActiveTetrimino();
        for (int keyCode : pressedKeys) {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }
            if (keyCode == KeyEvent.VK_R) {
                this.remove(gameBoard);
                this.remove(gameDisplay);
                gameBoard = new GameBoard(gameWindow.getSize());
                this.add(gameBoard);
                this.add(gameDisplay);
            }

            if (activeTetrimino != null) {
                if (keyCode == KeyEvent.VK_A
                        || keyCode == KeyEvent.VK_LEFT) {
                    activeTetrimino.moveLeft();
                    if (gameBoard.outOfHorBounds(activeTetrimino)
                            || gameBoard.blocking(activeTetrimino)) {
                        activeTetrimino.moveRight();
                    }
                    playSFX(5);
                }
                if (keyCode == KeyEvent.VK_D
                        || keyCode == KeyEvent.VK_RIGHT) {
                    activeTetrimino.moveRight();
                    if (gameBoard.outOfHorBounds(activeTetrimino)
                            || gameBoard.blocking(activeTetrimino)) {
                        activeTetrimino.moveLeft();
                    }
                    playSFX(5);
                }
                if (keyCode == KeyEvent.VK_S
                        || keyCode == KeyEvent.VK_DOWN) {
                    softDropNum++;
                    attemptToMoveDown(activeTetrimino);
                }
                if ((keyCode == KeyEvent.VK_W
                        || keyCode == KeyEvent.VK_UP)
                        && hardDropEnabled) {
                    gameBoard.hardDrop(activeTetrimino);
                    gameBoard.setActiveTetrimino(null);
                    playSFX(6);
                }
                if (keyCode == KeyEvent.VK_Q
                        || keyCode == KeyEvent.VK_X) {
                    try {
                        activeTetrimino.rotateLeft();
                        while (gameBoard.outOfVirBounds(activeTetrimino)) {
                            activeTetrimino.moveUp();
                        }
                        int movesUp = 0;
                        while (gameBoard.blocking(activeTetrimino)) {
                            activeTetrimino.moveUp();
                            movesUp++;
                            if (movesUp > 3) {
                                break;
                            }
                        }
                        while (gameBoard.outOfHorBounds(activeTetrimino)) {
                            if (activeTetrimino.getXPos() > gameBoard.getBoardTileWidth() / 2) {
                                activeTetrimino.moveLeft();
                            } else if (activeTetrimino.getXPos() < gameBoard.getBoardTileWidth() / 2) {
                                activeTetrimino.moveRight();
                            }
                            while (movesUp > 0) {
                                activeTetrimino.moveDown();
                                movesUp--;
                            }
                        }

                        if (gameBoard.blocking(activeTetrimino)) {
                            activeTetrimino.rotateRight();

                            while (gameBoard.outOfVirBounds(activeTetrimino)) {
                                activeTetrimino.moveUp();
                            }
                            while (gameBoard.outOfHorBounds(activeTetrimino)) {
                                if (activeTetrimino.getXPos() > gameBoard.getBoardTileWidth() / 2) {
                                    activeTetrimino.moveRight();
                                } else if (activeTetrimino.getXPos() < gameBoard.getBoardTileWidth() / 2) {
                                    activeTetrimino.moveLeft();
                                }
                            }
                        }
                        playSFX(8);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (keyCode == KeyEvent.VK_E
                        || keyCode == KeyEvent.VK_C) {
                    try {
                        activeTetrimino.rotateRight();

                        while (gameBoard.outOfVirBounds(activeTetrimino)) {
                            activeTetrimino.moveUp();
                        }
                        int movesUp = 0;
                        while (gameBoard.blocking(activeTetrimino)) {
                            activeTetrimino.moveUp();
                            movesUp++;
                            if (movesUp > 3) {
                                break;
                            }
                        }
                        while (gameBoard.outOfHorBounds(activeTetrimino)) {
                            if (activeTetrimino.getXPos() > gameBoard.getBoardTileWidth() / 2) {
                                activeTetrimino.moveLeft();
                            } else if (activeTetrimino.getXPos() < gameBoard.getBoardTileWidth() / 2) {
                                activeTetrimino.moveRight();
                            }
                            while (movesUp > 0) {
                                activeTetrimino.moveDown();
                                movesUp--;
                            }
                        }

                        if (gameBoard.blocking(activeTetrimino)) {
                            activeTetrimino.rotateLeft();

                            while (gameBoard.outOfVirBounds(activeTetrimino)) {
                                activeTetrimino.moveUp();
                            }
                            while (gameBoard.outOfHorBounds(activeTetrimino)) {
                                if (activeTetrimino.getXPos() > gameBoard.getBoardTileWidth() / 2) {
                                    activeTetrimino.moveRight();
                                } else if (activeTetrimino.getXPos() < gameBoard.getBoardTileWidth() / 2) {
                                    activeTetrimino.moveLeft();
                                }
                            }
                        }
                        playSFX(8);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (keyCode == KeyEvent.VK_1) {
                    toggleHardDrop();
                }
                if (keyCode == KeyEvent.VK_2) {
                    toggleGhostPiece();
                }
            }
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressedKeys.remove((Integer) e.getKeyCode());
    }
}
