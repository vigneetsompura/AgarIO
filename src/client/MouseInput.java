package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private PlayerHandler playerHandler;

    public MouseInput(PlayerHandler playerHandler) {
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
