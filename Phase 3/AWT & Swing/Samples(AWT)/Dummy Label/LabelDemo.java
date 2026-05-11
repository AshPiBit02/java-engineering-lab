import java.awt.*;

public class LabelDemo extends Frame {
    LabelDemo() {
        setLayout(null);

        Label l1 = new Label("Username:");
        l1.setBounds(30, 50, 100, 30);

        Label l2 = new Label("Status: Active", Label.CENTER);
        l2.setBounds(30, 100, 150, 30);
        l2.setForeground(Color.BLUE);
        l2.setFont(new Font("Arial", Font.BOLD, 14));

        add(l1);
        add(l2);

        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LabelDemo();
    }
}