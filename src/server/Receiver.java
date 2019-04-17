package server;

import agario.Game;
import agario.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

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
            String received = new String(packet.getData(), 0, packet.getLength());
            String command = received.split(":")[0];
            String parameters = received.split(":")[1];
            InetAddress address = packet.getAddress();
            executeCommand(port, command, parameters, address);
        }
        socket.close();
    }

    private void executeCommand(int port, String command, String parameters, InetAddress address) {
        switch (command) {
            case "startGame":
                startGame(address, port, parameters);
                break;
            case "locationUpdate":
                locationUpdate(address, port, parameters);
                break;
            case "endGame":
                endGame(address, port, parameters);
                break;
        }
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

    private void startGame(InetAddress address, int port, String parameters) {
        int id = Integer.parseInt(parameters.trim());
        Random random = new Random();
        Player newPlayer = new Player(id, random.nextInt(Game.WIDTH - 64) + 32, random.nextInt(Game.HEIGHT - 64) + 32);
        handler.addPlayer(newPlayer);
        System.out.println("New player joined: " + id);
        startSenderThread(address, port);
    }

    private void locationUpdate(InetAddress address, int port, String parameters) {
        String[] p = parameters.split(",");
        Player player = handler.getPlayer(Integer.parseInt(p[0].trim()));
        if (player != null) {
            player.setXY(Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
            startSenderThread(address, port);
        }
    }

    private void endGame(InetAddress address, int port, String parameters) {
        int playerID = Integer.parseInt(parameters);
        handler.removePlayer(playerID);
        startSenderThread(address, port);
    }

    private void startSenderThread(InetAddress address, int port) {
        Sender sender = new Sender(socket, address, port, handler);
        Thread thread = new Thread(sender);
        thread.start();
    }
}
