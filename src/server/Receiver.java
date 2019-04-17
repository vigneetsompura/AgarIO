package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import agario.Player;

public class Receiver implements Runnable {

	Handler handler;
	private DatagramSocket socket;
	private boolean running;
	private byte[] messageBytes = new byte[65500];
	Thread thread; 
	
	public Receiver(Handler handler) throws SocketException {
		socket = new DatagramSocket(4445);
		this.handler = handler;
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
			System.out.println("Server thread started.");
			while (running) {
				DatagramPacket packet= new DatagramPacket(messageBytes, messageBytes.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				String received	= new String(packet.getData(), 0, packet.getLength());
				
				String command = received.split(":")[0];
				String parameters = received.split(":")[1];
				
				if (command.equals("startGame")) {
					int id = Integer.parseInt(parameters.trim());
					Random random = new Random();
					Player newPlayer = new Player(id, random.nextInt(Server.WIDTH), random.nextInt(Server.HEIGHT));
					handler.addPlayer(newPlayer);

					//send handler to player
					Sender sender = new Sender(socket, address, port, handler);
					Thread thread = new Thread(sender);
					thread.start();
					
				}else if (command.equals("locationUpdate")){
					String[] p = parameters.split(",");
					Player player = handler.getPlayer(Integer.parseInt(p[0].trim()));
					player.setXY(Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
					//send handler to player
					Sender sender = new Sender(socket, address, port, handler);
					Thread thread = new Thread(sender);
					thread.start();
					
				}else if(command.equals("endGame")) {
					int playerID = Integer.parseInt(parameters);
					handler.removePlayer(playerID);
					Sender sender = new Sender(socket, address, port, handler);
					Thread thread = new Thread(sender);
					thread.start();
				}
				
			}
			socket.close();
	}

}
