package client;

import agario.Game;
import agario.Player;

import java.net.DatagramSocket;

/**
* A thread for this class is created every time a response is received from the
* server. It reads the response and updates the players internal data structures
* accordingly. It also ends the clients session if this is indicated by the 
* server in its response.
* 
* @author  Vigneet Sompura
* @version 1.0
* @since   2019-04-17 
*/

public class ResponseHandler implements Runnable {

    private Client client;
    private DatagramSocket clientSocket;
    private byte[] inData = new byte[65500];

    ResponseHandler(DatagramSocket clientSocket, Client client) {
        this.client = client;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Game game = client.readObjectFromSocket(this.inData, this.clientSocket);
            Player player = client.getPlayer(game);
            if (player != null) {
                client.setGame(game);
                client.setRadius(player);
            } else {
                client.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
