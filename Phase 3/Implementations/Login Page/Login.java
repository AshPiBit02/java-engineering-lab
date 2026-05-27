import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Login extends JFrame {
    protected Login(boolean dummy) { // dummy constructor

    }

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
    ImageIcon bearbase;
    ImageIcon bearlog;

    JLabel bearLabel;

    JButton signupBtn;
    JPanel signPanel;
    JLabel signHeader;
    JDialog emptyDialog;

    // for generic popup
    JLabel emptyMsg;

    private String Fieldpassword = "";
    private boolean isPasswordVisible = false;
    protected String signPassword;
    protected String signConfirmPassword;

    Login() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        icon = new ImageIcon("Images/background.jpg");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        bearLabel = new JLabel();
        bearLabel.setBounds(600, 113, 100, 100);
        bearbase = new ImageIcon("Images/bbase.png");
        bearhide = new ImageIcon("Images/bhide.png");
        bearshow = new ImageIcon("Images/bshow.png");
        bearlog = new ImageIcon("Images/bnice.png");
        bearLabel.setIcon(bearbase);
        bearLabel.setVisible(true);

        JLabel background = new JLabel(scaledIcon);
        background.setLayout(null);
        this.setContentPane(background);

        username = new JLabel("Username: ");
        username.setBounds(50, 70, 120, 30);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setForeground(Color.white);

        userField = new JTextField();
        userField.setBounds(175, 73, 150, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));

        password = new JLabel("Password: ");
        password.setBounds(50, 120, 120, 30);
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setForeground(Color.white);

        icon1 = new ImageIcon("Images/show.jpg");
        icon2 = new ImageIcon("Images/hide.jpg");

        // Password field first, then button
        passField = new JPasswordField();
        passField.setBounds(175, 123, 150, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Generic show button for password field
        showpassword = createShowPassBtn(passField, true, null);
        passField.add(showpassword);

        passField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Fieldpassword.isEmpty()) {
                    bearLabel.setIcon(bearhide);
                } else {
                    bearLabel.setIcon(isPasswordVisible ? bearshow : bearhide);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                bearLabel.setIcon(bearbase);
            }
        });

        loginDialog = new JDialog();
        loginDialog.setTitle("Loggedin Successful");
        loginDialog.setBounds(550, 20, 200, 100);
        loginDialog.getContentPane().setBackground(Color.WHITE);
        loginDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

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

        emptyDialog = new JDialog();
        emptyDialog.setTitle("Empty Field Error");
        emptyMsg = new JLabel(
                "<html><div style='text-align: center;'>Fields Can't be Empty!</div></html>");
        emptyMsg.setFont(new Font("Arial", Font.PLAIN, 14));
        emptyMsg.setForeground(Color.BLACK);
        emptyDialog.add(emptyMsg);
        emptyDialog.setBounds(550, 20, 200, 100);
        emptyDialog.getContentPane().setBackground(Color.decode("#ffffff"));
        emptyDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

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

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login Button clicked");
                String UserName = userField.getText();
                char[] pass = passField.getPassword();
                String UserPassword = new String(pass);
                if (UserName.equals("Admin") && UserPassword.equals("123")) {
                    bearLabel.setIcon(null);
                    bearLabel.setIcon(bearlog);
                    LoggingDailog(UserName);
                }
            }
        });

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

        JSeparator separator = new JSeparator();
        separator.setBounds(90, 210, 220, 1);
        separator.setForeground(Color.BLACK);
        separator.setBackground(Color.BLACK);
        logPanel.add(separator);

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

        signPanel.add(signSeparator);
        signPanel.add(signHeader);

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
        // Remove the mouseListener for mousePressed logic, use this:
        signupBtn.addActionListener(e -> {
            System.out.println("Signup clicked");
            dispose();
            new Signup();
        });

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
                System.out.println("Signup button clicked");
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

    protected JButton createShowPassBtn(JPasswordField targetField, boolean updateBear, String passType) {
        JButton btn = new JButton(icon2);
        if (updateBear) {
            btn.setBounds(123, 2, 30, 20);
        } else {
            btn.setBounds(230, 2, 30, 20);
        }
        btn.setFocusable(false);
        btn.setBorder(null);
        btn.setVisible(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (updateBear) {
                    isPasswordVisible = !isPasswordVisible;
                    if (isPasswordVisible) {
                        targetField.setEchoChar((char) 0);
                        btn.setIcon(icon1);
                        bearLabel.setIcon(bearshow);
                    } else {
                        targetField.setEchoChar('•');
                        btn.setIcon(icon2);
                        bearLabel.setIcon(bearhide);
                    }
                } else {
                    // plain toggle — own local state via array
                    boolean[] visible = getClientProperty(btn);
                    visible[0] = !visible[0];
                    if (visible[0]) {
                        targetField.setEchoChar((char) 0);
                        btn.setIcon(icon1);
                    } else {
                        targetField.setEchoChar('•');
                        btn.setIcon(icon2);
                    }
                }
            }
        });

        // Mirrors original updatePass() DocumentListener
        boolean[] localVisible = { false };
        btn.putClientProperty("localVisible", localVisible);

        targetField.getDocument().addDocumentListener(new DocumentListener() {
            void update() {
                char[] pwd = targetField.getPassword();
                String text = new String(pwd);

                if (text.isEmpty()) {
                    if (updateBear)
                        isPasswordVisible = false;
                    else
                        localVisible[0] = false;
                    targetField.setEchoChar('•');
                    btn.setIcon(icon2);
                    btn.setVisible(false);
                    if (updateBear) {
                        bearLabel.setIcon(bearhide);
                        bearLabel.setVisible(true);
                    }
                } else {
                    btn.setVisible(true);
                    if (updateBear) {
                        bearLabel.setVisible(true);
                        bearLabel.setIcon(isPasswordVisible ? bearshow : bearhide);
                    }
                }

                if (updateBear)
                    Fieldpassword = text; // keep Fieldpassword in sync for focusGained
                else {
                    if (passType.equals("signPassword")) {
                        signPassword = text;
                    } else if (passType.equals("signConfirmPassword")) { // or else for now
                        signConfirmPassword = text;
                    }

                }
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        return btn;
    }

    // helper to retrieve localVisible from btn clientProperty
    private boolean[] getClientProperty(JButton btn) {
        return (boolean[]) btn.getClientProperty("localVisible");
    }

    protected void EmptyDialog() {
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
            adminFrame = new Admin();
            dispose();
        });
        time.setRepeats(false);
        time.start();
    }
}