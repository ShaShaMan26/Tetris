package Game.Tetrimino;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Tetrimino extends Component {
    private int xPos;
    private int yPos;
    private Point origin;
    private TetriminoNode[] tetriminoNodes;

    public Tetrimino(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.origin = new Point(xPos, yPos);
    }

    public void setSprite(BufferedImage sprite) {
        for (TetriminoNode tetriminoNode : getTetriminoNodes()) {
            tetriminoNode.setSprite(sprite);
        }
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
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

    public void hardDrop(int yPos) {
        for (TetriminoNode tetriminoNode : tetriminoNodes) {
            tetriminoNode.moveDownBy(yPos - this.yPos);
        }

        this.yPos = yPos;
    }

    public Point getOrigin() {
        return origin;
    }

    public abstract void rotateLeft();
    public abstract void rotateRight();

    public void paint(Graphics g) {
        super.paint(g);

        for (TetriminoNode tetriminoNode : tetriminoNodes) {
            tetriminoNode.paint(g);
        }
    }
}
