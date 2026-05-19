import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import java.awt.*;

public class MyPanel extends JPanel {

    MyPanel() {
        this.setPreferredSize(new Dimension(500, 500));

    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        // Line
        g2D.setStroke(new BasicStroke(5)); // makes line thicker
        // g2D.setPaint(Color.blue);
        // g2D.drawLine(0, 0, 500, 500);

        // Rectangle
        // g2D.setStroke(new BasicStroke(6));
        // g2D.setPaint(Color.green);
        // g2D.drawRect(10, 10, 250, 300);
        // g2D.fillRect(10, 10, 250, 300);

        // Circle
        // g2D.setStroke(new BasicStroke(5)); // makes line thicker
        // g2D.setPaint(Color.BLACK);
        // g2D.drawOval(0, 0, 100, 100);

        g2D.drawArc(200, 200, 100, 100, 0, 180);

    }

}
