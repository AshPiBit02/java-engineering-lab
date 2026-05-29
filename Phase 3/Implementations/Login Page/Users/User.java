package Users;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class User extends JFrame {
    ImageIcon icon;

    public User(String userid) {
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

        this.setVisible(true);
    }

}
