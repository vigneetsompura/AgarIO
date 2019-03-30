/**
 * 
 */
package agario;

import java.awt.Canvas;

/**
 * @author Vigneet Sompura
 *
 */
public class AgarIO extends Canvas implements Runnable{

	public static final int WIDTH = 1600, HEIGHT = WIDTH/16*9;
	private Thread thread;
	private boolean running = false;
	
	public AgarIO() {
		new Window(WIDTH, HEIGHT, "AgarIO", this);
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
				System.out.println("FPS: "+frames);
				frames = 0;
			}
		}
		stop();
	}

	private void render() {
		// TODO Auto-generated method stub
		
	}

	private void tick() {
		// TODO Auto-generated method stub
		
	}

}
