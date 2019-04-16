package server;

import java.io.Serializable;
import java.util.Random;

public class Food extends GameObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4324808548266228820L;
	static Random r = new Random();
	
	public Food() {
		super(r.nextInt(Server.WIDTH-32)+16, r.nextInt(Server.HEIGHT-32)+16, 16);
	}

	@Override
	public void tick() {
	}

	public void respawn() {
		this.setX(r.nextInt(Server.WIDTH-32)+16);
		this.setY(r.nextInt(Server.HEIGHT-32)+16);
	}

}
