
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class layoutsDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Layouts Demo");
        frame.setLayout(new FlowLayout());

        JPanel defaultLayout = new JPanel();
        defaultLayout.setBackground(Color.BLUE);
        defaultLayout.add(new JLabel("Label1"), BorderLayout.EAST);
        defaultLayout.add(new JLabel("Label2"), BorderLayout.WEST);
        defaultLayout.add(new JLabel("Label3"), BorderLayout.NORTH);
        defaultLayout.add(new JLabel("Label4"), BorderLayout.SOUTH);
        defaultLayout.add(new JLabel("Label4"), BorderLayout.CENTER);

        JPanel gridLayout = new JPanel();
        gridLayout.setBackground(Color.RED);
        gridLayout.setLayout(new GridLayout(2, 2, 20, 20));
        gridLayout.add(new JLabel("LabelA"));
        gridLayout.add(new JLabel("LabelB"));
        gridLayout.add(new JLabel("LabelC"));
        gridLayout.add(new JLabel("LabelD"));

        JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.Y_AXIS));
        boxLayout.setBackground(Color.green);
        boxLayout.add(new JLabel("LabelX"));
        boxLayout.add(new JLabel("LabelY"));
        boxLayout.add(new JLabel("LabelZ"));

        frame.add(defaultLayout);
        frame.add(gridLayout);
        frame.add(boxLayout);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}
