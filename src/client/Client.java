package client;

import agario.Game;
import agario.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/**
 * @author Vigneet Sompura
 */
public class Client extends Canvas implements Runnable {

    private static final long serialVersionUID = 7846764236102367675L;
    static final int FWIDTH = 1600, FHEIGHT = FWIDTH / 16 * 9;

    double scale = 1;
    private Thread thread;
    private boolean running = false;
    Game game;
    // Server communication variables.
    private DatagramSocket clientSocket;
    private InetAddress serverIP;
    private byte[] outData;
    private PlayerHandler playerHandler;
    private JFrame frame;

    public Client(String serverIP) throws IOException, ClassNotFoundException {
        this.serverIP = InetAddress.getByName(serverIP);
        clientSocket = new DatagramSocket();
        Random random = new Random();
        int playerID = random.nextInt(Integer.MAX_VALUE);
        sendStartGameMessage(playerID);

        byte[] inData = new byte[65500];
        game = readObjectFromSocket(inData, clientSocket);

        playerHandler = new PlayerHandler(game.getPlayer(playerID));

        this.addMouseMotionListener(new MouseInput(playerHandler));
        this.addMouseListener(new MouseInput(playerHandler));
        this.addKeyListener(new KeyInput(playerHandler));
    }

    private void sendStartGameMessage(int playerID) throws IOException {
        String startMessage = "startGame:" + playerID;
        outData = startMessage.getBytes();

        DatagramPacket out = new DatagramPacket(outData, outData.length, this.serverIP, 4445);
        clientSocket.send(out);
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
//        String serverIp = args[0];
//        Client game = new Client(serverIp);
        new Window(FWIDTH, FHEIGHT, "AgarIO");
    }

    synchronized void start(JFrame frame) {
        this.frame = frame;
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    synchronized void stop() {
        try {
            running = false;
//            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            new Window(FWIDTH, FHEIGHT, "AgarIO", this, true);
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                try {
                    tick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delta--;
            }
            if (running)
                playerHandler.render(this);
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    private void tick() throws IOException {
        playerHandler.tick();
        String updateMessage = playerHandler.getLocationUpdateMessage();
        outData = updateMessage.getBytes();

        DatagramPacket out = new DatagramPacket(outData, outData.length, this.serverIP, 4445);
        clientSocket.send(out);

        ResponseHandler responseHandler = new ResponseHandler(clientSocket, this);
        responseHandler.start();
    }

      void setGame(Game game) {
        this.game = game;
    }

    Game readObjectFromSocket(byte[] inData, DatagramSocket clientSocket) throws IOException, ClassNotFoundException {
        DatagramPacket in = new DatagramPacket(inData, inData.length);
        clientSocket.receive(in);
        inData = in.getData();

        ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(inData));
        return (Game) objStream.readObject();
    }

    Player getPlayer(Game game) {
        int playerID = playerHandler.getPlayerID();
        return game.getPlayer(playerID);
    }

    public int getPlayerID() {
		return playerHandler.getPlayerID();
    }
    
    void setRadius(Player player) {
        playerHandler.setRadius(player.getRadius());
    }
}
