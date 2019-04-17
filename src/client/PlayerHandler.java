package client;

import agario.Game;
import agario.Player;

class PlayerHandler {

    private Player player;
    private int velX;
    private int velY;
    private int mouseX;
    private int mouseY;
    private int speed;
    private int boost;

    PlayerHandler(Player player) {
        this.player = player;
        mouseX = 0;
        mouseY = 0;
        this.speed = 5;
        this.boost = 1;
    }

    void tick() {
        updateVelocity();
        int x = moveWithConstraints(getX() + velX, (int) getRadius(), Game.WIDTH - (int) getRadius());
        int y = moveWithConstraints(getY() + velY, (int) getRadius(), Game.HEIGHT - (int) getRadius());
        player.setXY(x, y);
        if (boost != 1)
            boost--;
    }

    private void updateVelocity() {
        if (mouseX != 0 || mouseY != 0) {
            int velX = (int) ((speed * boost * mouseX) / Math.hypot(mouseX, mouseY));
            int velY = (int) ((speed * boost * mouseY) / Math.hypot(mouseX, mouseY));
            this.velX = velX;
            this.velY = velY;
        } else {
            this.velX = 0;
            this.velY = 0;
        }
    }

    private int moveWithConstraints(int x, int min, int max) {
        if (x > max)
            return max;
        if (x < min)
            return min;
        return x;
    }

    void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    void setBoost(int boost) {
        this.boost = boost;
    }

    int getX() {
        return player.getX();
    }

    int getY() {
        return player.getY();
    }

    double getRadius() {
        return player.getRadius();
    }

    int getPlayerID() {
        return player.getPlayerID();
    }

    void setRadius(double radius) {
        player.setRadius(radius);
    }

    String getLocationUpdateMessage() {
        return "locationUpdate:" + getPlayerID() + "," + getX() + "," + getY();
    }
}
