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
        // keyTyped-> Invoked when a key is typed. Uses keyChar, char output
        switch (e.getKeyChar()) {
            case 'a':
                label.setLocation(label.getX() - 10, label.getY());
                break;
            case 'w':
                label.setLocation(label.getX(), label.getY() - 10);
                break;
            case 'd':
                label.setLocation(label.getX() + 10, label.getY());
                break;
            case 's':
                label.setLocation(label.getX(), label.getY() + 10);
                break;

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // keyPressed -> Invoked when a physical key is pressed down. Uses keyCode, int
        // output

        // Arrow keycodes
        switch (e.getKeyCode()) {
            case 37:
                label.setLocation(label.getX() - 10, label.getY());
                break;
            case 38:
                label.setLocation(label.getX(), label.getY() - 10);
                break;
            case 39:
                label.setLocation(label.getX() + 10, label.getY());
                break;
            case 40:
                label.setLocation(label.getX(), label.getY() + 10);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // keyReleased -> called whenever a button is released

        // System.out.println("You released key Char: " + e.getKeyChar());
        System.out.println("You released key Code: " + e.getKeyCode());
    }

}
