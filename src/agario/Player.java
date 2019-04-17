package agario;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {

    private static final long serialVersionUID = -1295501542406655467L;
    private int playerID, x, y;
    private double radius;
    private Color color;

    public Player(int playerID, int x, int y) {
        Random random = new Random();
        this.playerID = playerID;
        this.x = x;
        this.y = y;
        this.color = Color.getHSBColor(random.nextFloat(), (random.nextInt(2000) + 1000) / 10000f, 0.95f);
        this.radius = 32;
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

    public void tryEat(Food food) {
        if (Point2D.distance(getX(), getY(), food.getX(), food.getY()) < getRadius() - Food.RADIUS) {
            setRadius(Math.hypot(getRadius(), Food.RADIUS));
            Random random = new Random();
            food.setXY(random.nextInt(Game.WIDTH - (int) Food.RADIUS * 2) + (int) Food.RADIUS, random.nextInt(Game.HEIGHT - (int) Food.RADIUS * 2) + (int) Food.RADIUS);
        }
    }

    public boolean ifAtePrey(Player prey) {
        if (Point2D.distance(getX(), getY(), prey.getX(), prey.getY()) < (getRadius() - prey.getRadius())) {
            setRadius(Math.hypot(getRadius(), prey.getRadius()));
            return true;
        }
        return false;
    }

    public boolean isNotPredator(Player predator) {
        return predator.getPlayerID() != getPlayerID();
    }
}
