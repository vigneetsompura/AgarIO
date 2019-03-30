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
		// TODO Auto-generated method stub
		
	}

}
