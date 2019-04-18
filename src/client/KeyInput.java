package client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
* Class to handle the boost functionality.
* Boosts a players position whenever they press 'Space' button.
* Long pressing 'Space' button only boosts the player once.
* 
* @author  Varun Patni
* @version 1.0
* @since   2019-04-17 
*/

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
