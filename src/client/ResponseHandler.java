package client;

import agario.Game;
import agario.Player;

import java.net.DatagramSocket;

public class ResponseHandler implements Runnable {

    private Client client;
    // Server communication variables.
    private DatagramSocket clientSocket;
    private byte[] inData = new byte[65500];

    ResponseHandler(DatagramSocket clientSocket, Client client) {
        this.client = client;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Game game = client.readObjectFromSocket(this.inData,this.clientSocket);
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
