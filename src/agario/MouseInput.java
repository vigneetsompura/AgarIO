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
		p.setMouseX(e.getX()+AgarIO.OX);
		p.setMouseY(e.getY()+AgarIO.OY);
		//System.out.println(e.getX()+","+e.getY());
	}
	
	public void mouseExited(MouseEvent e) {
		p.setMouseX(p.getX());
		p.setMouseY(p.getY());
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
