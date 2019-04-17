package client;

import agario.Food;
import agario.Game;
import agario.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Vigneet Sompura
 */
public class Client extends Canvas implements Runnable {

    private static final long serialVersionUID = 7846764236102367675L;
    static final int FWIDTH = 1600, FHEIGHT = FWIDTH / 16 * 9;

    private double scale = 1;
    private Thread thread;
    private boolean running = false;
    private Game game;
    // Server communication variables.
    private DatagramSocket clientSocket;
    private InetAddress serverIP;
    private byte[] outData;
    private PlayerHandler playerHandler;
    private JFrame frame;

    private Client(String serverIP) throws IOException, ClassNotFoundException {
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
        String serverIp = args[0];
        Client game = new Client(serverIp);
        new Window(FWIDTH, FHEIGHT, "AgarIO", game);
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
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
                render();
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    private void tick() throws IOException {
        playerHandler.tick();
        String updateMessage = "locationUpdate:" + playerHandler.getPlayerID() + "," + playerHandler.getX() + "," + playerHandler.getY();
        outData = updateMessage.getBytes();

        DatagramPacket out = new DatagramPacket(outData, outData.length, this.serverIP, 4445);
        clientSocket.send(out);

        ResponseHandler responseHandler = new ResponseHandler(clientSocket, this);
        responseHandler.start();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        g.translate(FWIDTH / 2, FHEIGHT / 2);
        if (playerHandler.getRadius() > 64)
            scale = 64 / playerHandler.getRadius();
        g.scale(scale, scale);
        g.translate(-playerHandler.getX(), -playerHandler.getY());
        g.setColor(Color.gray);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.RED);
        g.drawRect(0, 0, WIDTH, HEIGHT);

        List<Food> foodList = game.getFoodList();
        for (Food food : foodList) {
            g.setColor(Color.YELLOW);
            g.fillOval(food.getX() - (int) Food.RADIUS, food.getY() - (int) Food.RADIUS, (int) Food.RADIUS * 2, (int) Food.RADIUS * 2);
        }

        List<Player> players = new ArrayList<>(game.getPlayers().values());
        players.sort(Comparator.comparingDouble(Player::getRadius));

        for (Player player : players) {
            g.setColor(player.getColor());
            g.fillOval(player.getX() - (int) player.getRadius(), player.getY() - (int) player.getRadius(), (int) player.getRadius() * 2, (int) player.getRadius() * 2);
        }

        g.dispose();
        bs.show();
    }

    void setGame(Game game) {
        this.game = game;
    }

    PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    Game readObjectFromSocket(byte[] inData, DatagramSocket clientSocket) throws IOException, ClassNotFoundException {
        DatagramPacket in = new DatagramPacket(inData, inData.length);
        clientSocket.receive(in);
        inData = in.getData();

        ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(inData));
        return (Game) objStream.readObject();
    }
}
