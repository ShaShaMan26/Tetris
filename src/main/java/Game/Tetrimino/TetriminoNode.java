package Game.Tetrimino;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TetriminoNode extends Component {
    private boolean active = true;
    private boolean displayed = true;
    private BufferedImage sprite;
    private int sideLength = 0;
    private int colNum;
    private int rowNum;

    public TetriminoNode(int colNum, int rowNum) {
        this.colNum = colNum;
        this.rowNum = rowNum;
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

    public void setYPos(int yPos) {
        this.rowNum = yPos;
    }

    public void setXPos(int xPos) {
        this.colNum = xPos;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void toggleDisplayed() {
        displayed = !displayed;
    }

    public void paint(Graphics g) {
        if (active && displayed) {
            super.paint(g);

            g.drawImage(sprite, colNum * sideLength, rowNum * sideLength, sideLength, sideLength, null);
        }
    }
}
