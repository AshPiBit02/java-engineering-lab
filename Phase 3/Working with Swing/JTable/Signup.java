import java.awt.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Signup extends Login {
    JLabel fullName;
    JTextField fname;
    JLabel signUser;
    JTextField signUsername;
    JLabel emailAdd;
    JPasswordField signPass;
    JPasswordField signCPass;
    JButton signup;

    Signup() {
        fullName = new JLabel("Full Name");
        fullName.setBounds(50, 100, 100, 30);
        fullName.setFont(new Font("Arial", Font.PLAIN, 20));
        fullName.setLayout(null);
        fullName.setForeground(Color.WHITE);
        fullName.setVisible(true);

        fname = new JTextField();
        fname.setBounds(70, 135, 260, 25);
        fname.setLayout(new FlowLayout());
        fname.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        fname.setForeground(Color.BLACK);

        signUser = new JLabel("Username");
        signUser.setBounds(50, 185, 100, 30);
        signUser.setFont(new Font("Arial", Font.PLAIN, 20));
        signUser.setLayout(null);
        signUser.setForeground(Color.WHITE);
        signUser.setVisible(true);

        signUsername = new JTextField();
        signUsername.setBounds(70, 220, 260, 25);
        signUsername.setLayout(new FlowLayout());
        signUsername.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        signUsername.setForeground(Color.BLACK);

        signPanel.add(fname);
        signPanel.add(fullName);
        signPanel.add(signUser);
        signPanel.add(signUsername);

        logPanel.setVisible(false);
        signPanel.setVisible(true);

    }

}
