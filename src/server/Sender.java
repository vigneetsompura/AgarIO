package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
* A sender class whose role is send messages to clients based on what the 
* message type is. It spawns a new thread and delegates the reply logic to
* that thread.
* 
* 
* @author  Vigneet Sompura
* @version 1.0
* @since   2019-04-18 
*/
public class Sender implements Runnable {

    private Handler handler;
    private InetAddress address;
    private int port;
    private DatagramSocket socket;

    public Sender(DatagramSocket socket, InetAddress address, int port, Handler handler) {
        this.handler = handler;
        this.address = address;
        this.port = port;
        this.socket = socket;
    }

    @Override
    public void run() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        writeObject(byteStream);
        DatagramPacket replyPacket = generateReplyPacket(byteStream);
        sendReply(replyPacket);
    }

    private DatagramPacket generateReplyPacket(ByteArrayOutputStream byteStream) {
        byte[] message = byteStream.toByteArray();
        return new DatagramPacket(message, message.length, address, port);
    }

    private void sendReply(DatagramPacket replyPacket) {
        try {
            socket.send(replyPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(ByteArrayOutputStream byteStream) {
        try (ObjectOutput output = new ObjectOutputStream(byteStream)) {
            output.writeObject(handler.getGame());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
