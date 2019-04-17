package server;

import java.net.SocketException;
import java.util.Random;

import agario.Food;
import agario.Game;

public class Server implements Runnable{
	
	private Handler handler;
	private Thread thread;
	private boolean running;
	private Receiver receiver;

	public Server() throws SocketException {
		handler = new Handler();
		for(int i=0; i<100; i++) {
			handler.addFood();
		}
		receiver = new Receiver(handler);
	}

	public static void main(String[] args) throws SocketException {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.start();
	}
	
	synchronized public void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
		receiver.start();
	}
	
	synchronized public void stop() {
		try {
			running = false;
			receiver.stop();
			thread.join();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void tick() {
		handler.tick();
	}
	
	@Override
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
			}
		}
		stop();
	}
}