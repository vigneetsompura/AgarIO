package server;

import java.util.Random;

public class Food extends GameObject {
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
