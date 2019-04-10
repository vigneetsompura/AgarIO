package agario;

import java.awt.Graphics2D;

public abstract class GameObject {

	protected int x,y;
	protected double radius;
	protected Type type;
	protected int velX, velY;
	
	public GameObject(int x, int y, double radius, Type id) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.type = id;
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

	public Type getId() {
		return type;
	}

	public void setId(Type id) {
		this.type = id;
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
