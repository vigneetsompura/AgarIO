package agario;

import java.awt.Graphics2D;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> objects = new LinkedList<>();
	
	public void tick() {
		for(GameObject object: objects) {
			object.tick();
			if(object instanceof Player) {
				Player self = (Player) object;
				for(GameObject other: objects) {
					if(other != object) {
						self.tryeat((Food) other);
					}
				}
			}
		}
	}
	
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
}
