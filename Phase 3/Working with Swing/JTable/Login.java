import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Login extends JFrame {
    JLabel username;
    JLabel password;
    JButton login;
    JButton showpassword;
    JPanel logPanel;
    JTextField userField;
    JPasswordField passField;
    ImageIcon icon;
    ImageIcon icon1;
    ImageIcon icon2;

    Login() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Login Button
        login = new JButton("LogIn");
        login.setBounds(100, 170, 200, 30);
        login.setFont(new Font("Arial", Font.PLAIN, 18));
        Color clrs = Color.decode("#0071ea");
        login.setBackground(new Color(clrs.getRed(), clrs.getGreen(), clrs.getBlue(), 40));
        login.setForeground(Color.WHITE);
        login.setFocusable(false);
        login.setFocusPainted(false);
        login.setBorderPainted(false);

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
        icon1 = new ImageIcon("show.jpg");
        icon2 = new ImageIcon("hide.jpg");
        showpassword = new JButton(icon2);
        showpassword.setBounds(123, 2, 30, 20);
        showpassword.setFocusable(false);
        showpassword.setBorder(null);
        showpassword.setVisible(false);
        showpassword.setFocusPainted(false);
        showpassword.setBorderPainted(false);
        showpassword.setContentAreaFilled(false);
        showpassword.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        showpassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggle) {
                    passField.setEchoChar((char) 0); // shows plain text(visible password)
                    showpassword.setIcon(icon1);
                } else {
                    passField.setEchoChar('•'); // restore masking
                    showpassword.setIcon(icon2);

                }

                toggle = !toggle;

            }
        });

        passField = new JPasswordField();
        passField.setBounds(175, 103, 150, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.add(showpassword);

        // Adding Document listener for continuous checking of passwordField
        passField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePass();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePass();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePass();
            }

        });

        logPanel = new JPanel();
        logPanel.setBounds(450, 200, 400, 240);
        logPanel.setLayout(null);
        logPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#96b1bd"), 5));
        logPanel.add(username);
        logPanel.add(password);
        logPanel.add(userField);
        logPanel.add(passField);
        logPanel.add(login);

        Color base = Color.decode("#a5d0e4");
        Color transparent = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);
        logPanel.setBackground(transparent);

        this.add(logPanel);
        this.setVisible(true);
    }

    private String Fieldpassword = "";
    private boolean toggle = false;

    private void updatePass() {
        // access password
        char[] pwd = passField.getPassword();
        Fieldpassword = new String(pwd);
        if (Fieldpassword.isEmpty()) {
            showpassword.setVisible(false);
            toggle = false;
            passField.setEchoChar('•');
        } else {
            showpassword.setVisible(true);
        }
    }

}