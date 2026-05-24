import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.util.*;

import javax.swing.JTable;

public class Admin extends JFrame {
    JButton logout;
    JDialog logoutDialog;
    JLabel salesheaderLabel;
    JLabel logheaderLabel;
    JPanel tablePanel;
    ImageIcon icon;
    JTable table;
    JPanel menuPanel;
    JLabel adminUsage;
    JButton salesBtn;
    JButton logBtn;

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

        salesheaderLabel = new JLabel();
        salesheaderLabel.setBounds(700, 10, 400, 100);
        salesheaderLabel.setText("Sales Details");
        salesheaderLabel.setFont(new Font("Arial", Font.BOLD, 45));
        salesheaderLabel.setLayout(new FlowLayout());
        salesheaderLabel.setVisible(false);

        logheaderLabel = new JLabel();
        logheaderLabel.setBounds(700, 10, 400, 100);
        logheaderLabel.setText("Log Details");
        logheaderLabel.setFont(new Font("Arial", Font.BOLD, 45));
        logheaderLabel.setLayout(new FlowLayout());
        logheaderLabel.setVisible(false);

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
        logout.setBounds(50, 675, 200, 50);
        logout.setFont(new Font("Arial", Font.PLAIN, 25));
        logout.setBackground(Color.decode("#1d0000"));
        logout.setForeground(Color.WHITE);
        logout.setOpaque(false);
        logout.setContentAreaFilled(false);
        logout.setFocusable(false);
        logout.setFocusPainted(false);
        logout.setBorderPainted(false);

        logoutDialog = new JDialog();
        logoutDialog.setTitle("Logout Successfully");
        logoutDialog.setBounds(500, 50, 300, 150);
        logoutDialog.getContentPane().setBackground(Color.decode("#2c1515"));
        JLabel msg = new JLabel(
                "<html><div style='text-align: center;'>LoggedOut<br>from Admin System!</div></html>");
        msg.setFont(new Font("Arial", Font.BOLD, 14));
        msg.setForeground(Color.WHITE);
        logoutDialog.add(msg);
        logoutDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));

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
                Timer time = new Timer(500, ev -> {
                    logoutDialog.dispose();
                    new Login();
                    dispose();
                });
                time.setRepeats(false); // run only once
                time.start();
            }

        });

        adminUsage = new JLabel();
        adminUsage.setBounds(0, 0, 297, 50);
        adminUsage.setText("Admin Interface");
        adminUsage.setHorizontalAlignment(JLabel.CENTER);
        adminUsage.setFont(new Font("Arial", Font.BOLD, 25));
        adminUsage.setForeground(Color.WHITE);
        adminUsage.setBackground(Color.decode("#003df4"));
        adminUsage.setOpaque(true);
        adminUsage.setLayout(null);

        salesBtn = new JButton("Sales Details") {
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

        salesBtn.setBounds(25, 250, 250, 30);
        salesBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        salesBtn.setBackground(new Color(0, 113, 234, 60));
        salesBtn.setForeground(Color.WHITE);
        salesBtn.setOpaque(false);
        salesBtn.setContentAreaFilled(false);
        salesBtn.setFocusable(false);
        salesBtn.setFocusPainted(false);
        salesBtn.setBorderPainted(false);

        salesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 80));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 60));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 120));
                logheaderLabel.setVisible(false);
                salesheaderLabel.setVisible(true);
                tablePanel.setVisible(true);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 60));
            }
        });

        logBtn = new JButton("Log Details") {
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

        logBtn.setBounds(25, 300, 250, 30);
        logBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        logBtn.setBackground(new Color(0, 113, 234, 60));
        logBtn.setForeground(Color.WHITE);
        logBtn.setOpaque(false);
        logBtn.setContentAreaFilled(false);
        logBtn.setFocusable(false);
        logBtn.setFocusPainted(false);
        logBtn.setBorderPainted(false);

        logBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                logBtn.setBackground(new Color(0, 113, 234, 80));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                logBtn.setBackground(new Color(0, 113, 234, 60));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                logBtn.setBackground(new Color(0, 113, 234, 120));
                salesheaderLabel.setVisible(false);
                tablePanel.setVisible(false);
                logheaderLabel.setVisible(true);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                logBtn.setBackground(new Color(0, 113, 234, 60));
            }
        });

        menuPanel = new JPanel();
        menuPanel.setBounds(0, 0, 300, Toolkit.getDefaultToolkit().getScreenSize().height);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#747c87"), 3));
        menuPanel.setBackground(new Color(9, 28, 87));
        menuPanel.setLayout(null);
        menuPanel.setOpaque(true);
        menuPanel.add(adminUsage);
        menuPanel.add(logBtn);
        menuPanel.add(salesBtn);
        menuPanel.add(logout);
        this.add(menuPanel);

        tablePanel = new JPanel();
        tablePanel.setBounds(305, 125, 1060, 680);
        tablePanel.setOpaque(false);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        table.setGridColor(Color.RED);

        table.setSelectionBackground(Color.BLUE);
        table.setSelectionForeground(Color.WHITE);
        table.setBackground(Color.decode("#95a9dc"));

        table.getTableHeader().setBackground(Color.decode("#040051"));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setBorder(BorderFactory.createLineBorder(Color.decode("#4a4a4e"), 3));

        Border outer = BorderFactory.createLineBorder(Color.decode("#4a4a4e"), 3);
        Border inner = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        table.setBorder(BorderFactory.createCompoundBorder(outer, inner));

        table.setFont(new Font("Serif", Font.PLAIN, 25));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 30));
        table.setRowHeight(32);
        table.getTableHeader().setPreferredSize(new Dimension(0, 37));
        String filePath = "demo.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<String[]> rows = new ArrayList<>();
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (firstLine) {
                    for (String header : values) {
                        model.addColumn(header);
                    }
                    firstLine = false;
                } else {
                    rows.add(values);
                }
            }
            for (String[] row : rows) {
                model.addRow(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.decode("#47477e"));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setVisible(false);
        this.add(tablePanel);
        this.add(salesheaderLabel);
        this.add(logheaderLabel);
        this.setVisible(true);

    }

}
