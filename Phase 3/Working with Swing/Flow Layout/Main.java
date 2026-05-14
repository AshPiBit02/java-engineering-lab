import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.Flow;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        // Layout manager -> Defines the natural layout for components within a
        // container.
        // FlowLayout -> Places components in a row, sized at their preferred size.
        // If the horizontal space in the container is too small.
        // The flowLayout class uses the next avaliable row.

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 250));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new FlowLayout());

        // Array to hold separate button objects
        JButton[] buttons = new JButton[10];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(String.valueOf(i));
            buttons[i].setFocusable(false);
            panel.add(buttons[i]);
        }

        // Prints the button when clicked
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(e -> {
                JButton b = (JButton) e.getSource();
                System.out.println("Clicked: " + b.getText());
            });
        }

        frame.add(panel);
        frame.setVisible(true);
    }

}
