package Game;

import Game.Tetrimino.Tetrimino;
import Game.Tetrimino.TetriminoNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    public Tetrimino[] getTetriminos() {
        ArrayList<Tetrimino> tetriminos = new ArrayList<>();

        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino) {
                tetriminos.add((Tetrimino) component);
            }
        }

        Tetrimino[] finalTetriminos = new Tetrimino[tetriminos.size()];

        for (int i = 0; i < tetriminos.size(); i++) {
            finalTetriminos[i] = tetriminos.get(i);
        }

        return finalTetriminos;
    }

    public int getBoardHeight() {
        return boardHeight / tileSize;
    }

    public int getBoardWidth() {
        return boardWidth / tileSize;
    }

    public void clearRow(int rowNum) {
        for (Tetrimino tetrimino : getTetriminos()) {
            for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
                if (tetriminoNode.getYPos() == rowNum) {
                    tetriminoNode.setActive(false);
                }
            }
        }

        for (Tetrimino tetrimino : getTetriminos()) {
            for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
                if (tetriminoNode.getYPos() < rowNum) {
                    tetriminoNode.moveDown();
                }
            }
        }
    }

    public void checkForRowClears() {
        int numOfNodes = 0;

        for (int row = 0; row < getBoardHeight(); row++) {
            for (Tetrimino tetrimino : getTetriminos()) {
                for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
                    if (tetriminoNode.getYPos() == row) {
                        numOfNodes++;
                    }
                }
            }
            if (numOfNodes == getBoardWidth()) {
                clearRow(row);
            }
            numOfNodes = 0;
        }
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

    public boolean aboveVirBound(Tetrimino tetrimino) {
        for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
            if (tetriminoNode.getYPos() >= getBoardHeight() - 1) {
                return false;
            }
        }

        return true;
    }

    public boolean leftOfHorBounds(Tetrimino tetrimino) {
        for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
            if (tetriminoNode.getXPos() >= getBoardWidth() - 1) {
                return false;
            }
        }
        return true;
    }

    public boolean rightOfHorBounds(Tetrimino tetrimino) {
        for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
            if (tetriminoNode.getXPos() < 1) {
                return false;
            }
        }
        return true;
    }

    public void hardDrop(Tetrimino tetrimino) {
        while (aboveVirBound(tetrimino) && !isTetriminoBelow(tetrimino)) {
            tetrimino.moveDown();
        }
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
