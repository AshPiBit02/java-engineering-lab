import java.awt.event.*;
import java.util.jar.JarEntry;
import java.awt.Color;
import javax.swing.*;

public class MyFrame extends JFrame implements KeyListener {
    JLabel label;
    JLabel label2;
    ImageIcon rock1;
    ImageIcon rock2;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLayout(null);
        this.addKeyListener(this);

        rock1 = new ImageIcon("rock4.jpg");
        rock2 = new ImageIcon("rock5.jpg");

        label = new JLabel();
        label.setBounds(400, 400, 183, 283);
        label.setIcon(rock1);

        label2 = new JLabel();
        label2.setBounds(100, 400, 221, 293);
        label2.setIcon(rock2);
        // label.setBackground(Color.BLACK);
        // label.setOpaque(true);

        this.add(label);
        this.add(label2);
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int step = 10;
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        int labelWidth = label2.getWidth();
        int labelHeight = label2.getHeight();

        switch (e.getKeyChar()) {
            case 'a': // left
                if (label2.getX() - step < 0) {
                    label2.setLocation(frameWidth - labelWidth, label2.getY());
                } else {
                    label2.setLocation(label2.getX() - step, label2.getY());
                }
                break;
            case 'w': // up
                if (label2.getY() - step < 0) {
                    label2.setLocation(label.getX(), frameHeight - labelHeight);
                } else {
                    label2.setLocation(label2.getX(), label2.getY() - step);
                }
                break;
            case 'd': // right
                if (label2.getX() + step + labelWidth > frameWidth) {
                    label2.setLocation(0, label2.getY());
                } else {
                    label2.setLocation(label2.getX() + step, label2.getY());
                }
                break;
            case 's': // down
                if (label2.getY() + step + labelHeight > frameHeight) {
                    label2.setLocation(label2.getX(), 0);
                } else {
                    label2.setLocation(label2.getX(), label2.getY() + step);
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
