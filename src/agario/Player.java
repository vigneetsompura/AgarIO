package agario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Player{

	private int playerID, x, y;
	private double radius;
	private Color color;

	public Player(int playerID, int x, int y, Color color) {
		this.playerID = playerID;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public Player(int playerID, int x, int y) {
		Random random = new Random();
		this.playerID = playerID;
		this.x = x;
		this.y = y;
		this.color = Color.getHSBColor(random.nextFloat(), (random.nextInt(2000) + 1000) / 10000f, 0.95f);
	}

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
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public int getPlayerID() {
		return playerID;
	}

	public Color getColor() {
		return color;
	}

	
	
}
