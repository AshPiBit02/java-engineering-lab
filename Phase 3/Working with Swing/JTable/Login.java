import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
    JDialog loginDialog;

    JFrame adminFrame;

    private String Fieldpassword = "";
    private boolean toggle = false;

    Login() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        // Load and scale image to fit screen
        icon = new ImageIcon("background.jpg");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        // Set background label
        JLabel background = new JLabel(scaledIcon);
        background.setLayout(null);
        this.setContentPane(background);

        // Username label
        username = new JLabel("Username: ");
        username.setBounds(50, 50, 120, 30);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setForeground(Color.white);

        // Username field
        userField = new JTextField();
        userField.setBounds(175, 53, 150, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Password label
        password = new JLabel("Password: ");
        password.setBounds(50, 100, 120, 30);
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setForeground(Color.white);

        // Show/hide password button
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
                    passField.setEchoChar((char) 0);
                    showpassword.setIcon(icon1);
                } else {
                    passField.setEchoChar('•');
                    showpassword.setIcon(icon2);
                }
                toggle = !toggle;
            }
        });

        // Password field
        passField = new JPasswordField();
        passField.setBounds(175, 103, 150, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.add(showpassword);

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
        loginDialog = new JDialog();
        loginDialog.setTitle("Loggedin Successful");
        loginDialog.setBounds(500, 50, 300, 150);
        loginDialog.getContentPane().setBackground(Color.decode("#191932"));
        loginDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));

        // Login button
        login = new JButton("LogIn") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        login.setBounds(100, 170, 200, 30);
        login.setFont(new Font("Arial", Font.PLAIN, 18));
        login.setBackground(new Color(0, 113, 234, 60));
        login.setForeground(Color.WHITE);
        login.setOpaque(false);
        login.setContentAreaFilled(false);
        login.setFocusable(false);
        login.setFocusPainted(false);
        login.setBorderPainted(false);

        login.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                login.setBackground(new Color(0, 113, 234, 80));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                login.setBackground(new Color(0, 113, 234, 60));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                login.setBackground(new Color(0, 113, 234, 120));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                login.setBackground(new Color(0, 113, 234, 60));
            }
        });

        // Login Button Listener
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String UserName = userField.getText();
                char[] pass = passField.getPassword();
                String UserPassword = new String(pass);
                if (UserName.equals("Admin") && UserPassword.equals("123")) {
                    LoggingDailog(UserName);
                }

            }

        });
        // Login panel
        logPanel = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        logPanel.setBounds(450, 200, 400, 240);
        logPanel.setLayout(null);
        logPanel.setOpaque(false);
        logPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#96b1bd"), 5));
        Color base = Color.decode("#a5d0e4");
        logPanel.setBackground(new Color(base.getRed(), base.getGreen(), base.getBlue(), 20));
        logPanel.add(username);
        logPanel.add(password);
        logPanel.add(userField);
        logPanel.add(passField);
        logPanel.add(login);

        this.add(logPanel);
        this.setVisible(true);
    }

    private void updatePass() {
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

    private void LoggingDailog(String name) {
        JLabel msg = new JLabel(
                "<html><div style='text-align: center;'>Greetings Sir, " + name
                        + "!<br>Welcome to the system.</div></html>");
        msg.setFont(new Font("Arial", Font.BOLD, 14));
        msg.setForeground(Color.WHITE);
        loginDialog.add(msg);
        loginDialog.setVisible(true);
        Timer time = new Timer(500, e -> {
            loginDialog.dispose();
            adminFrame = new Admin(); // Open Admin frame
            dispose(); // Close the current login frame
        });
        time.setRepeats(false); // run only once
        time.start();
    }

}