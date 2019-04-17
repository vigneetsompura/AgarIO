package client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import agario.Food;
import agario.Game;
import agario.Player;

public class Renderer implements Runnable {

	private Client client;
	private boolean running;
	private Thread thread;
	private double scale = 1;
	private Graphics2D g;
	private BufferStrategy bs;
	
	public Renderer(Client client, Graphics2D g, BufferStrategy bs) {
		this.client = client;
		this.g = g;
		this.bs  = bs;
	}

	synchronized public void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	
	synchronized public void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(running) {
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			PlayerHandler playerHandler = client.getPlayerHandler();
			g.translate(Client.FWIDTH/2, Client.FHEIGHT/2);
			if(playerHandler.getRadius() > 64)
				this.scale = 64/playerHandler.getRadius();
			g.scale(scale, scale);
			g.translate(-playerHandler.getX(),-playerHandler.getY());
			g.setColor(Color.gray);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g.setColor(Color.RED);
			g.drawRect(0, 0, Game.WIDTH, Game.HEIGHT);
			
			Game game = client.getGame();
			List<Food> foodList = game.getFoodList();
			for(Food food: foodList) {
				g.setColor(Color.YELLOW);
				g.fillOval(food.getX(), food.getY(), (int) Food.RADIUS * 2, (int) Food.RADIUS * 2);
			}
			
			ArrayList<Player> players = new ArrayList<Player> (game.getPlayers().values());
			players.sort(Comparator.comparingDouble(Player::getRadius));

			for(Player player: players) {
				if(player.getPlayerID() == client.getPlayerHandler().getPlayerID()) {
					Player p = client.getPlayerHandler().getPlayer();
					g.setColor(p.getColor());
					g.fillOval(p.getX(), p.getY(), (int) p.getRadius()*2, (int) p.getRadius()*2);
				}else {
					g.setColor(player.getColor());
					g.fillOval(player.getX(), player.getY(), (int) player.getRadius()*2, (int) player.getRadius()*2);
				}
			}
			
			
			
			g.dispose();
			bs.show();
		}

	}

}
