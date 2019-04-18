package agario;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Random;

/**
* Data wrapper object for Player functions.
* Manages the x and y co-ordinates players based on how the player moves.
* A list of Player objects is retained by the server based on which players
* are currently in the game.
* Sets and gets x any y co-ordinates for a player, also handles the boost
* functionality.
* 
* @author  Vigneet Sompura
* @version 1.0
* @since   2019-04-17 
*/

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

    public void setXYFromVelocity(int velX, int velY) {
        int x = moveWithConstraints(this.x + velX, (int) radius, Game.WIDTH - (int) radius);
        int y = moveWithConstraints(this.y + velY, (int) radius, Game.HEIGHT - (int) radius);
        this.x = x;
        this.y = y;
    }

    private int moveWithConstraints(int x, int min, int max) {
        if (x > max)
            return max;
        if (x < min)
            return min;
        return x;
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

    public void tryEat(Food food) {
        if (Point2D.distance(getX(), getY(), food.getX(), food.getY()) < getRadius() - Food.RADIUS) {
            setRadius(Math.hypot(getRadius(), Food.RADIUS));
            food.respawn();
        }
    }

    public boolean didEatPrey(Player prey) {
        if (Point2D.distance(getX(), getY(), prey.getX(), prey.getY()) < (getRadius() - prey.getRadius())) {
            setRadius(Math.hypot(getRadius(), prey.getRadius()));
            return true;
        }
        return false;
    }

    public boolean isNotPredator(Player predator) {
        return predator.getPlayerID() != getPlayerID();
    }

    public void fillColor(Graphics2D g) {
        g.setColor(color);
        int radius = (int) getRadius();
        g.fillOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
    }
}
