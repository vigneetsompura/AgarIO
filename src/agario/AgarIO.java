/**
 * 
 */
package agario;

import java.awt.Canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * @author Vigneet Sompura
 *
 */
public class AgarIO extends Canvas implements Runnable{

	public static final int WIDTH = 3600, HEIGHT = WIDTH/12*9;
	public static int OX = WIDTH/3, OY = HEIGHT/3;
	private Thread thread;
	private boolean running = false;
	private Handler handler; 
	
	public AgarIO() {
		handler = new Handler();
		Player p = new Player(WIDTH/2,HEIGHT/2, ID.Player);
		
		for(int i=0; i<100; i++) {
			handler.addObject(new Food(ID.Food));
		}
		handler.addObject(p);
		this.addMouseMotionListener(new MouseInput(p));
		this.addMouseListener(new MouseInput(p));
		new Window(WIDTH/3, HEIGHT/3, "AgarIO", this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AgarIO();
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
				//System.out.println("FPS: "+frames);
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
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.gray);
		g.fillRect(-OX, -OY, WIDTH, HEIGHT);
		handler.render(g);
		g.dispose();
		bs.show();
	}

	private void tick() {
		// TODO Auto-generated method stub
		handler.tick();
	}

	public static int getOX() {
		return OX;
	}

	public static void setOX(int oX) {
		OX = oX;
	}

	public static int getOY() {
		return OY;
	}

	public static void setOY(int oY) {
		OY = oY;
	}
}
