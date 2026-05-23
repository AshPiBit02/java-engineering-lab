import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
    JLabel username;
    JLabel password;
    JButton login;
    JButton showpassword;
    JPanel logPanel;
    JTextField userField;
    JPasswordField passField;
    ImageIcon icon;
    ImageIcon show;

    Login() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load and scale image to fit screen
        icon = new ImageIcon("background.jpg"); // replace with your image path
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        // Set background label
        JLabel background = new JLabel(scaledIcon);
        background.setLayout(null); // allow manual positioning of components

        // Set as content pane
        this.setContentPane(background);
        // this.setVisible(true);

        username = new JLabel("Username: ");
        username.setBounds(50, 50, 120, 30);
        username.setFont(new Font("Arial", Font.PLAIN, 20));

        userField = new JTextField();
        userField.setBounds(175, 53, 150, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));

        password = new JLabel("Password: ");
        password.setBounds(50, 100, 120, 30);
        password.setFont(new Font("Arial", Font.PLAIN, 20));

        // Adding Icon to showpassword button
        show = new ImageIcon("show.jpg");

        showpassword = new JButton(show);
        showpassword.setBounds(125, 2, 30, 20);
        showpassword.setText("show");
        showpassword.setFocusable(false);
        showpassword.setBorder(null);

        passField = new JPasswordField();
        passField.setBounds(175, 103, 150, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.add(showpassword);

        logPanel = new JPanel();
        logPanel.setBounds(450, 200, 400, 240);
        logPanel.setLayout(null);
        logPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#96b1bd"), 5));
        logPanel.add(username);
        logPanel.add(password);
        logPanel.add(userField);
        logPanel.add(passField);

        Color base = Color.decode("#a5d0e4");
        Color transparent = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);
        logPanel.setBackground(transparent);

        this.add(logPanel);
        // this.add(username);
        // this.add(password);
        this.setVisible(true);
    }

}