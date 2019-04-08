/**
 * 
 */
package agario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 * @author Vigneet Sompura
 *
 */
public class AgarIO extends Canvas implements Runnable{

	public static final int WIDTH = 4800, HEIGHT = WIDTH/16*9;
	public static final int FWIDTH = 1200, FHEIGHT = FWIDTH/16*9;
	public static int OX = WIDTH/3, OY = HEIGHT/3;
	private Thread thread;
	private boolean running = false;
	private Handler handler; 
	
	Player p;
	
	public AgarIO() {
		handler = new Handler();
		p = new Player(WIDTH/2,HEIGHT/2, ID.Player);
		
		for(int i=0; i<100; i++) {
			handler.addObject(new Food(ID.Food));
		}
		handler.addObject(p);
		this.addMouseMotionListener(new MouseInput(p));
		this.addMouseListener(new MouseInput(p));
		this.addKeyListener(new KeyInput(p));
		new Window(FWIDTH, FHEIGHT, "AgarIO", this);
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
		g.setColor(Color.gray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.translate(FWIDTH/2, FHEIGHT/2);
		double scale = 64/p.getRadius();
		g.scale(scale, scale);
		g.translate(-p.getX(),-p.getY());
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
