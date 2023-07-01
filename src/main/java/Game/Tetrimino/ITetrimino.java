package Game.Tetrimino;

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
        tetriminoNodes[0].setSprite(sprite1);
        tetriminoNodes[1].setSprite(sprite2);
        tetriminoNodes[2].setSprite(sprite2);
        tetriminoNodes[3].setSprite(sprite3);
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
}
