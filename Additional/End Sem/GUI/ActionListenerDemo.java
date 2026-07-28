import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ActionListenerDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Action Listener Demo");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JButton btn = new JButton("Click me");

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked");
            }
        });

        frame.add(btn, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
