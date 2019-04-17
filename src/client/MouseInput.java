package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{

	PlayerHandler playerHandler;
	boolean flag = true;
	
	public MouseInput(PlayerHandler playerHandler) {
		this.playerHandler = playerHandler;
	}
	
	public void mouseMoved(MouseEvent e) {
		System.out.println(playerHandler.getX()+","+playerHandler.getY());
		playerHandler.setMouseX(e.getX()-Client.FWIDTH/2);
		playerHandler.setMouseY(e.getY()-Client.FHEIGHT/2);
	}
	
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse Exit");
		playerHandler.setMouseX(0);
		playerHandler.setMouseY(0);
	}
	
//	public void mousePressed(MouseEvent e) {
//		if(e.getButton() == MouseEvent.BUTTON1 && flag) {
//			p.setBoost(10);
//			flag = false;
//		}
//	}
//	
//	public void mouseReleased(MouseEvent e) {
//		p.setBoost(1);
//		flag=true;
//	}
}
