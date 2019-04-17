package client;

import agario.Game;
import agario.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

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
            Game game = readObjectFromSocket();
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

    private Game readObjectFromSocket() throws IOException, ClassNotFoundException {
        DatagramPacket in = new DatagramPacket(inData, inData.length);
        clientSocket.receive(in);
        inData = in.getData();

        ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(inData));
        return (Game) objStream.readObject();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
