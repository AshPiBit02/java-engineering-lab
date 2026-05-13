import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import javax.swing.ImageIcon;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JLabel label;

    MyFrame() {
        ImageIcon icon2 = new ImageIcon("vid.png");
        label = new JLabel("MyLabel");
        label.setIcon(icon2);
        label.setBounds(200, 30, 150, 250);
        label.setVisible(false);

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
        // button.setEnabled(false); // disable button

        button.setFont(new Font("Comic Sans", Font.ITALIC, 25));

        button.setBounds(175, 200, 150, 150);
        button.addActionListener(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(button);
        this.add(label);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            label.setVisible(true);
        }
    }

}
