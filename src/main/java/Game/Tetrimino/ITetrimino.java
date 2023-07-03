package Game.Tetrimino;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ITetrimino extends Tetrimino{
    BufferedImage sprite1, sprite2, sprite3;

    public ITetrimino(int xPos, int yPos, BufferedImage sprite1, BufferedImage sprite2, BufferedImage sprite3) {
        super(xPos, yPos, sprite1);

        this.sprite1 = sprite1;
        this.sprite2 = sprite2;
        this.sprite3 = sprite3;

        checkRotation();
    }

    @Override
    public void setNodeSprites() {
        TetriminoNode[] tetriminoNodes = getTetriminoNodes();

        if (getRotation() == 0 || getRotation() == 2) {
            tetriminoNodes[0].setSprite(sprite1);
            tetriminoNodes[1].setSprite(sprite2);
            tetriminoNodes[2].setSprite(sprite2);
            tetriminoNodes[3].setSprite(sprite3);
        } else {
            tetriminoNodes[0].setSprite(rotate(sprite1));
            tetriminoNodes[1].setSprite(rotate(sprite2));
            tetriminoNodes[2].setSprite(rotate(sprite2));
            tetriminoNodes[3].setSprite(rotate(sprite3));
        }
    }

    @Override
    public void checkRotation() {
        TetriminoNode[] tetriminoNodes = new TetriminoNode[4];

        int xPos = getXPos();
        int yPos = getYPos();

        switch (getRotation()) {
            case 0, 2 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos-1, yPos);
                tetriminoNodes[1] = new TetriminoNode(xPos+1, yPos);
                tetriminoNodes[2] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[3] = new TetriminoNode(xPos+2, yPos);
            }
            case 1, 3 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos, yPos-1);
                tetriminoNodes[1] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[2] = new TetriminoNode(xPos, yPos+1);
                tetriminoNodes[3] = new TetriminoNode(xPos, yPos+2);
            }
        }

        setTetriminoNodes(tetriminoNodes);
        setNodeSprites();
    }

    // stolen from https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
    public static BufferedImage rotate(BufferedImage bimg) {

        int w = bimg.getWidth();
        int h = bimg.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(90), w/2, h/2);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.dispose();
        return rotated;
    }
}
