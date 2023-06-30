package Game;

import Game.Tetrimino.Tetrimino;
import Game.Tetrimino.TetriminoNode;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JComponent {
    private final int boardWidth;
    private final int boardHeight;
    private final int tileSize;
    private Tetrimino activeTetrimino;

    public GameBoard(Dimension displayDimension) {
        tileSize = displayDimension.height / 20;
        boardWidth = tileSize * 10;
        boardHeight = displayDimension.height;
        int displayCenter = displayDimension.width / 2;

        this.setBounds(displayCenter - (boardWidth / 2), 0, boardWidth, boardHeight);
    }

    public void setActiveTetrimino(Tetrimino tetrimino) {
        this.add(tetrimino);
        this.activeTetrimino = tetrimino;
    }

    public Tetrimino getActiveTetrimino() {
        return activeTetrimino;
    }

    public int getBoardHeight() {
        return boardHeight / tileSize;
    }

    public int getBoardWidth() {
        return boardWidth / tileSize;
    }

    public boolean isTetriminoBelow(Tetrimino tetrimino) {
        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino
                    && component != tetrimino) {
                for (TetriminoNode tetriminoComponentNode : ((Tetrimino) component).getTetriminoNodes()) {
                    for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
                        if (tetriminoNode.getXPos() == tetriminoComponentNode.getXPos()
                                && tetriminoComponentNode.getYPos() == tetriminoNode.getYPos() + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isTetriminoToTheRightOf(Tetrimino tetrimino) {
        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino
                    && component != tetrimino) {
                for (TetriminoNode tetriminoComponentNode : ((Tetrimino) component).getTetriminoNodes()) {
                    for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
                        if (tetriminoNode.getYPos() == tetriminoComponentNode.getYPos()
                                && tetriminoComponentNode.getXPos() == tetriminoNode.getXPos() + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isTetriminoToTheLeftOf(Tetrimino tetrimino) {
        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino
                    && component != tetrimino) {
                for (TetriminoNode tetriminoComponentNode : ((Tetrimino) component).getTetriminoNodes()) {
                    for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
                        if (tetriminoNode.getYPos() == tetriminoComponentNode.getYPos()
                                && tetriminoComponentNode.getXPos() == tetriminoNode.getXPos() - 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int getLowestDropOf(Tetrimino tetrimino) {
        int lowestDrop = getBoardHeight() - 1;

        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino
                    && component != tetrimino) {
                for (TetriminoNode tetriminoComponentNode : ((Tetrimino) component).getTetriminoNodes()) {
                    for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
                        if (tetriminoNode.getXPos() == tetriminoComponentNode.getXPos()
                                && tetriminoComponentNode.getYPos() - 1 < lowestDrop) {
                            lowestDrop = tetriminoComponentNode.getYPos() - 1;
                        }
                    }
                }
            }
        }
        return lowestDrop;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, boardWidth, boardHeight);

        for (Component component : this.getComponents()) {
            component.paint(g);
        }
    }
}
