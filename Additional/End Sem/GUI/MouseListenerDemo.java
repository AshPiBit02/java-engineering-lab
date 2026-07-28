import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseListenerDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mouse Listener DEMO");
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setBounds(20, 20, 160, 140);

        JButton btn = new JButton("Clicke Me");
        btn.setBounds(50, 170, 100, 30);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.setBackground(Color.RED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                panel.setBackground(Color.GREEN);
            }
        });

        frame.add(panel);
        frame.add(btn);
        frame.setSize(220, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

    }
}
