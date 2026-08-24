import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class swingMouse {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);

        JButton btn = new JButton("Click me");
        int clickCount[] = { 0 };
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickCount[0]++;
                if (clickCount[0] > 7) {
                    panel.setBackground(Color.RED);
                    System.out.println("Request Limit hit: " + clickCount[0]);
                } else {
                    panel.setBackground(Color.GREEN);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                panel.setBackground(Color.YELLOW);
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(btn, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 300);
        frame.setVisible(true);
    }
}