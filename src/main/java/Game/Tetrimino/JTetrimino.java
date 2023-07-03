package Game.Tetrimino;

import java.awt.image.BufferedImage;

public class JTetrimino extends Tetrimino{

    public JTetrimino(int xPos, int yPos, BufferedImage sprite) {
        super(xPos, yPos, sprite);

        checkRotation();
    }

    @Override
    public void checkRotation() {
        TetriminoNode[] tetriminoNodes = new TetriminoNode[4];

        int xPos = getXPos();
        int yPos = getYPos();

        switch (getRotation()) {
            case 0 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[1] = new TetriminoNode(xPos+1, yPos);
                tetriminoNodes[2] = new TetriminoNode(xPos-1, yPos);
                tetriminoNodes[3] = new TetriminoNode(xPos+1, yPos+1);
            }
            case 1 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[1] = new TetriminoNode(xPos, yPos-1);
                tetriminoNodes[2] = new TetriminoNode(xPos, yPos+1);
                tetriminoNodes[3] = new TetriminoNode(xPos-1, yPos+1);
            }
            case 2 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[1] = new TetriminoNode(xPos+1, yPos);
                tetriminoNodes[2] = new TetriminoNode(xPos-1, yPos);
                tetriminoNodes[3] = new TetriminoNode(xPos-1, yPos-1);
            }
            case 3 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[1] = new TetriminoNode(xPos, yPos+1);
                tetriminoNodes[2] = new TetriminoNode(xPos, yPos-1);
                tetriminoNodes[3] = new TetriminoNode(xPos+1, yPos-1);
            }
        }

        setTetriminoNodes(tetriminoNodes);
        setNodeSprites();
    }
}
