package server;

import agario.Game;
import agario.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

/**
* A receiver class whose role is spawn a new thread every time it receives a 
* new message from any of the clients. The communication protocol used 
* throughout the project is UDP and hence Datagrame sockets are used.
* 
* 
* @author  Vigneet Sompura
* @version 1.0
* @since   2019-04-18 
*/
public class Receiver implements Runnable {

    private Handler handler;
    private DatagramSocket socket;
    private boolean running;
    private byte[] messageBytes = new byte[65500];
    private Thread thread;

    Receiver(Handler handler) throws SocketException {
        socket = new DatagramSocket(4445);
        this.handler = handler;
    }

    synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    synchronized void stop() {
        try {
            running = false;
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Server thread started.");
        while (running) {
            DatagramPacket packet = readPacket();

            int port = packet.getPort();
            InetAddress address = packet.getAddress();
            String received = new String(packet.getData(), 0, packet.getLength());
            executeCommand(received);
            startSenderThread(address, port);
        }
        socket.close();
    }

    private DatagramPacket readPacket() {
        DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

    private void executeCommand(String received) {
        String command = received.split(":")[0];
        String parameters = received.split(":")[1];
        switch (command) {
            case "startGame":
                startGame(parameters);
                break;
            case "locationUpdate":
                locationUpdate(parameters);
                break;
            case "endGame":
                endGame(parameters);
                break;
        }
    }

    private void startGame(String parameters) {
        int id = Integer.parseInt(parameters.trim());
        Random random = new Random();
        Player newPlayer = new Player(id, random.nextInt(Game.WIDTH - 64) + 32, random.nextInt(Game.HEIGHT - 64) + 32);
        handler.addPlayer(newPlayer);
        System.out.println("New player joined: " + id);
    }

    private void locationUpdate(String parameters) {
        String[] p = parameters.split(",");
        Player player = handler.getPlayer(Integer.parseInt(p[0].trim()));
        if (player != null) {
            player.setXY(Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
        }
    }

    private void endGame(String parameters) {
        int playerID = Integer.parseInt(parameters);
        handler.removePlayer(playerID);
        System.out.println("Player exited: " + playerID);
    }

    private void startSenderThread(InetAddress address, int port) {
        Sender sender = new Sender(socket, address, port, handler);
        Thread thread = new Thread(sender);
        thread.start();
    }
}
