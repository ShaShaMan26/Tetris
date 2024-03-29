package Game;

import Game.Tetrimino.Tetrimino;
import Game.Tetrimino.TetriminoNode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard extends JComponent {
    private final int boardWidth;
    private final int boardHeight;
    private final int tileSize;
    private Tetrimino activeTetrimino;
    private boolean ghostPieceEnabled;
    private BufferedImage gameBoardImage;

    public GameBoard(Dimension displayDimension, boolean ghostPieceEnabled) {
        this.ghostPieceEnabled = ghostPieceEnabled;

        tileSize = displayDimension.height / 20;
        boardWidth = tileSize * 10;
        boardHeight = displayDimension.height;
        int displayCenter = displayDimension.width / 2;

        this.setBounds(displayCenter - (boardWidth / 2), 0, boardWidth, boardHeight);
    }

    public void setGhostPieceEnabled(boolean ghostPieceEnabled) {
        this.ghostPieceEnabled = ghostPieceEnabled;
    }

    public void setActiveTetrimino(Tetrimino tetrimino) {
        this.activeTetrimino = tetrimino;
    }

    public Tetrimino getActiveTetrimino() {
        return activeTetrimino;
    }

    public TetriminoNode[] getBoardTetriminoNodes() {
        ArrayList<TetriminoNode> tetriminoNodes = new ArrayList<>();

        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino
                    && component != activeTetrimino) {
                for (TetriminoNode tetriminoNode : ((Tetrimino) component).getTetriminoNodes()) {
                    if (tetriminoNode.getActive()) {
                        tetriminoNodes.add(tetriminoNode);
                    }
                }
            }
        }

        TetriminoNode[] finalTetriminoNodes = new TetriminoNode[tetriminoNodes.size()];

        for (int i = 0; i < tetriminoNodes.size(); i++) {
            finalTetriminoNodes[i] = tetriminoNodes.get(i);
        }

        return finalTetriminoNodes;
    }

    public Tetrimino[] getBoardTetriminos() {
        ArrayList<Tetrimino> tetriminos = new ArrayList<>();

        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino
                    && component != activeTetrimino) {
                tetriminos.add((Tetrimino) component);
            }
        }

        Tetrimino[] finalTetriminos = new Tetrimino[tetriminos.size()];

        for (int i = 0; i < tetriminos.size(); i++) {
            finalTetriminos[i] = tetriminos.get(i);
        }

        return finalTetriminos;
    }

    public TetriminoNode[] getTetriminoNodes() {
        ArrayList<TetriminoNode> tetriminoNodes = new ArrayList<>();

        for (Component component : this.getComponents()) {
            if (component instanceof Tetrimino) {
                for (TetriminoNode tetriminoNode : ((Tetrimino) component).getTetriminoNodes()) {
                    if (tetriminoNode.getActive()) {
                        tetriminoNodes.add(tetriminoNode);
                    }
                }
            }
        }

        TetriminoNode[] finalTetriminoNodes = new TetriminoNode[tetriminoNodes.size()];

        for (int i = 0; i < tetriminoNodes.size(); i++) {
            finalTetriminoNodes[i] = tetriminoNodes.get(i);
        }

        return finalTetriminoNodes;
    }

    public int getBoardTileHeight() {
        return boardHeight / tileSize;
    }

    public int getBoardTileWidth() {
        return boardWidth / tileSize;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setGameBoardImage(BufferedImage gameBoardImage) {
        this.gameBoardImage = gameBoardImage;
    }

    public void updateTetriminoTileSize() {
        for (TetriminoNode tetriminoNode : getTetriminoNodes()) {
            tetriminoNode.setSideLength(tileSize);
        }
    }

    public void clearQueuedClears() {
        TetriminoNode[] nodesToBeCleared = getQueuedClears();

        for (TetriminoNode tetriminoNode : nodesToBeCleared) {
            tetriminoNode.setActive(false);
        }

        for (int row = getBoardTileHeight()-1; row > -1; row--) {
            if (getNodesOf(row).length == 0) {
                for (TetriminoNode tetriminoNode : getBoardTetriminoNodes()) {
                    if (tetriminoNode.getYPos() < row) {
                        tetriminoNode.moveDown();
                    }
                }
            }
        }
    }

    public TetriminoNode[] getQueuedClears() {
        ArrayList<TetriminoNode> nodesToBeCleared = new ArrayList<>();

        for (int row = 0; row < getBoardTileHeight(); row++) {
            TetriminoNode[] nodesAtRow = getNodesOf(row);

            if (nodesAtRow.length == getBoardTileWidth()) {
                nodesToBeCleared.addAll(Arrays.stream(nodesAtRow).toList());
            }
        }

        TetriminoNode[] finalNodesToBeCleared = new TetriminoNode[nodesToBeCleared.size()];

        for (int i = 0; i < nodesToBeCleared.size(); i++) {
            finalNodesToBeCleared[i] = nodesToBeCleared.get(i);
        }

        return finalNodesToBeCleared;
    }

    public TetriminoNode[] getNodesOf(int row) {
        ArrayList<TetriminoNode> nodesInRow = new ArrayList<>();

        for (TetriminoNode tetriminoNode : getBoardTetriminoNodes()) {
            if (tetriminoNode.getYPos() == row) {
                nodesInRow.add(tetriminoNode);
            }
        }

        TetriminoNode[] finalNodesInRow = new TetriminoNode[nodesInRow.size()];

        for (int i = 0; i < nodesInRow.size(); i++) {
            finalNodesInRow[i] = nodesInRow.get(i);
        }

        return finalNodesInRow;
    }

    public int hardDrop(Tetrimino tetrimino) {
        int fallDistance = -2;

        while (!outOfVirBounds(tetrimino)
                && !blocking(tetrimino)) {
            tetrimino.moveDown();
            fallDistance += 2;
        }
        tetrimino.moveUp();

        return fallDistance;
    }

    public boolean blocking(Tetrimino activeTetrimino) {
        for (TetriminoNode activeTetriminoNode : activeTetrimino.getTetriminoNodes()) {
            for (TetriminoNode tetriminoNode : getBoardTetriminoNodes()) {
                if (activeTetriminoNode.getYPos() == tetriminoNode.getYPos()
                        && activeTetriminoNode.getXPos() == tetriminoNode.getXPos()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean outOfHorBounds(Tetrimino tetrimino) {
        for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
            if (tetriminoNode.getXPos() > getBoardTileWidth() - 1 || tetriminoNode.getXPos() < 0) {
                return true;
            }
        }

        return false;
    }

    public boolean outOfVirBounds(Tetrimino tetrimino) {
        for (TetriminoNode tetriminoNode : tetrimino.getTetriminoNodes()) {
            if (tetriminoNode.getYPos() > getBoardTileHeight() - 1) {
                return true;
            }
        }

        return false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(248,248,248));
        g.fillRect(0, 0, boardWidth, boardHeight);

        if (ghostPieceEnabled && activeTetrimino != null) {
            int yPos = activeTetrimino.getYPos();

            hardDrop(activeTetrimino);
            float alpha = 0.4F;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
            ((Graphics2D) g).setComposite(ac);

            activeTetrimino.paint(g);

            activeTetrimino.setYPos(yPos);

            alpha = 1;
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
            ((Graphics2D) g).setComposite(ac);
        }

        for (Component component : this.getComponents()) {
            component.paint(g);
        }

        if (gameBoardImage != null) {
            g.drawImage(gameBoardImage, 0, 0, boardWidth, boardHeight, null);
        }
    }
}
