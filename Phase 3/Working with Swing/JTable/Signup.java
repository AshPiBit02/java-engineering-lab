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
    JTextField email;
    JLabel signPassLbl;
    JPasswordField signpass;
    JLabel signCpassLbl;
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

        emailAdd = new JLabel("Email Address");
        emailAdd.setBounds(50, 270, 150, 30);
        emailAdd.setFont(new Font("Arial", Font.PLAIN, 20));
        emailAdd.setLayout(null);
        emailAdd.setForeground(Color.WHITE);
        emailAdd.setVisible(true);

        email = new JTextField();
        email.setBounds(70, 305, 260, 25);
        email.setLayout(new FlowLayout());
        email.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        email.setForeground(Color.BLACK);

        signPassLbl = new JLabel("Password");
        signPassLbl.setBounds(50, 360, 150, 30);
        signPassLbl.setFont(new Font("Arial", Font.PLAIN, 20));
        signPassLbl.setLayout(null);
        signPassLbl.setForeground(Color.WHITE);
        signPassLbl.setVisible(true);

        signpass = new JPasswordField();
        signpass.setBounds(70, 395, 260, 25);
        signpass.setLayout(new FlowLayout());
        signpass.setForeground(Color.BLACK);

        signCpassLbl = new JLabel("Confirm Password");
        signCpassLbl.setBounds(50, 445, 200, 30);
        signCpassLbl.setFont(new Font("Arial", Font.PLAIN, 20));
        signCpassLbl.setLayout(null);
        signCpassLbl.setForeground(Color.WHITE);
        signCpassLbl.setVisible(true);

        signCPass = new JPasswordField();
        signCPass.setBounds(70, 480, 260, 25);
        signCPass.setLayout(new FlowLayout());
        signCPass.setForeground(Color.BLACK);

        signPanel.add(fname);
        signPanel.add(fullName);
        signPanel.add(signUser);
        signPanel.add(signUsername);
        signPanel.add(emailAdd);
        signPanel.add(email);
        signPanel.add(signPassLbl);
        signPanel.add(signpass);
        signPanel.add(signCpassLbl);
        signPanel.add(signCPass);

        logPanel.setVisible(false);
        signPanel.setVisible(true);

    }

}
