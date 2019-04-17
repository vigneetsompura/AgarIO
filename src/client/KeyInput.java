package client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private PlayerHandler playerHandler;
    private boolean flag = true;

    KeyInput(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void keyPressed(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_SPACE && flag) {
            playerHandler.setBoost(10);
            flag = false;
        }
    }

    public void keyReleased(KeyEvent k) {
        playerHandler.setBoost(1);
        flag = true;
    }
}
