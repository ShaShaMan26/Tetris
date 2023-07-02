package Game.Tetrimino;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TetriminoNode extends Component {
    private boolean active = true;
    private boolean displayed = true;
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

    public int getYPos() {
        return rowNum;
    }

    public int getXPos() {
        return colNum;
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

    public void toggleDisplayed() {
        displayed = !displayed;
    }

    public void paint(Graphics g) {
        if (active && displayed) {
            super.paint(g);

            g.drawImage(sprite, colNum * SIDELENGTH, rowNum * SIDELENGTH, SIDELENGTH, SIDELENGTH, null);
        }
    }
}
