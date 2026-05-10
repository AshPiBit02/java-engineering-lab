import java.awt.*;
import java.awt.event.*;

public class FunctionalButton {
    public static void main(String[] args) {
        Frame f = new Frame("Functional Button Demostration");
        Button b = new Button("Hit me");
        b.setBounds(100, 100, 100, 40);
        f.add(b);

        f.setSize(300, 200);
        f.setLayout(null);
        f.setVisible(true);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Breaked!");
            }
        });

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });

    }

}
