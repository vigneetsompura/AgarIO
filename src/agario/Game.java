package agario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game implements Serializable{

	ReentrantLock lock = new ReentrantLock();
	private static final long serialVersionUID = -5776416645573148979L;
	public static final int WIDTH = 4800, HEIGHT = WIDTH/16*9;
	private HashMap<Integer, Player> players;
	private ArrayList<Food> foodList;
	private Random random;
	
	public Game() {
		this.players = new HashMap<Integer, Player>();
		this.foodList = new ArrayList<Food>();
		this.random = new Random();
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
	
	public void addFood() {
		foodList.add(new Food(random.nextInt(WIDTH-(int) Food.RADIUS*2)+(int) Food.RADIUS, random.nextInt(HEIGHT-(int) Food.RADIUS*2)+(int) Food.RADIUS));
	}
	
	public void lock() {
		this.lock.lock();
	}
	
	public void unlock() {
		this.lock.unlock();
	}

}
