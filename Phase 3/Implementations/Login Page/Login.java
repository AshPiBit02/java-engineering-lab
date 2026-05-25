import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Login extends JFrame {

    // Login Panel Components
    JPanel logPanel;
    JLabel username;
    JLabel password;
    ImageIcon icon2;
    JButton showpassword;
    JTextField userField;
    ImageIcon icon1;
    JPasswordField passField;
    JButton login;
    JLabel logHeader;
    JDialog loginDialog;
    JFrame adminFrame;

    ImageIcon icon;
    ImageIcon bearshow;
    ImageIcon bearhide;

    // bear label
    JLabel bearLabel;

    // Signup Panel Components
    JButton signupBtn;
    JPanel signPanel;
    JLabel signHeader;
    JDialog emptyDialog;

    private String Fieldpassword = "";

    private boolean isPasswordVisible = false;

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

        // Bear label
        bearLabel = new JLabel();
        bearLabel.setBounds(600, 113, 100, 100);
        bearhide = new ImageIcon("bhide.png");
        bearshow = new ImageIcon("bshow.png");
        bearLabel.setIcon(bearhide);
        bearLabel.setVisible(false);

        // Set background label
        JLabel background = new JLabel(scaledIcon);
        background.setLayout(null);
        this.setContentPane(background);

        // Username label
        username = new JLabel("Username: ");
        username.setBounds(50, 70, 120, 30);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setForeground(Color.white);

        // Username field
        userField = new JTextField();
        userField.setBounds(175, 73, 150, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Password label
        password = new JLabel("Password: ");
        password.setBounds(50, 120, 120, 30);
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
                isPasswordVisible = !isPasswordVisible; // toggle state first

                if (isPasswordVisible) {
                    passField.setEchoChar((char) 0); // show password
                    showpassword.setIcon(icon1);
                    bearLabel.setIcon(bearshow);
                } else {
                    passField.setEchoChar('•');
                    showpassword.setIcon(icon2);
                    bearLabel.setIcon(bearhide);
                }
            }
        });

        // Password field
        passField = new JPasswordField();
        passField.setBounds(175, 123, 150, 25);
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
        loginDialog.setBounds(550, 20, 200, 100);
        loginDialog.getContentPane().setBackground(Color.WHITE);
        loginDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        // Login button
        login = new JButton("Login") {
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

        // Empty Field Dialog
        emptyDialog = new JDialog();
        emptyDialog.setTitle("Empty Field Error");
        emptyDialog.setBounds(550, 20, 200, 100);
        emptyDialog.getContentPane().setBackground(Color.decode("#ffffff"));
        emptyDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        JLabel emptyMsg = new JLabel(
                "<html><div style='text-align: center;'>Fields Can't be Empty!</div></html>");
        emptyMsg.setFont(new Font("Arial", Font.PLAIN, 14));
        emptyMsg.setForeground(Color.BLACK);
        emptyDialog.add(emptyMsg);

        login.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                login.setBackground(Color.decode("#050791"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                login.setBackground(new Color(0, 113, 234, 60));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                login.setBackground(Color.decode("#0008fa"));
                if (userField.getText().isEmpty() || Fieldpassword.isEmpty()) {
                    EmptyDialog();
                }
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

        logHeader = new JLabel();
        logHeader.setText("Login");
        logHeader.setBounds(155, 10, 100, 40);
        logHeader.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        logHeader.setForeground(Color.decode("#c5ff06"));
        logHeader.setHorizontalAlignment(JLabel.CENTER);

        JSeparator logSeparator = new JSeparator();
        logSeparator.setBounds(135, 48, 130, 1);
        logSeparator.setForeground(Color.decode("#0f2541"));
        logSeparator.setBackground(Color.decode("#0f2541"));

        logPanel.setBounds(450, 200, 400, 270);
        logPanel.setLayout(null);
        logPanel.setOpaque(false);
        logPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#96b1bd"), 5));
        Color base = Color.decode("#a5d0e4");
        logPanel.setBackground(new Color(base.getRed(), base.getGreen(), base.getBlue(), 20));

        logPanel.add(logHeader);
        logPanel.add(logSeparator);
        logPanel.add(username);
        logPanel.add(password);
        logPanel.add(userField);
        logPanel.add(passField);

        // Line separator
        JSeparator separator = new JSeparator();
        separator.setBounds(90, 210, 220, 1);
        separator.setForeground(Color.BLACK);
        separator.setBackground(Color.BLACK);
        logPanel.add(separator);

        // Signup Panel
        signPanel = new JPanel();
        signPanel.setBounds(475, 50, 400, 650);
        signPanel.setLayout(null);
        Color clrs = Color.decode("#0015ff");
        signPanel.setBackground(new Color(clrs.getRed(), clrs.getGreen(), clrs.getBlue(), 20));

        signHeader = new JLabel();
        signHeader.setText("Signup");
        signHeader.setBounds(140, 10, 120, 40);
        signHeader.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        signHeader.setForeground(Color.decode("#ffffff"));
        signHeader.setHorizontalAlignment(JLabel.CENTER);
        signPanel.setVisible(false);

        JSeparator signSeparator = new JSeparator();
        signSeparator.setBounds(30, 55, 340, 1);
        signSeparator.setForeground(Color.decode("#bdff8e"));
        signSeparator.setBackground(Color.decode("#bdff8e"));

        // Signup Components

        signPanel.add(signSeparator);
        signPanel.add(signHeader);

        // Register Button
        signupBtn = new JButton("Sign up") {
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

        signupBtn.setBounds(100, 220, 200, 30);
        signupBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        signupBtn.setBackground(Color.decode("#046813"));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setOpaque(false);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setFocusable(false);
        signupBtn.setFocusPainted(false);
        signupBtn.setBorderPainted(false);

        signupBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                signupBtn.setBackground(Color.decode("#00a118"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                signupBtn.setBackground(Color.decode("#046813"));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                signupBtn.setBackground(Color.decode("#00ff26"));
                dispose();
                new Signup();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                signupBtn.setBackground(Color.decode("#046813"));
            }
        });

        logPanel.add(login);
        logPanel.add(signupBtn);

        this.add(bearLabel);
        this.add(signPanel);
        this.add(logPanel);
        this.setVisible(true);
    }

    private void updatePass() {
        char[] pwd = passField.getPassword();
        Fieldpassword = new String(pwd);

        if (Fieldpassword.isEmpty()) {
            isPasswordVisible = false;
            passField.setEchoChar('•');
            showpassword.setIcon(icon2);
            showpassword.setVisible(false);
            bearLabel.setIcon(bearhide);
            bearLabel.setVisible(false);
        } else {
            showpassword.setVisible(true);
            bearLabel.setVisible(true);
        }
    }

    private void EmptyDialog() {
        emptyDialog.setVisible(true);
        Timer time = new Timer(1000, e -> emptyDialog.setVisible(false));
        time.setRepeats(false);
        time.start();
    }

    private void LoggingDailog(String name) {
        JLabel msg = new JLabel(
                "<html><div style='text-align: center;'>Greetings Sir, " + name
                        + "!<br>Welcome to the system.</div></html>");
        msg.setFont(new Font("Arial", Font.PLAIN, 14));
        msg.setForeground(Color.BLACK);
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