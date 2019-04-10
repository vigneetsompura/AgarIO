package agario;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends GameObject {

	
	private int mouseX, mouseY, speed, boost;
	
	
	public Player(int x, int y,Type type) {
		super(x, y, 32, type);
		mouseX = 0;
		mouseY = 0;
		this.speed = 5;
		this.boost = 1;
	} 

	public void tick() {
		updateVelocity();
		x  = moveWithConstraints(x+velX,(int)radius,AgarIO.WIDTH-(int)radius);
		y = moveWithConstraints(y+velY,(int)radius,AgarIO.HEIGHT-(int)radius);
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
	
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.CYAN);
		g.fillOval((int) (x-radius),(int) (y-radius), (int) radius*2,(int) radius*2);
		
		 
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public int getBoost() {
		return boost;
	}

	public void setBoost(int boost) {
		this.boost = boost;
	}
	
	public int moveWithConstraints(int x, int min, int max) {
		if(x>max)
			return max;
		if(x<min)
			return min;
		return x;
	}

}
