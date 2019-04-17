package agario;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
* The HelloWorld program implements an application that
* simply displays "Hello World!" to the standard output.
*
* @author  Vigneet Sompura
* @version 1.0
* @updated   04-17-2019 
*/

public class Food implements Serializable {

    private static final long serialVersionUID = 5494431746711149112L;
    private int x, y;
    public static double RADIUS = 16;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

	public void fillColor(Graphics2D g) {
		g.setColor(Color.YELLOW);
		int radius = (int) Food.RADIUS;
		g.fillOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
	}
}
