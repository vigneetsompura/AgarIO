package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Receiver implements Runnable {

	Handler handler;
	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];
	
	public Receiver(Handler handler) throws SocketException {
		socket = new DatagramSocket(4445);
		this.handler = handler;
	}

	@Override
	public void run() {
			running = true;
			System.out.println("Server thread started.");
			while (running) {
				DatagramPacket packet= new DatagramPacket(buf, buf.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				String received	= new String(packet.getData(), 0, packet.getLength());
				String command = received.split(":")[0];
				String parameters = received.split(":")[1].trim();
				if (command.equals("startGame")) {
					int id = Integer.parseInt(parameters);
					Player newPlayer = new Player(id);
					handler.addObject(newPlayer);
					//send handler to player
					Sender sender = new Sender(socket, address, port, handler);
					Thread thread = new Thread(sender);
					thread.start();
					
				}else if (command.equals("locationUpdate")){
					String[] p = parameters.split(",");
					Player player = handler.getPlayer(Integer.parseInt(p[0].trim()));
					player.setXY(Integer.parseInt(p[1]), Integer.parseInt(p[2].trim()));
					//send handler to player
					Sender sender = new Sender(socket, address, port, handler);
					Thread thread = new Thread(sender);
					thread.start();
					
				}else if(command.equals("endGame")) {
					int id = Integer.parseInt(parameters);
					handler.removePlayer(id);
					Sender sender = new Sender(socket, address, port, handler);
					Thread thread = new Thread(sender);
					thread.start();
				}
				
			}
			socket.close();
	}

}
