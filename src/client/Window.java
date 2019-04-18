package client;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Vigneet Sompura
 */
class Window extends Canvas {

	private static String serverIp;
    private static final long serialVersionUID = 505941576144136988L;

    Window(int width, int height, String title, Client client) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);       
        frame.add(client);
        frame.setVisible(true);
        client.start(frame);
        client.requestFocusInWindow();
        frame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		
        		String updateMessage = "endGame:"+client.getPlayerID();
                byte[] outData = updateMessage.getBytes();
                
                try {
        		DatagramSocket clientSocket = new DatagramSocket();
        		DatagramPacket out = new DatagramPacket(outData, outData.length, InetAddress.getByName(serverIp), 4445);
                clientSocket.send(out);
                clientSocket.close();
                System.exit(0);
                
                }catch(Exception exception) {
                	exception.printStackTrace();
                }
        	}
        });
        
    }
    
    Window(int width, int height, String title) {
        JFrame frame = new JFrame(title);
        frame.setSize(300,300); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label1 = new JLabel("Test");
        label1.setText("Enter server IP:");
        label1.setBounds(10, 10, 100, 100);
        
        JTextField textfield= new JTextField();
		textfield.setBounds(110, 50, 130, 30);
		
        JButton b=new JButton("Submit");    
		b.setBounds(100,100,140, 40);
		frame.add(label1);
		frame.add(textfield);
		frame.add(b);
		frame.setLayout(null); 
		
		b.addActionListener(new ActionListener() {
	        
			public void actionPerformed(ActionEvent arg0) {
					serverIp = textfield.getText();
					Client game = null;
					try {
						game = new Client(serverIp);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					new Window(width, height, "AgarIO", game);
			}          
	      });

        frame.setVisible(true);

    }
    
    
}
