/**
 * 
 */
package client;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;

/**
 * @author Vigneet Sompura
 *
 */
public class Window extends Canvas {

	private static final long serialVersionUID = 505941576144136988L;

	/**
	 * 
	 */
	public Window(int width, int height, String title, Client client) {
		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(client);
		frame.setVisible(true);
		client.start(frame);
		client.requestFocusInWindow();
	}

	/**
	 * @param arg0
	 */
	public Window(GraphicsConfiguration arg0) {
		super(arg0);
	}

}
