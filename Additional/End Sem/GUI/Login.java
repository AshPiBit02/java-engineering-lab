import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Demo");
        frame.setLayout(new GridLayout(4, 2, 20, 20));

        JLabel userLabel = new JLabel("Username");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password");
        JPasswordField passField = new JPasswordField();

        JLabel statusLabel = new JLabel("Status");
        JLabel statusField = new JLabel();

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());

                if (user.equals("ashpibit02") && pass.equals("xxxxxxxx")) {
                    statusField.setText("Successful");
                } else {
                    statusField.setText("Unsuccessful");
                }
            }
        });

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(statusLabel);
        frame.add(statusField);
        frame.add(new JLabel()); // filler for layout
        frame.add(submit);

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
