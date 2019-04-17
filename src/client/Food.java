package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Food extends GameObject {
	static Random r = new Random();
	
	public Food() {
		super(r.nextInt(AgarIO.WIDTH-32)+16, r.nextInt(AgarIO.HEIGHT-32)+16, 16);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillOval((int) (x-radius),(int) (y-radius), (int) radius*2,(int) radius*2);
	}

	public void respawn() {
		this.setX(r.nextInt(AgarIO.WIDTH-32)+16);
		this.setY(r.nextInt(AgarIO.HEIGHT-32)+16);
	}

}
