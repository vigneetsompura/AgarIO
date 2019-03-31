package agario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class Player extends GameObject {

	
	private int mouseX, mouseY, speed;
	
	
	public Player(int x, int y,ID id) {
		super(x, y, 32, id);
		mouseX = x;
		mouseY = y;
		this.speed = 5;
	} 

	public void tick() {
		updateVelocity();
		x += velX;
		y += velY;
		AgarIO.setOX(AgarIO.OX + velX);
		AgarIO.setOY(AgarIO.OY + velY);
	}
	
	public void updateVelocity() {
		int velX = (int) (mouseX - getX());
		int velY = (int) (mouseY - getY());
		
		int x = (int) ((speed*velX)/Math.sqrt((velX*velX + velY*velY)));
		int y = (int) ((speed*velY)/Math.sqrt((velX*velX + velY*velY)));
		this.setVelX(x);
		this.setVelY(y);
	}

	
	public void tryeat(Food f) {
		if(distance(f) < (radius - f.radius)) {
			this.setRadius(Math.sqrt(radius*radius + f.radius*f.radius));
			f.respawn();
		}
	}
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.CYAN);
		g.fillOval((int) (x-radius-AgarIO.OX),(int) (y-radius-AgarIO.OY), (int) radius*2,(int) radius*2);
		 
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
	
	

}
