import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import java.awt.*;

public class MyPanel extends JPanel {
    Image image;

    MyPanel() {
        image = new ImageIcon("man53.png").getImage();
        this.setPreferredSize(new Dimension(100, 100));

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

        // g2D.drawArc(200, 200, 100, 100, 0, 180);

        // g2D.setPaint(Color.red);
        // g2D.fillArc(200, 200, 100, 100, 0, 180);

        // g2D.setPaint(Color.white);
        // g2D.fillArc(200, 200, 100, 100, 180, 180);

        g2D.drawImage(image, 0, 0, null);

        // Polygons
        int[] xPoints = { 0, 100, 50 };
        int[] yPoints = { 100, 100, 0 };
        g2D.drawPolygon(xPoints, yPoints, 3);

        // g2D.setPaint(Color.BLUE);
        // g2D.setFont(new Font("Ink Free", Font.ITALIC, 35));
        // g2D.drawString("You're a Dumb!", 50, 50);

    }

}
