package Game.Tetrimino;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OTetrimino extends Tetrimino {

    public OTetrimino(int xPos, int yPos, BufferedImage sprite) throws IOException {
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
    public void checkRotation() throws IOException {
        setNodeSprites();
    }
}
