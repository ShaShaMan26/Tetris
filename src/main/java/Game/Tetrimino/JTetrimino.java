package Game.Tetrimino;

import javax.imageio.ImageIO;
import java.io.IOException;

public class JTetrimino extends Tetrimino{

    public JTetrimino(int xPos, int yPos) throws IOException {
        super(xPos, yPos);

        TetriminoNode[] tetriminoNodes = new TetriminoNode[4];

        tetriminoNodes[0] = new TetriminoNode(xPos, yPos);
        tetriminoNodes[1] = new TetriminoNode(xPos+1, yPos);
        tetriminoNodes[2] = new TetriminoNode(xPos-1, yPos);
        tetriminoNodes[3] = new TetriminoNode(xPos+1, yPos+1);

        this.setTetriminoNodes(tetriminoNodes);

        this.setSprite(ImageIO.read(getClass().getResourceAsStream("/Tetriminos/J_Tetrimino_Sprite.png")));
    }

    @Override
    public void checkRotation() {
        TetriminoNode[] tetriminoNodes = new TetriminoNode[4];

        int xPos = getOrigin().x;
        int yPos = getOrigin().y;

        switch (getRotation()) {
            case 0 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos-1, yPos-1);
                tetriminoNodes[1] = new TetriminoNode(xPos, yPos-1);
                tetriminoNodes[2] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[3] = new TetriminoNode(xPos-2, yPos-1);
            }
            case 1 -> {
                tetriminoNodes[0] = new TetriminoNode(xPos-1, yPos-1);
                tetriminoNodes[1] = new TetriminoNode(xPos, yPos-1);
                tetriminoNodes[2] = new TetriminoNode(xPos, yPos);
                tetriminoNodes[3] = new TetriminoNode(xPos-2, yPos-1);
            }
        }

        this.setTetriminoNodes(tetriminoNodes);
    }
}
