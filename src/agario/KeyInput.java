package agario;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	Player p;
	boolean flag = true;
	
	public KeyInput(Player p) {
		this.p = p;
	}
	
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode()==KeyEvent.VK_SPACE && flag) {
			p.setBoost(10);
			flag = false;
		}
	}
	
	public void keyReleased(KeyEvent k) {
		p.setBoost(1);
		flag=true;
	}

}
