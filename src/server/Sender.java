package server;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender implements Runnable {

	private Handler handler;
	private InetAddress address;
	private int port; 
	private DatagramSocket socket;
	
	public Sender(DatagramSocket socket, InetAddress address, int port, Handler handler) {
		this.handler = handler;
		this.address = address;
		this.port = port;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutput output = new ObjectOutputStream(byteStream);
		output.writeObject(handler);
		output.close();
		byte[] message = byteStream.toByteArray();
		
		DatagramPacket replyPacket = new DatagramPacket(message, message.length, address, port);
		socket.send(replyPacket);
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
