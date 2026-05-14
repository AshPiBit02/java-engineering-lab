import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // GridLayout -> places components in a grid of cells.
        // each component takes all the avilable space within its cell,
        // and each cell is the same size.

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(3, 3, 10, 10));

        frame.add(new JButton("One"));
        frame.add(new JButton("Two"));
        frame.add(new JButton("Three"));
        frame.add(new JButton("Four"));
        frame.add(new JButton("Five"));
        frame.add(new JButton("Six"));
        frame.add(new JButton("Seven"));
        frame.add(new JButton("Eight"));
        frame.add(new JButton("Nine"));
        // frame.add(new JButton("Ten"));
        frame.setVisible(true);
    }

}
