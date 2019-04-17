package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{

	Player p;
	boolean flag = true;
	
	public MouseInput(Player g) {
		this.p = g;
	}
	
	public void mouseMoved(MouseEvent e) {
		//System.out.println("Mouse Move");
		p.setMouseX(e.getX()-Client.FWIDTH/2);
		p.setMouseY(e.getY()-Client.FHEIGHT/2);
	}
	
	public void mouseExited(MouseEvent e) {
		//System.out.println("Mouse Exit");
		p.setMouseX(0);
		p.setMouseY(0);
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
