import java.awt.*;

public class AWT {
    // Constructor
    public AWT() {
        Frame f = new Frame();
        Button btn = new Button("Hello");

        // Set button position and size
        btn.setBounds(100, 100, 100, 20);

        // Add button to frame
        f.add(btn);

        // Frame properties
        f.setSize(300, 300);
        f.setTitle("AWT Program");
        f.setLayout(null); // Needed for absolute positioning
        f.setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        new AWT();
    }
}

