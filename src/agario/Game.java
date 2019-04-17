package agario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Serializable{

	private static final long serialVersionUID = -5776416645573148979L;
	private HashMap<Integer, Player> players;
	private ArrayList<Food> foodList;
	
	public Game() {
		this.players = new HashMap<Integer, Player>();
		this.foodList = new ArrayList<Food>();
	}

	public HashMap<Integer, Player> getPlayers() {
		return players;
	}

	public void setPlayers(HashMap<Integer, Player> players) {
		this.players = players;
	}

	public ArrayList<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(ArrayList<Food> foodList) {
		this.foodList = foodList;
	}
	
	public Player getPlayer(int playerID) {
		return players.get(playerID);
	}
	
	public void addPlayer(Player player) {
		players.put(player.getPlayerID(), player);
	}
	
	public void removePlayer(int playerID) {
		players.remove(playerID);
	}
	
	public void addFood(Food food) {
		foodList.add(food);
	}

}
