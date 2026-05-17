import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;

public class MyFrame extends JFrame implements KeyListener {
    JLabel label;
    ImageIcon icon;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.addKeyListener(this);

        icon = new ImageIcon("rock4.jpg");

        label = new JLabel();
        label.setBounds(250, 250, 183, 283);
        label.setIcon(icon);
        // label.setBackground(Color.BLACK);
        // label.setOpaque(true);

        this.add(label);
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int step = 10;
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int labelWidth = label.getWidth();
        int labelHeight = label.getHeight();

        switch (e.getKeyChar()) {
            case 'a': // left
                if (label.getX() - step < 0) {
                    label.setLocation(frameWidth - labelWidth, label.getY());
                } else {
                    label.setLocation(label.getX() - step, label.getY());
                }
                break;
            case 'w': // up
                if (label.getY() - step < 0) {
                    label.setLocation(label.getX(), frameHeight - labelHeight);
                } else {
                    label.setLocation(label.getX(), label.getY() - step);
                }
                break;
            case 'd': // right
                if (label.getX() + step + labelWidth > frameWidth) {
                    label.setLocation(0, label.getY());
                } else {
                    label.setLocation(label.getX() + step, label.getY());
                }
                break;
            case 's': // down
                if (label.getY() + step + labelHeight > frameHeight) {
                    label.setLocation(label.getX(), 0);
                } else {
                    label.setLocation(label.getX(), label.getY() + step);
                }
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int step = 10;
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int labelWidth = label.getWidth();
        int labelHeight = label.getHeight();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (label.getX() - step < 0) {
                    label.setLocation(frameWidth - labelWidth, label.getY());
                } else {
                    label.setLocation(label.getX() - step, label.getY());
                }
                break;
            case KeyEvent.VK_UP:
                if (label.getY() - step < 0) {
                    label.setLocation(label.getX(), frameHeight - labelHeight);
                } else {
                    label.setLocation(label.getX(), label.getY() - step);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (label.getX() + step + labelWidth > frameWidth) {
                    label.setLocation(0, label.getY());
                } else {
                    label.setLocation(label.getX() + step, label.getY());
                }
                break;
            case KeyEvent.VK_DOWN:
                if (label.getY() + step + labelHeight > frameHeight) {
                    label.setLocation(label.getX(), 0);
                } else {
                    label.setLocation(label.getX(), label.getY() + step);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // keyReleased -> called whenever a button is released

        System.out.println("You released key Char: " + e.getKeyChar());
        System.out.println("You released key Code: " + e.getKeyCode());
    }

}
