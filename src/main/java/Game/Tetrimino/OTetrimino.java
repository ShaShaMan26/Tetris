package Game.Tetrimino;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OTetrimino extends Tetrimino {

    public OTetrimino(int xPos, int yPos) throws IOException {
        super(xPos, yPos);

        TetriminoNode[] tetriminoNodes = new TetriminoNode[4];

        tetriminoNodes[0] = new TetriminoNode(xPos, yPos);
        tetriminoNodes[1] = new TetriminoNode(xPos+1, yPos);
        tetriminoNodes[2] = new TetriminoNode(xPos+1, yPos+1);
        tetriminoNodes[3] = new TetriminoNode(xPos, yPos+1);

        this.setTetriminoNodes(tetriminoNodes);

        this.setSprite(ImageIO.read(getClass().getResourceAsStream("/Tetriminos/O_Tetrimino_Sprite.png")));
    }

    @Override
    public void checkRotation() {

    }
}
