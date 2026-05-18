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

        // Actions For Red Label (WASD)
        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('w'), "redUp");
        Redlabel.getActionMap().put("redUp", new MoveAction(Redlabel, 0, -offset));

        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('s'), "redDown");
        Redlabel.getActionMap().put("redDown", new MoveAction(Redlabel, 0, offset));

        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('a'), "redLeft");
        Redlabel.getActionMap().put("redLeft", new MoveAction(Redlabel, -offset, 0));

        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('d'), "redRight");
        Redlabel.getActionMap().put("redRight", new MoveAction(Redlabel, offset, 0));

        // Actions For Blue Label (Arrow keys)
        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "blueUp");
        Bluelabel.getActionMap().put("blueUp", new MoveAction(Bluelabel, 0, -offset));

        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "blueDown");
        Bluelabel.getActionMap().put("blueDown", new MoveAction(Bluelabel, 0, offset));

        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "blueLeft");
        Bluelabel.getActionMap().put("blueLeft", new MoveAction(Bluelabel, -offset, 0));

        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "blueRight");
        Bluelabel.getActionMap().put("blueRight", new MoveAction(Bluelabel, offset, 0));

        frame.add(Redlabel);
        frame.add(Bluelabel);
        frame.setVisible(true);
    }

    // Generic action class
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
}
