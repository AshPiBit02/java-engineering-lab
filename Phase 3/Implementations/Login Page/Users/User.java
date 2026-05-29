package Users;

import java.awt.*;
import javax.swing.*;

public class User extends JFrame {
    ImageIcon icon;
    JPanel leftRectPanel;

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
        leftRectPanel.setBackground(new Color(195, 88, 29, 200));
        leftRectPanel.setOpaque(true);

        this.add(leftRectPanel);
        this.setVisible(true);
    }

}
