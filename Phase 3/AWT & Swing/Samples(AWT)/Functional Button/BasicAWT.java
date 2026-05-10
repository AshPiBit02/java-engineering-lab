import java.awt.*;
import java.awt.event.*;

public class BasicAWT {
    public static void main(String[] args) {

        // Create a frame (window)
        Frame f = new Frame("My First AWT Demo");

        // Create a button
        Button b = new Button("Click Me");
        Button b1 = new Button("Go to");

        // Set button position and size
        b.setBounds(100, 100, 80, 30);
        b1.setBounds(50, 50, 80, 40);
        // Add button to frame
        f.add(b);
        f.add(b1);

        // Set frame size,layout, and visibility
        f.setSize(300, 200);
        f.setLayout(null); // no layout manager, absolute positioning
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });
    }
}