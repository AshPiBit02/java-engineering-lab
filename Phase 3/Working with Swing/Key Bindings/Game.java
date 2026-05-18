import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class Game {

    JFrame frame;
    JLabel Redlabel;
    JLabel Bluelabel;
    int offset = 10;

    Game() {
        frame = new JFrame("KeyBinding Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);

        Redlabel = new JLabel();
        Redlabel.setBackground(Color.RED);
        Redlabel.setBounds(100, 100, 100, 100);
        Redlabel.setOpaque(true);

        Bluelabel = new JLabel();
        Bluelabel.setBackground(Color.BLUE);
        Bluelabel.setBounds(200, 100, 100, 100);
        Bluelabel.setOpaque(true);

        // Red label controls (WASD)
        InputMap redMap = Redlabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap redActions = Redlabel.getActionMap();

        redMap.put(KeyStroke.getKeyStroke('w'), "redUp");
        redActions.put("redUp", new MoveAction(Redlabel, 0, -offset));

        redMap.put(KeyStroke.getKeyStroke('s'), "redDown");
        redActions.put("redDown", new MoveAction(Redlabel, 0, offset));

        redMap.put(KeyStroke.getKeyStroke('a'), "redLeft");
        redActions.put("redLeft", new MoveAction(Redlabel, -offset, 0));

        redMap.put(KeyStroke.getKeyStroke('d'), "redRight");
        redActions.put("redRight", new MoveAction(Redlabel, offset, 0));

        // Blue label controls (Arrow keys)
        InputMap blueMap = Bluelabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap blueActions = Bluelabel.getActionMap();

        blueMap.put(KeyStroke.getKeyStroke("UP"), "blueUp");
        blueActions.put("blueUp", new MoveAction(Bluelabel, 0, -offset));

        blueMap.put(KeyStroke.getKeyStroke("DOWN"), "blueDown");
        blueActions.put("blueDown", new MoveAction(Bluelabel, 0, offset));

        blueMap.put(KeyStroke.getKeyStroke("LEFT"), "blueLeft");
        blueActions.put("blueLeft", new MoveAction(Bluelabel, -offset, 0));

        blueMap.put(KeyStroke.getKeyStroke("RIGHT"), "blueRight");
        blueActions.put("blueRight", new MoveAction(Bluelabel, offset, 0));

        frame.add(Redlabel);
        frame.add(Bluelabel);
        frame.setVisible(true);
    }

    // Generic reusable action
    public class MoveAction extends AbstractAction {
        JLabel label;
        int dx, dy;

        MoveAction(JLabel label, int dx, int dy) {
            this.label = label;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            label.setLocation(label.getX() + dx, label.getY() + dy);
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
