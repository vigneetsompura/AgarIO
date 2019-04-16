package agario;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;

import server.Player;

public class Handler {
	HashMap<Integer,Player> players = new HashMap<Integer, Player>();
	LinkedList<GameObject> objects = new LinkedList<>();
		
	public void render(Graphics2D g) {
		for(GameObject object: objects) {
			object.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
	
	public void removeObject(GameObject object) {
		objects.remove(object);
	}
	
	public int getX(int id) {
		return players.get(id).getX();
	}
	
	public int getY(int id) {
		return players.get(id).getY();
	}
	
	public double getRadius(int id) {
		return players.get(id).getRadius();
	}
	
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
}
