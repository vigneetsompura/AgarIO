package agario;

import java.io.Serializable;

public class Food implements Serializable{
	
	private static final long serialVersionUID = 5494431746711149112L;
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
