import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class Game {

    JFrame frame;
    JLabel Redlabel;
    JLabel Bluelabel;
    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;

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
        Bluelabel.setBounds(100, 100, 100, 100);
        Bluelabel.setOpaque(true);

        upAction = new UpAction();
        downAction = new DownAction();
        leftAction = new LeftAction();
        rightAction = new RightAction();

        // Actions For Red Label
        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('w'), "upAction");
        Redlabel.getActionMap().put("upAction", upAction);

        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('s'), "downAction");
        Redlabel.getActionMap().put("downAction", downAction);

        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('a'), "leftAction");
        Redlabel.getActionMap().put("leftAction", leftAction);

        Redlabel.getInputMap().put(KeyStroke.getKeyStroke('d'), "rightAction");
        Redlabel.getActionMap().put("rightAction", rightAction);

        // Actions for Blue Label
        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
        Bluelabel.getActionMap().put("upAction", upAction);

        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        Bluelabel.getActionMap().put("downAction", downAction);

        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        Bluelabel.getActionMap().put("leftAction", leftAction);

        Bluelabel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        Bluelabel.getActionMap().put("rightAction", rightAction);

        frame.add(Redlabel);
        frame.add(Bluelabel);
        frame.setVisible(true);

    }

    int offset = 10;

    // For Red Label
    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setLocation(label.getX(), label.getY() - offset);

        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setLocation(label.getX(), label.getY() + offset);

        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setLocation(label.getX() - offset, label.getY());

        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setLocation(label.getX() + offset, label.getY());

        }
    }

}
