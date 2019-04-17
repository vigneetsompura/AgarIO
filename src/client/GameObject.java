package client;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class GameObject {

	protected int x,y;
	protected double radius;
	protected int velX, velY;
	
	public GameObject(int x, int y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g);

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
		return Point2D.distance(x, y, o.getX(), o.getY());
	}
	
}
