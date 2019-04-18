package agario;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Food implements Serializable {

    private static final long serialVersionUID = 5494431746711149112L;
    private int x, y;
    static double RADIUS = 16;

    Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    private void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void fillColor(Graphics2D g) {
        g.setColor(Color.YELLOW);
        int radius = (int) Food.RADIUS;
        g.fillOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
    }

    void respawn() {
        Random random = new Random();
        int x = random.nextInt(Game.WIDTH - (int) Food.RADIUS * 2) + (int) Food.RADIUS;
        int y = random.nextInt(Game.HEIGHT - (int) Food.RADIUS * 2) + (int) Food.RADIUS;
        setXY(x, y);
    }
}
