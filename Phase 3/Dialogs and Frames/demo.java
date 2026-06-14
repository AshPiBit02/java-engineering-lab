import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class demo {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JDialog dialog = new JDialog(frame, "My Dialog", true);
        dialog.setSize(200, 150);
        JLabel label = new JLabel("This is a dialog");
        dialog.add(label);
        frame.setVisible(true);
        dialog.setVisible(true);
    }

}
