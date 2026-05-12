import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        // JPanel -> a GUI component that functions as a container to hold other
        // components
        ImageIcon icon = new ImageIcon("looo.jpg");

        JLabel label = new JLabel();
        label.setText("Hi! This is C++");
        label.setIcon(icon);
        label.setVerticalAlignment(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.CENTER);

        JPanel redpanel = new JPanel();
        redpanel.setBackground(Color.red);
        redpanel.setBounds(0, 0, 250, 250);

        JPanel greenpanel = new JPanel();
        greenpanel.setBackground(Color.green);
        greenpanel.setBounds(250, 0, 250, 250);

        JPanel bluepanel = new JPanel();
        bluepanel.setBackground(Color.blue);
        bluepanel.setBounds(500, 0, 250, 250);

        JPanel graypanel = new JPanel();
        graypanel.setBackground(Color.gray);
        graypanel.setBounds(0, 250, 750, 250);
        graypanel.setLayout(new BorderLayout());

        JFrame frame = new JFrame("My Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(750, 750);
        frame.setVisible(true);
        graypanel.add(label);
        frame.add(redpanel);
        frame.add(greenpanel);
        frame.add(bluepanel);
        frame.add(graypanel);

    }

}
