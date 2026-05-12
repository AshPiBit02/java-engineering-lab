package frames;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.ImageIcon;

public class MyFrame extends JFrame {
    public MyFrame(String Ftitle) {
        this.setSize(400, 500); // define this size
        this.setVisible(true); // makes this visible
        this.setTitle(Ftitle); // sets this title
        this.setResizable(false); // prevent this from being resized
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out application
        ImageIcon logo = new ImageIcon("loo.png"); // create an ImageIcon
        this.setIconImage(logo.getImage()); // change icon of this

        this.getContentPane().setBackground(Color.GRAY);// change color of background(default)
        this.getContentPane().setBackground(new Color(100, 10, 23)); // Custom color
        this.getContentPane().setBackground(new Color(0x123456)); // Custom color

    }

}
