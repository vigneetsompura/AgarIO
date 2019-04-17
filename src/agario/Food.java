package agario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Food {
	public int x,y;
	public static double RADIUS = 16;
	
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
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
}
