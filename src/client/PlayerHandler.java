package client;

import agario.Food;
import agario.Game;
import agario.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Comparator;
import java.util.List;

/**
* Handler class that manages all types of player movements. It uses the helper
* classes KeyInput and MouseInput to determine the next position of the player's
* object.
* It uses the tick method to keep reading new data  in a loop.
* 
* @author  Ankit Soni
* @version 1.0
* @since   2019-04-17 
*/

class PlayerHandler {

    private Player player;
    private int velX;
    private int velY;
    private int mouseX;
    private int mouseY;
    private double basespeed;
    private int boost;

    PlayerHandler(Player player) {
        this.player = player;
        mouseX = 0;
        mouseY = 0;
        this.basespeed = 7;
        this.boost = 1;
    }

    void tick() {
        updateVelocity();
        player.setXYFromVelocity(velX, velY);
        if (boost != 1)
            boost--;
    }

    private void updateVelocity() {
        if (mouseX != 0 || mouseY != 0) {
            int velX = (int) ((getSpeed() * boost * mouseX) / Math.hypot(mouseX, mouseY));
            int velY = (int) ((getSpeed() * boost * mouseY) / Math.hypot(mouseX, mouseY));
            this.velX = velX;
            this.velY = velY;
        } else {
            this.velX = 0;
            this.velY = 0;
        }
    }

    double getSpeed() {
    	return Math.max(32*basespeed/(32+(getRadius()-32)/4), 2);
  
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

    private int getX() {
        return player.getX();
    }

    private int getY() {
        return player.getY();
    }

    private double getRadius() {
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
