package server;

import agario.Food;
import agario.Game;
import agario.Player;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
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
        players.stream()
                .filter(predator -> game.getPlayer(predator.getPlayerID()) != null)
                .forEach(this::tryEat);
        game.unlock();
    }

    private void tryEat(Player predator) {
        tryEatFood(predator);
        tryEatPrey(predator);
    }

    private void tryEatPrey(Player predator) {
        List<Player> preys = new ArrayList<>(game.getPlayers().values());
        preys.stream()
                .filter(prey -> predator.getPlayerID() != prey.getPlayerID())
                .forEach(prey -> tryEat(predator, prey));
    }

    private void tryEatFood(Player predator) {
        getFoodList().forEach(food -> tryEat(predator, food));
    }

    private void tryEat(Player player, Food food) {
        if (Point2D.distance(player.getX(), player.getY(), food.getX(), food.getY()) < player.getRadius() - Food.RADIUS) {
            player.setRadius(Math.hypot(player.getRadius(), Food.RADIUS));
            Random random = new Random();
            food.setXY(random.nextInt(Game.WIDTH - (int) Food.RADIUS * 2) + (int) Food.RADIUS, random.nextInt(Game.HEIGHT - (int) Food.RADIUS * 2) + (int) Food.RADIUS);
        }
    }

    private void tryEat(Player predator, Player prey) {
        if (Point2D.distance(predator.getX(), predator.getY(), prey.getX(), prey.getY()) < (predator.getRadius() - prey.getRadius())) {
            predator.setRadius(Math.hypot(predator.getRadius(), prey.getRadius()));
            game.removePlayer(prey.getPlayerID());
        }
    }
}

