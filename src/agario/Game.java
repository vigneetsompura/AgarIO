package agario;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
* The HelloWorld program implements an application that
* simply displays "Hello World!" to the standard output.
*
* @author  Vigneet Sompura
* @version 1.0
* @updated   04-17-2019 
*/

public class Game implements Serializable {

    private ReentrantLock lock = new ReentrantLock();
    private static final long serialVersionUID = -5776416645573148979L;
    public static final int WIDTH = 4800, HEIGHT = WIDTH / 16 * 9;
    private Map<Integer, Player> players;
    private List<Food> foodList;

    public Game() {
        this.players = new HashMap<>();
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
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }
}
