package Game.Tetrimino;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TetriminoNode extends Component {
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

    public void moveDownBy(int numOfRows) {
        rowNum += numOfRows;
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.YELLOW);
        g.fillRect(colNum*SIDELENGTH, rowNum*SIDELENGTH, SIDELENGTH, SIDELENGTH);
    }
}
