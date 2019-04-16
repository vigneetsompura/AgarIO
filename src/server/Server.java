package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable{

	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];
	
	public Server() throws SocketException {
		socket = new DatagramSocket(4445);
	}

	public static void main(String[] args) throws SocketException {
		// TODO Auto-generated method stub
		new Thread(new Server()).start();

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		System.out.println("Server thread started.");
		while (running) {
			DatagramPacket packet= new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			packet = new DatagramPacket(buf, buf.length, address, port);
			String received	= new String(packet.getData(), 0, packet.getLength());

			if (received.equals("end")) {
				running = false;
				continue;
			}else {
				System.out.println(received);
			}
			try {
				String reply = "Reply from server.";
				byte[] replyByte = reply.getBytes();
				DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length, address, port);
				socket.send(replyPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		socket.close();
	}

}