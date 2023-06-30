package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed, downPressed, upPressed, leftRotatePressed, rightRotatePressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        if (keyCode == KeyEvent.VK_A
                || keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_D
                || keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_S
                || keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_W
                || keyCode == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_Q
                || keyCode == KeyEvent.VK_Z) {
            leftRotatePressed = true;
        }
        if (keyCode == KeyEvent.VK_E
                || keyCode == KeyEvent.VK_X) {
            rightRotatePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A
                || keyCode == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_D
                || keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_S
                || keyCode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_W
                || keyCode == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_Q
                || keyCode == KeyEvent.VK_Z) {
            leftRotatePressed = false;
        }
        if (keyCode == KeyEvent.VK_E
                || keyCode == KeyEvent.VK_X) {
            rightRotatePressed = false;
        }
    }
}
