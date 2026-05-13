import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import javax.swing.ImageIcon;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JLabel label, label2;
    ImageIcon lvl1, lvl2, lvl3, lvl4, lvl5;

    MyFrame() {
        lvl1 = new ImageIcon("lvl1.png");
        lvl2 = new ImageIcon("lvl2.png");
        lvl3 = new ImageIcon("lvl3.png");
        lvl4 = new ImageIcon("lvl4.png");
        lvl5 = new ImageIcon("lvl5.png");
        label = new JLabel("MyLabel");
        label.setBounds(0, 0, 200, 200);
        label.setVisible(false);

        label2 = new JLabel();
        label2.setText("Knocked!!!!!!!");
        label2.setBackground(Color.LIGHT_GRAY);
        label2.setBounds(0, 0, 150, 50);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setVerticalTextPosition(JLabel.TOP);
        label2.setVisible(false);
        label2.setLayout(null);

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
        this.add(label2);

    }

    int tap = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if (tap == 0) {
                label.setIcon(lvl1);
            } else if (tap == 1) {
                label.setIcon(lvl2);
            } else if (tap == 2) {
                label.setIcon(lvl3);
            } else if (tap == 3) {
                label.setIcon(lvl4);
            } else if (tap == 4) {
                label.setIcon(lvl5);
            } else {
                label.setVisible(false);
                button.setEnabled(false);
                label2.setVisible(true);
                return;
            }
            label.setVisible(true);
            tap++;
        }

    }

}
