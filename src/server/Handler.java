package server;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import agario.Food;
import agario.Game;
import agario.Player;

public class Handler{

	private Game game;
	
	public Handler() {
		this.game = new Game();
	}

	public HashMap<Integer, Player> getPlayers() {
		return game.getPlayers();
	}

	public ArrayList<Food> getFoodList() {
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

	public void addFood(Food food) {
		game.addFood(food);
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public void tick() {
		for(Player predator: getPlayers().values()) {
			for(Food food: getFoodList()) {
				tryEat(predator, food);
			}
			for(Player prey: getPlayers().values()) {
				if(predator.getPlayerID() != prey.getPlayerID()) {
					tryEat(predator, prey);
				}
			}
		}
	}
	
	public void tryEat(Player player, Food food) {
		if(Point2D.distance(player.getX(), player.getY(), food.getX(), food.getY())< player.getRadius() - Food.RADIUS) {
			player.setRadius(Math.hypot(player.getRadius(), Food.RADIUS));
			Random random = new Random();
			food.setXY(random.nextInt(Server.WIDTH), random.nextInt(Server.HEIGHT));
		}
	}
	
	public void tryEat(Player predator, Player prey) {
		if(Point2D.distance(predator.getX(), predator.getY(), prey.getX(), prey.getY()) < predator.getRadius()-prey.getRadius()) {
			predator.setRadius(Math.hypot(predator.getRadius(), prey.getRadius()));
			game.removePlayer(prey.getPlayerID());
		}
	}
}

