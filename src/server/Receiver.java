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

    public Receiver(Handler handler) throws SocketException {
        socket = new DatagramSocket(4445);
        this.handler = handler;
    }

    synchronized public void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    synchronized public void stop() {
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
            DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());
            //System.out.println( received);
            String command = received.split(":")[0];
            String parameters = received.split(":")[1];

            switch (command) {
                case "startGame": {
                    int id = Integer.parseInt(parameters.trim());
                    Random random = new Random();
                    Player newPlayer = new Player(id, random.nextInt(Game.WIDTH - 64) + 32, random.nextInt(Game.HEIGHT - 64) + 32);
                    handler.addPlayer(newPlayer);
                    System.out.println("New player joined: " + id);
                    //send handler to player
                    Sender sender = new Sender(socket, address, port, handler);
                    Thread thread = new Thread(sender);
                    thread.start();

                    break;
                }
                case "locationUpdate":
                    String[] p = parameters.split(",");
                    Player player = handler.getPlayer(Integer.parseInt(p[0].trim()));
                    if (player != null) {
                        player.setXY(Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
                        //send handler to player
                        Sender sender = new Sender(socket, address, port, handler);
                        Thread thread = new Thread(sender);
                        thread.start();
                    }

                    break;
                case "endGame": {
                    int playerID = Integer.parseInt(parameters);
                    handler.removePlayer(playerID);
                    Sender sender = new Sender(socket, address, port, handler);
                    Thread thread = new Thread(sender);
                    thread.start();
                    break;
                }
            }
        }
        socket.close();
    }
}
