package server;

import java.util.HashMap;
import java.util.LinkedList;

public class Handler {

	HashMap<Integer,Player> players = new HashMap<Integer, Player>();
	LinkedList<GameObject> objects = new LinkedList<>();
	
	public void tick() {
		for(GameObject object: objects) {
			object.tick();
			if(object instanceof Player) {
				Player self = (Player) object;
				for(GameObject other: objects) {
					if(other != object) {
						self.tryeat(other, this);
					}
				}
			}
		}
	}

	public void addObject(GameObject object) {
		if(object instanceof Food)
			objects.add(object);
		else if (object instanceof Player) {
			Player player = (Player) object;
			players.put(player.getPlayerID(), player);
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getPlayerID());
	}
}

