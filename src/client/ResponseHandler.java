package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import agario.Game;
import agario.Player;

public class ResponseHandler implements Runnable {
	
	Client client;
	Thread thread;
	// Server communication variables.
	private DatagramSocket clientSocket;
    private byte[] inData = new byte[65500];
    
    public ResponseHandler(DatagramSocket clientSocket,Client client) throws UnknownHostException, SocketException {
    	this.client = client;
    	this.clientSocket = clientSocket;

    }
    
	@Override
	public void run() {
		try {
		DatagramPacket in = new DatagramPacket(inData, inData.length);
        clientSocket.receive(in);
        
        inData = in.getData();
        
        ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(inData));
        Game game = (Game) objStream.readObject();
        //System.out.println();
        //System.out.println(game.getFoodList().size());
        //System.out.println(game.getPlayers().size());
        Player p = game.getPlayer(client.getPlayerHandler().getPlayerID());
        System.out.println(p.getX()+","+p.getY()+","+p.getRadius());
        if(game.getPlayer(client.getPlayerHandler().getPlayerID())!=null) {
	        client.setGame(game);        
			client.getPlayerHandler().setRadius(game.getPlayer(client.getPlayerHandler().getPlayerID()).getRadius());
        }else {
        	client.stop();
        }
        
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}
		

}
