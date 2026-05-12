import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("looo.jpg");
        Border border = BorderFactory.createLineBorder(Color.blue, 1);

        // JLabel= a GUI display area for a string of text, an image, or both
        JLabel label = new JLabel(); // create a label
        label.setText("Bro, do you even code?"); // set text of label
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER); // set text left,center or right of imageicon
        label.setVerticalTextPosition(JLabel.TOP); // set text top,cneter or botton of imageicon
        label.setForeground(new Color(0x123456)); // sets label text color
        label.setFont(new Font("MV Boli", Font.PLAIN, 20)); // Custom font
        label.setIconTextGap(100); // set gap between text and image
        label.setBackground(Color.black); // set background color
        label.setOpaque(true); // display backgound color
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER); // set vertical position of icontext within label
        label.setHorizontalAlignment(JLabel.CENTER);
        // label.setBounds(100, 50, 300, 300); //position within frame as well as
        // dimensions

        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        // frame.setLayout(null);
        frame.setVisible(true);
        frame.add(label);
        frame.pack(); // sets auto-size to fit content

    }

}
