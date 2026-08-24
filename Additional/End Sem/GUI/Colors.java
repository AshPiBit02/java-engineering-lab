import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Colors {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Colors");

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);

        JButton btn = new JButton("Click");
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.setBackground(Color.GREEN);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                panel.setBackground(Color.RED);
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(btn, BorderLayout.SOUTH);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
