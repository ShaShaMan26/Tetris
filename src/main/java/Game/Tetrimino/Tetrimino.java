package Game.Tetrimino;

import java.awt.*;

public abstract class Tetrimino extends Component {
    private int xPos;
    private int yPos;
    private Dimension origin;
    private TetriminoNode[] tetriminoNodes;

    public Tetrimino(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setOrigin(Dimension origin) {
        this.origin = origin;
    }

    public void setTetriminoNodes(TetriminoNode[] tetriminoNodes) {
        this.tetriminoNodes = tetriminoNodes;
    }

    public TetriminoNode[] getTetriminoNodes() {
        return tetriminoNodes;
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

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
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
