package client;

import agario.Game;
import agario.Player;

public class PlayerHandler {

    private Player player;
    private int velX, velY, mouseX, mouseY, speed, boost;

    public PlayerHandler(Player player) {
        this.player = player;
        mouseX = 0;
        mouseY = 0;
        this.speed = 5;
        this.boost = 1;
    }

    public void tick() {
        updateVelocity();
        int x = moveWithConstraints(getX() + velX, (int) getRadius(), Game.WIDTH - (int) getRadius());
        int y = moveWithConstraints(getY() + velY, (int) getRadius(), Game.HEIGHT - (int) getRadius());
        setXY(x, y);
        if (boost != 1)
            boost--;
    }

    private void updateVelocity() {
        if (mouseX != 0 || mouseY != 0) {
            int velX = (int) ((speed * boost * mouseX) / Math.hypot(mouseX, mouseY));
            int velY = (int) ((speed * boost * mouseY) / Math.hypot(mouseX, mouseY));
            this.setVelX(velX);
            this.setVelY(velY);
        } else {
            this.setVelX(0);
            this.setVelY(0);
        }
    }

    private int moveWithConstraints(int x, int min, int max) {
        if (x > max)
            return max;
        if (x < min)
            return min;
        return x;
    }

    private void setVelX(int velX) {
        this.velX = velX;
    }

    private void setVelY(int velY) {
        this.velY = velY;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public void setBoost(int boost) {
        this.boost = boost;
    }

    public int getX() {
        return player.getX();
    }

    public int getY() {
        return player.getY();
    }

    private void setXY(int x, int y) {
        player.setXY(x, y);
    }

    public double getRadius() {
        return player.getRadius();
    }

    public int getPlayerID() {
        return player.getPlayerID();
    }

    public void setRadius(double radius) {
        player.setRadius(radius);
    }
}
