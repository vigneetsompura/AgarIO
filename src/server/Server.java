package server;

import java.net.SocketException;

/**
* Contains the core server logic. It takes helps from the Sender, Receiver and 
* Handler classes in order to manage communication as well as the game objects.
* When the server is onvoked it will listen for UDP packets on the port 4445.
* 
* @author  Vigneet Sompura
* @version 1.0
* @since   2019-04-18 
*/
public class Server implements Runnable {

    private Handler handler;
    private Thread thread;
    private boolean running;
    private Receiver receiver;

    private Server() throws SocketException {
        handler = new Handler();
        handler.addFood();
        receiver = new Receiver(handler);
    }

    public static void main(String[] args) throws SocketException {
        Server server = new Server();
        server.start();
    }

    private synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
        receiver.start();
    }

    private synchronized void stop() {
        try {
            running = false;
            receiver.stop();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        handler.tick();
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
                tick();
                delta--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }
}