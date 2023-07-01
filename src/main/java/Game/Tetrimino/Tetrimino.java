package Game.Tetrimino;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Tetrimino extends Component {
    private int rotation = 0;
    private int xPos;
    private int yPos;
    private TetriminoNode[] tetriminoNodes;

    public Tetrimino(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setSprite(BufferedImage sprite) {
        for (TetriminoNode tetriminoNode : getTetriminoNodes()) {
            tetriminoNode.setSprite(sprite);
        }
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getRotation() {
        return rotation;
    }

    public void setTetriminoNodes(TetriminoNode[] tetriminoNodes) {
        this.tetriminoNodes = tetriminoNodes;
    }

    public TetriminoNode[] getTetriminoNodes() {

        ArrayList<TetriminoNode> tetriminoNodes = new ArrayList<>();

        for (TetriminoNode tetriminoNode : this.tetriminoNodes) {
            if (tetriminoNode.getActive()) {
                tetriminoNodes.add(tetriminoNode);
            }
        }

        TetriminoNode[] finalTetriminoNodes = new TetriminoNode[tetriminoNodes.size()];

        for (int i = 0; i < tetriminoNodes.size(); i++) {
            finalTetriminoNodes[i] = tetriminoNodes.get(i);
        }

        return finalTetriminoNodes;
    }

    public void moveRight() {
        xPos++;

        for (TetriminoNode tetriminoNode : tetriminoNodes) {
            tetriminoNode.moveRight();
        }
    }

    public void moveLeft() {
        xPos--;

        for (TetriminoNode tetriminoNode : tetriminoNodes) {
            tetriminoNode.moveLeft();
        }
    }

    public void moveDown() {
        yPos++;

        for (TetriminoNode tetriminoNode : tetriminoNodes) {
            tetriminoNode.moveDown();
        }
    }

    public abstract void checkRotation() throws IOException;

    public void rotateLeft() throws IOException {
        rotation--;

        if (rotation < 0) {
            rotation = 3;
        }

        checkRotation();
    }
    public void rotateRight() throws IOException {
        rotation++;

        if (rotation > 3) {
            rotation = 0;
        }

        checkRotation();
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (TetriminoNode tetriminoNode : tetriminoNodes) {
            tetriminoNode.paint(g);
        }
    }
}
