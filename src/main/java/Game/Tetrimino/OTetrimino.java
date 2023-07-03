package Game.Tetrimino;

import java.awt.image.BufferedImage;

public class OTetrimino extends Tetrimino {

    public OTetrimino(int xPos, int yPos, BufferedImage sprite) {
        super(xPos, yPos, sprite);

        TetriminoNode[] tetriminoNodes = new TetriminoNode[4];

        tetriminoNodes[0] = new TetriminoNode(xPos, yPos);
        tetriminoNodes[1] = new TetriminoNode(xPos+1, yPos);
        tetriminoNodes[2] = new TetriminoNode(xPos+1, yPos+1);
        tetriminoNodes[3] = new TetriminoNode(xPos, yPos+1);

        setTetriminoNodes(tetriminoNodes);

        checkRotation();
    }

    @Override
    public void checkRotation() {
        setNodeSprites();
    }
}
