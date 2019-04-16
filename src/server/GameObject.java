package server;

import java.util.Random;

public abstract class GameObject {

	protected int x,y;
	protected double radius;
	protected int velX, velY;
	
	public GameObject(int x, int y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public GameObject(double radius) {
		Random random = new Random();
		this.x = random.nextInt(Server.WIDTH);
		this.y = random.nextInt(Server.HEIGHT);
		this.radius = radius;
	}
	
	public abstract void tick();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}
	

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double d) {
		this.radius = d;
	}
	
	public double distance(GameObject o) {
		return Math.sqrt((x-o.x)*(x-o.x) + (y-o.y)*(y-o.y));
	}
	
}

