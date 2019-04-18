package agario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Game implements Serializable {

    private ReentrantLock lock = new ReentrantLock();
    private static final long serialVersionUID = -5776416645573148979L;
    public static final int WIDTH = 16000, HEIGHT = WIDTH / 16 * 9;
    private Map<Integer, Player> players;
    private List<Food> foodList;

    public Game() {
        this.players = new ConcurrentHashMap<>();
        this.foodList = new ArrayList<>();
    }

    public List<Food> getFoodList() {
        return foodList;
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
        foodList.add(new Food(calc(WIDTH), calc(HEIGHT)));
    }

    private int calc(int param) {
        Random random = new Random();
        return random.nextInt(param - (int) Food.RADIUS * 2) + (int) Food.RADIUS;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }
}
