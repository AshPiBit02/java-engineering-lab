import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;

public class MyFrame extends JFrame implements KeyListener {

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.addKeyListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // keyTyped-> Invoked when a key is typed. Uses keyChar, char output

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // keyPressed -> Invoked when a physical key is pressed down. Uses keyCode, int
        // output
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // keyReleased -> called whenever a button is released
        // System.out.println("You released key Char: " + e.getKeyChar());
        System.out.println("You released key Code: " + e.getKeyCode());
    }

}
