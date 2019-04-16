package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import agario.Food;
import agario.Handler;

public class Server implements Runnable{
	
	public static final int WIDTH = 4800, HEIGHT = WIDTH/16*9;
	public static final int FWIDTH = 1600, FHEIGHT = FWIDTH/16*9;

	private Handler handler;
	private Thread thread;
	private boolean running;
	
	
	public Server() throws SocketException {
		handler = new Handler();
		for(int i=0; i<100; i++) {
			handler.addObject(new Food());
		}
	}

	public static void main(String[] args) throws SocketException {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.start();

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
	
	private void tick() {
		handler.tick();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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
			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}

}