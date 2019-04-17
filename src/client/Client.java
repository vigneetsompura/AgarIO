package client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author Vigneet Sompura
 *
 */
public class Client extends Canvas implements Runnable{

	private static final long serialVersionUID = 4780645461398335058L;
	public static final int WIDTH = 4800, HEIGHT = WIDTH/16*9;
	public static final int FWIDTH = 1600, FHEIGHT = FWIDTH/16*9;
	public double scale = 1;
	
	private Thread thread;
	private boolean running = false;
	private Handler handler; 

	// Server communication variables.
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	private byte[] outData;
    private byte[] inData;
	
	Player p;
	
	public Client(String serverIp) throws IOException, ClassNotFoundException {
		
    	clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName(serverIp);
        
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE);
        String sentence = "startGame:" + id;
        outData = sentence.getBytes();
        
        DatagramPacket out = new DatagramPacket(outData, outData.length, IPAddress, 4445);
        clientSocket.send(out);
        
        DatagramPacket in = new DatagramPacket(inData, inData.length);
        clientSocket.receive(in);
        
        inData = in.getData();
        
        ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(inData));
        handler = (Handler) objStream.readObject();

		p = new Player(id, handler.getX(id), handler.getY(id));

		this.addMouseMotionListener(new MouseInput(p));
		this.addMouseListener(new MouseInput(p));
		this.addKeyListener(new KeyInput(p));
		
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
		new Thread(new Sender(game.handler, game.p)).start();
	}

	synchronized public void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
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
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
				System.out.println("FPS: "+frames);
				System.out.println("Score :" + (int) ((p.radius-32)*2));
				frames = 0;
			}
		}
		stop();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.translate(FWIDTH/2, FHEIGHT/2);
		if(p.getRadius() > 64)
			scale = 64/p.getRadius();
		g.scale(scale, scale);
		g.translate(-p.getX(),-p.getY());
		g.setColor(Color.gray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.RED);
		g.drawRect(0, 0, WIDTH, HEIGHT);
		handler.render(g);
		g.dispose();
		bs.show();
	}

	private void tick() {
		p.tick();
	}

	
}
