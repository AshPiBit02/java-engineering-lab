import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import javax.swing.ImageIcon;

public class MyFrame extends JFrame {
    JButton button;

    MyFrame() {
        ImageIcon icon = new ImageIcon("thumb.png");
        button = new JButton("Tap");
        // button.setText("TAP"); set button title
        button.setFocusable(false); // remove the unwanted bound that get visible when
        // click
        button.setIcon(icon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setIconTextGap(-30);
        button.setForeground(Color.cyan);
        button.setBackground(Color.lightGray);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setEnabled(false); // disable button

        button.setFont(new Font("Comic Sans", Font.ITALIC, 25));

        button.setBounds(200, 200, 150, 150);
        button.addActionListener(e -> System.out.println("TAP"));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(button);

    }

}
