package agario;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{

	Player p;
	boolean flag = true;
	
	public MouseInput(Player g) {
		this.p = g;
	}
	
	public void mouseMoved(MouseEvent e) {
		p.setMouseX(e.getX()-AgarIO.FWIDTH/2);
		p.setMouseY(e.getY()-AgarIO.FHEIGHT/2);
		//System.out.println(e.getX()+","+e.getY());
	}
	
	public void mouseExited(MouseEvent e) {
		p.setMouseX(0);
		p.setMouseY(0);
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && flag) {
			p.setBoost(10);
			flag = false;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		p.setBoost(1);
		flag=true;
	}
}
