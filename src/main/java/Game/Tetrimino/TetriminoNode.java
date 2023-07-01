package Game.Tetrimino;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TetriminoNode extends Component {
    private boolean active = true;
    private BufferedImage sprite;
    private final int SIDELENGTH;
    private int colNum;
    private int rowNum;

    TetriminoNode(int colNum, int rowNum) {
        this.colNum = colNum;
        this.rowNum = rowNum;
        SIDELENGTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().height / 20;
    }

    public void moveRight() {
        colNum++;
    }

    public void moveLeft() {
        colNum--;
    }

    public void moveDown() {
        rowNum++;
    }

    public void moveUp() {
        rowNum--;
    }

    public int getYPos() {
        return rowNum;
    }

    public int getXPos() {
        return colNum;
    }

    public void moveDownBy(int numOfRows) {
        rowNum += numOfRows;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void paint(Graphics g) {
        if (active) {
            super.paint(g);

            g.drawImage(sprite, colNum * SIDELENGTH, rowNum * SIDELENGTH, SIDELENGTH, SIDELENGTH, null);
        }
    }
}
