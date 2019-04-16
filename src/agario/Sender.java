package agario;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Sender implements Runnable {
	
	private Handler handler;
	private Player p;

	// Server communication variables.
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	private byte[] outData;
    private byte[] inData;
    
    public Sender(Handler handler, Player p) throws UnknownHostException, SocketException {
    	this.handler = handler;
    	this.p = p;
    	clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");

    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean running = true;
			while (running) {
			try {				
				inData = new byte[1024];
	            outData = new byte[1024];
	            
				String sentence = "locationUpdate:"+p.getId()+","+p.getX()+","+p.getY();
	            outData = sentence.getBytes();
	            
	            DatagramPacket out = new DatagramPacket(outData, outData.length, IPAddress, 4445);
	            clientSocket.send(out);
	            
	            DatagramPacket in = new DatagramPacket(inData, inData.length);
	            clientSocket.receive(in);
	            
	            inData = in.getData();
	            
	            ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(inData));
	            handler = (Handler) objStream.readObject();
	            
	            if (handler.getPlayer(p.getId()) == null) {
	            	running = false;
	            } else {
	            	p.setRadius(handler.getRadius(p.getId()));
	            }
	            
			}catch (IOException | ClassNotFoundException e) {
				
			}
		}
		
	}

}
