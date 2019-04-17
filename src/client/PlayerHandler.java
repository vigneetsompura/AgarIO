package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Comparator;
import java.util.List;

import agario.Food;
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

	void render(Client client) {
	    BufferStrategy bs = client.getBufferStrategy();
	    if (bs == null) {
	        client.createBufferStrategy(2);
	        return;
	    }

        Graphics2D g = drawCanvas(client, bs);
        drawFood(client, g);
        drawPlayers(client, g);

        g.dispose();
	    bs.show();
	}

    private void drawPlayers(Client client, Graphics2D g) {
        List<Player> players = client.game.getPlayers();
        players.sort(Comparator.comparingDouble(Player::getRadius));

        for (Player player : players) {
            if (player.getPlayerID() == getPlayerID()) {
                this.player.fillColor(g);
            } else {
                player.fillColor(g);
            }
        }
    }

    private void drawFood(Client client, Graphics2D g) {
        List<Food> foodList = client.game.getFoodList();
        for (Food food : foodList) {
            food.fillColor(g);
        }
    }

    private Graphics2D drawCanvas(Client client, BufferStrategy bs) {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        g.translate(Client.FWIDTH / 2, Client.FHEIGHT / 2);
        if (getRadius() > 64)
            client.scale = 64 / getRadius();
        g.scale(client.scale, client.scale);
        g.translate(-getX(), -getY());
        g.setColor(Color.gray);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(0, 0, Client.WIDTH, Client.HEIGHT);
        return g;
    }
}
