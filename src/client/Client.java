package client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import javax.swing.JFrame;

import agario.Food;
import agario.Game;
import agario.Player;

/**
 * @author Vigneet Sompura
 *
 */
public class Client extends Canvas implements Runnable{

	private static final long serialVersionUID = 7846764236102367675L;
	public static final int FWIDTH = 1600, FHEIGHT = FWIDTH/16*9;
	
	public double scale = 1;
	private Random random;
	private Thread thread;
	private boolean running = false;
	private Game game; 
	private Renderer renderer;
	// Server communication variables.
	private DatagramSocket clientSocket;
	private InetAddress serverIP;
	private byte[] outData = new byte[65500];
    private byte[] inData = new byte[65500];
	private PlayerHandler playerHandler;
	private BufferStrategy bs;
	private Graphics2D g = null;
	private boolean flag = false;
	private JFrame frame;
	
	public Client(String serverIP) throws IOException, ClassNotFoundException {
        this.serverIP = InetAddress.getByName(serverIP);
        clientSocket = new DatagramSocket();
        random = new Random();
        int playerID = random.nextInt(Integer.MAX_VALUE);
        String startMessage = "startGame:" + playerID;
        outData = startMessage.getBytes();
        
        
        DatagramPacket out = new DatagramPacket(outData, outData.length, this.serverIP, 4445);
        clientSocket.send(out);
        
        DatagramPacket in = new DatagramPacket(inData, inData.length);
        clientSocket.receive(in);
        
        inData = in.getData();
        
        ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(inData));
        game = (Game) objStream.readObject();

		playerHandler = new PlayerHandler(game.getPlayer(playerID));

		this.addMouseMotionListener(new MouseInput(playerHandler));
		this.addMouseListener(new MouseInput(playerHandler));
		this.addKeyListener(new KeyInput(playerHandler));
		
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		String serverIp = args[0];
		Client game = new Client(serverIp);
		new Window(FWIDTH, FHEIGHT, "AgarIO", game);
	}

	synchronized public void start(JFrame frame) {
		this.frame = frame;
		thread = new Thread(this);
		thread.start();
		running = true;
		
		
	}
	
	synchronized public void stop() {
		try {
			running = false;
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			renderer.stop();
			thread.join();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta >= 1) {
				try {
					tick();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				delta--;
			}
			if(running)
				render();
			frames++;
			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: "+frames);
				//System.out.println("Score :" + (int) ((playerHandler.getRadius()-32)*2));
				frames = 0;
			}
		}
		stop();
	}

	

	private void tick() throws IOException {
		playerHandler.tick();
		String updateMessage = "locationUpdate:" + playerHandler.getPlayerID()+ "," + playerHandler.getX() + "," + playerHandler.getY();
        outData = updateMessage.getBytes();
        
        DatagramPacket out = new DatagramPacket(outData, outData.length, this.serverIP, 4445);
        clientSocket.send(out);
		
        ResponseHandler responseHandler = new ResponseHandler(clientSocket, this);
        responseHandler.start();
        
	}
	
	public void setupRender() {
		bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		g = (Graphics2D) bs.getDrawGraphics();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.translate(FWIDTH/2, FHEIGHT/2);
		if(playerHandler.getRadius() > 64)
			scale = 64/playerHandler.getRadius();
		g.scale(scale, scale);
		g.translate(-playerHandler.getX(),-playerHandler.getY());
		g.setColor(Color.gray);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.RED);
		g.drawRect(0, 0, WIDTH, HEIGHT);
				
		List<Food> foodList = game.getFoodList();
		for(Food food: foodList) {
			g.setColor(Color.YELLOW);
			g.fillOval(food.getX()- (int) Food.RADIUS, food.getY()- (int) Food.RADIUS, (int) Food.RADIUS * 2, (int) Food.RADIUS * 2);
		}
		
		ArrayList<Player> players = new ArrayList<Player> (game.getPlayers().values());
		players.sort(Comparator.comparingDouble(Player::getRadius));
		
		for(Player player: players) {
			
				g.setColor(player.getColor());
				g.fillOval(player.getX()-(int)player.getRadius(), player.getY()-(int)player.getRadius(), (int) player.getRadius()*2, (int) player.getRadius()*2);

		}
		
		g.dispose();
		bs.show();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	
}
