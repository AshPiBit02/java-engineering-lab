package Users;

import java.awt.*;
import javax.swing.*;

public class User extends JFrame {
    ImageIcon icon;
    JPanel leftRectPanel;
    JLabel greet;
    JLabel username;

    public User(String firstname) {

        // Set backgound image
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("PI Corporation");
        // Load and scale image to fit screen
        icon = new ImageIcon("Images/userimg.jpg");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JLabel background = new JLabel(scaledIcon);
        background.setLayout(null);
        this.setContentPane(background);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting Panel for user interaction
        leftRectPanel = new JPanel();
        leftRectPanel.setBounds(0, 0, 250, Toolkit.getDefaultToolkit().getScreenSize().height);
        leftRectPanel.setBackground(new Color(0, 0, 0, 200));
        leftRectPanel.setOpaque(true);
        leftRectPanel.setLayout(null);

        greet = new JLabel();
        greet.setBounds(5, 5, 150, 30);
        greet.setText("Hello, ");
        greet.setForeground(Color.WHITE);
        greet.setFont(new Font("Helvetica Now", Font.PLAIN, 25));
        greet.setHorizontalAlignment(JLabel.LEFT);

        username = new JLabel();
        username.setBounds(75, 30, 200, 30);
        username.setText(firstname);
        username.setForeground(Color.WHITE);
        username.setFont(new Font("Helvetica Now", Font.PLAIN, 25));
        username.setHorizontalAlignment(JLabel.LEFT);

        leftRectPanel.add(greet);
        leftRectPanel.add(username);
        this.add(leftRectPanel);
        this.setVisible(true);
    }

}
