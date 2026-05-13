import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class main {
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("dumb.jpg");

        JLabel redLabel = new JLabel();
        redLabel.setOpaque(true);
        redLabel.setBackground(Color.RED);
        redLabel.setBounds(40, 50, 150, 40);
        redLabel.setText("It's okay to be Native.");
        redLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));

        redLabel.setForeground(Color.BLACK);

        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(Color.YELLOW);
        yellowLabel.setBounds(40, 90, 150, 40);
        yellowLabel.setText("It's okay to be Asian.");
        yellowLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        yellowLabel.setForeground(Color.BLACK);

        JLabel brownLabel = new JLabel();
        brownLabel.setOpaque(true);
        brownLabel.setBackground(new Color(139, 69, 19));
        brownLabel.setBounds(40, 130, 150, 40);
        brownLabel.setText("It's okay to be Latino.");
        brownLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        brownLabel.setForeground(Color.BLACK);

        JLabel blackLabel = new JLabel();
        blackLabel.setOpaque(true);
        blackLabel.setBackground(Color.BLACK);
        blackLabel.setBounds(40, 170, 150, 40);
        blackLabel.setText("It's okay to be Black.");
        blackLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        blackLabel.setForeground(Color.BLACK);

        JLabel whiteLabel = new JLabel();
        whiteLabel.setOpaque(true);
        whiteLabel.setBackground(Color.WHITE);
        whiteLabel.setBounds(40, 210, 150, 40);
        whiteLabel.setText("It's okay to be White.");
        whiteLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        whiteLabel.setForeground(Color.BLACK);

        JFrame frame = new JFrame("Dumb!");
        frame.setIconImage(icon.getImage());
        frame.setLayout(null);
        frame.setSize(250, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(redLabel);
        frame.add(yellowLabel);
        frame.add(brownLabel);
        frame.add(blackLabel);
        frame.add(whiteLabel);
        frame.setVisible(true);

    }

}
