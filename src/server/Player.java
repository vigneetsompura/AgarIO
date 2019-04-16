package server;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

public class Player extends GameObject implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7903683352089744063L;
	private int playerID;
	private int mouseX, mouseY, speed, boost;
	private Color color;


	public Player(int playerID) {
		super(32);
		Random random = new Random();
		this.playerID = playerID;
		color = Color.getHSBColor(random.nextFloat(), (random.nextInt(2000) + 1000) / 10000f, 0.95f);
		mouseX = 0;
		mouseY = 0;
		this.speed = 5;
		this.boost = 1;
	}
	
	public void tick() {
		updateVelocity();
		if(boost!=1)
			boost--;
	}

	public void updateVelocity() {
		if(mouseX!=0 || mouseY!=0) {
			int velX = (int) ((speed*boost*mouseX)/Math.sqrt((mouseX*mouseX + mouseY*mouseY)));
			int velY = (int) ((speed*boost*mouseY)/Math.sqrt((mouseX*mouseX + mouseY*mouseY)));
			this.setVelX(velX);
			this.setVelY(velY);
		}else {
			this.setVelX(0);
			this.setVelY(0);
		}
	}


	public void tryeat(GameObject object, Handler handler) {
		if(distance(object) < (radius - object.radius)) {
			this.setRadius(Math.sqrt(radius*radius + object.radius*object.radius));
			if(object instanceof Food) {
				Food food = (Food) object;
				food.respawn();
			}else if(object instanceof Player) {
				Player player = (Player) object;
				handler.removePlayer(player.getPlayerID());
			}
		}
	}
	
	public int getPlayerID() {
		return this.playerID;
	}
}

