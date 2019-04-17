package client;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vigneet Sompura
 */
class Window extends Canvas {

    private static final long serialVersionUID = 505941576144136988L;

    Window(int width, int height, String title, Client client) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(client);
        frame.setVisible(true);
        client.start(frame);
        client.requestFocusInWindow();
    }
}
