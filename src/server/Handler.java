package server;

import agario.Food;
import agario.Game;
import agario.Player;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Handler {

    private Game game;

    public Handler() {
        this.game = new Game();
    }

    public List<Food> getFoodList() {
        return game.getFoodList();
    }

    public Player getPlayer(int playerID) {
        return game.getPlayer(playerID);
    }

    public void addPlayer(Player player) {
        game.addPlayer(player);
    }

    public void removePlayer(int playerID) {
        game.removePlayer(playerID);
    }

    public void addFood() {
        game.addFood();
    }

    public Game getGame() {
        return this.game;
    }

    public void tick() {
        game.lock();
        List<Player> players = new ArrayList<>(game.getPlayers().values());
        for (Player predator : players) {
            if (game.getPlayer(predator.getPlayerID()) != null) {
                for (Food food : getFoodList()) {
                    tryEat(predator, food);
                }
                List<Player> preys = new ArrayList<>(game.getPlayers().values());
                for (Player prey : preys) {
                    if (predator.getPlayerID() != prey.getPlayerID()) {
                        tryEat(predator, prey);
                    }
                }
            }
        }
        game.unlock();
    }

    public void tryEat(Player player, Food food) {
        if (Point2D.distance(player.getX(), player.getY(), food.getX(), food.getY()) < player.getRadius() - Food.RADIUS) {
            player.setRadius(Math.hypot(player.getRadius(), Food.RADIUS));
            Random random = new Random();
            food.setXY(random.nextInt(Game.WIDTH - (int) Food.RADIUS * 2) + (int) Food.RADIUS, random.nextInt(Game.HEIGHT - (int) Food.RADIUS * 2) + (int) Food.RADIUS);
        }
    }

    public void tryEat(Player predator, Player prey) {
        if (Point2D.distance(predator.getX(), predator.getY(), prey.getX(), prey.getY()) < (predator.getRadius() - prey.getRadius())) {
            predator.setRadius(Math.hypot(predator.getRadius(), prey.getRadius()));
            game.removePlayer(prey.getPlayerID());
        }
    }
}

