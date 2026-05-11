import java.awt.*;
import java.awt.event.*;

public class InputDemo {
    public static void main(String[] args) {
        Frame f = new Frame("Input Demostration");

        Label l1 = new Label("Username:");
        Label l2 = new Label("Password:");

        TextField tf1 = new TextField(20);
        TextField tf2 = new TextField(20);
        tf2.setEchoChar('*'); // hide password characteres

        Button b = new Button("Login");

        l1.setBounds(50, 50, 80, 30);
        tf1.setBounds(150, 50, 150, 30);
        l2.setBounds(50, 100, 80, 30);
        tf2.setBounds(150, 100, 150, 30);
        b.setBounds(150, 150, 80, 30);

        f.add(l1);
        f.add(tf1);
        f.add(l2);
        f.add(tf2);
        f.add(b);

        f.setSize(400, 250);
        f.setLayout(null);
        f.setVisible(true);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = tf1.getText();
                String pass = tf2.getText();
                System.out.println("Username: " + user);
                System.out.println("Password: " + pass);
            }
        });

        // Handle window closing
        f.addWindowFocusListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });
    }

}
