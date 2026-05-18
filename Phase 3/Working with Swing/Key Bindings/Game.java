import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class Game {

    JFrame frame;
    JLabel Redlabel;
    JLabel Bluelabel;
    int offset = 5; // smaller step for smoother motion
    Set<Integer> pressedKeys = new HashSet<>();

    Game() {
        frame = new JFrame("Simultaneous Key Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);

        Redlabel = new JLabel();
        Redlabel.setBackground(Color.RED);
        Redlabel.setBounds(100, 100, 100, 100);
        Redlabel.setOpaque(true);

        Bluelabel = new JLabel();
        Bluelabel.setBackground(Color.BLUE);
        Bluelabel.setBounds(300, 100, 100, 100);
        Bluelabel.setOpaque(true);

        frame.add(Redlabel);
        frame.add(Bluelabel);

        // Add key listener to frame
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        // Timer to update positions continuously
        Timer timer = new Timer(30, e -> updatePositions());
        timer.start();

        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    private void updatePositions() {
        // Red label controls (WASD)
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            Redlabel.setLocation(Redlabel.getX(), Redlabel.getY() - offset);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            Redlabel.setLocation(Redlabel.getX(), Redlabel.getY() + offset);
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            Redlabel.setLocation(Redlabel.getX() - offset, Redlabel.getY());
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            Redlabel.setLocation(Redlabel.getX() + offset, Redlabel.getY());
        }

        // Blue label controls (Arrow keys)
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            Bluelabel.setLocation(Bluelabel.getX(), Bluelabel.getY() - offset);
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            Bluelabel.setLocation(Bluelabel.getX(), Bluelabel.getY() + offset);
        }
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            Bluelabel.setLocation(Bluelabel.getX() - offset, Bluelabel.getY());
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            Bluelabel.setLocation(Bluelabel.getX() + offset, Bluelabel.getY());
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
