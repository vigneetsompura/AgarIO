package agario;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{

	Player g;
	
	public MouseInput(Player g) {
		this.g = g;
	}
	
	public void mouseMoved(MouseEvent e) {
		g.setMouseX(e.getX()+AgarIO.OX);
		g.setMouseY(e.getY()+AgarIO.OY);
		System.out.println(e.getX()+","+e.getY());
	}
	
	public void mouseExited(MouseEvent e) {
		g.setMouseX(g.getX());
		g.setMouseY(g.getY());
	}
	
}
