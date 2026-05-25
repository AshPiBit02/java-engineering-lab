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
        fname.setBounds(70, 135, 260, 30);
        fname.setLayout(new FlowLayout());
        fname.setFont(new Font("Arial", Font.PLAIN, 18));
        fname.setForeground(Color.WHITE);

        signPanel.add(fname);
        signPanel.add(fullName);

        logPanel.setVisible(false);
        signPanel.setVisible(true);

    }

}
