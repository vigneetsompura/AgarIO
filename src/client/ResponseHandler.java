package client;

import agario.Game;

import java.net.DatagramSocket;

public class ResponseHandler implements Runnable {

    private Client client;
    // Server communication variables.
    private DatagramSocket clientSocket;
    private byte[] inData = new byte[65500];

    public ResponseHandler(DatagramSocket clientSocket, Client client) {
        this.client = client;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Game game = client.readObjectFromSocket(this.inData,this.clientSocket);
            int playerID = client.getPlayerHandler().getPlayerID();
            if (game.getPlayer(playerID) != null) {
                client.setGame(game);
                client.getPlayerHandler().setRadius(game.getPlayer(playerID).getRadius());
            } else {
                client.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
