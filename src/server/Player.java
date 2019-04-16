package server;

public class Player extends GameObject {


	private int mouseX, mouseY, speed, boost;


	public Player(int x, int y) {
		super(x, y, 32);
		mouseX = 0;
		mouseY = 0;
		this.speed = 5;
		this.boost = 1;
	}

	public void tick() {
		updateVelocity();
		if(boost!=1)
			boost--;

	}

	public void updateVelocity() {
		if(mouseX!=0 || mouseY!=0) {
			int velX = (int) ((speed*boost*mouseX)/Math.sqrt((mouseX*mouseX + mouseY*mouseY)));
			int velY = (int) ((speed*boost*mouseY)/Math.sqrt((mouseX*mouseX + mouseY*mouseY)));
			this.setVelX(velX);
			this.setVelY(velY);
		}else {
			this.setVelX(0);
			this.setVelY(0);
		}
	}


	public void tryeat(Food f) {
		if(distance(f) < (radius - f.radius)) {
			this.setRadius(Math.sqrt(radius*radius + f.radius*f.radius));
			f.respawn();
		}
	}
}

