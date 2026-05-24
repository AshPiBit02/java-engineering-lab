import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Admin extends JFrame {
    JButton logout;
    JDialog logoutDialog;
    JLabel headerLabel;

    ImageIcon icon;

    Admin() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sales Record");
        // Load and scale image to fit screen
        icon = new ImageIcon("inventory.jpg");
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

        headerLabel = new JLabel();
        headerLabel.setBounds(550, 10, 400, 100);
        headerLabel.setText("Sales Details");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 45));
        headerLabel.setLayout(new FlowLayout());

        logout = new JButton("Logout") {
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
        logout.setBounds(575, 650, 200, 50);
        logout.setFont(new Font("Arial", Font.PLAIN, 20));
        logout.setBackground(Color.decode("#db5050"));
        logout.setForeground(Color.WHITE);
        logout.setOpaque(false);
        logout.setContentAreaFilled(false);
        logout.setFocusable(false);
        logout.setFocusPainted(false);
        logout.setBorderPainted(false);

        logoutDialog = new JDialog();
        logoutDialog.setTitle("Logout Successfully");
        logoutDialog.add(new JLabel("Logged from Admin"));
        logoutDialog.setBounds(500, 50, 300, 150);
        logoutDialog.setLayout(new FlowLayout());

        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                logout.setBackground(Color.decode("#a21d1d"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                logout.setBackground(Color.decode("#db5050"));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                logout.setBackground(Color.decode("#f00000"));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                logout.setBackground(Color.decode("#db5050"));
            }
        });
        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logoutDialog.setVisible(true);
                Timer time = new Timer(1000, ev -> {
                    logoutDialog.dispose();
                    new Login();
                    dispose();
                });
                time.setRepeats(false); // run only once
                time.start();
            }

        });

        this.add(headerLabel);
        this.add(logout);
        this.setVisible(true);

    }

}
