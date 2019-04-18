package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Class that reads the current mouse cursor position inside the game canvas
* and returns these co-ordinates. The player object is then moved a step towards
* these co-ordinates.
* 
* @author  Vigneet Sompura
* @version 1.0
* @since   2019-04-17 
*/

public class MouseInput extends MouseAdapter {

    private PlayerHandler playerHandler;

    MouseInput(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void mouseMoved(MouseEvent e) {
        playerHandler.setMouseX(e.getX() - Client.FWIDTH / 2);
        playerHandler.setMouseY(e.getY() - Client.FHEIGHT / 2);
    }

    public void mouseExited(MouseEvent e) {
        playerHandler.setMouseX(0);
        playerHandler.setMouseY(0);
    }
}
