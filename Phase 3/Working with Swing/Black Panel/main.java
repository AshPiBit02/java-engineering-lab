import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class Main {
    private static JLabel createLabel(String Text,Color bgColor,int x,int y){
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setBounds(x,y, 150, 40);
        label.setText(Text);
        label.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        label.setForeground(Color.BLACK);
        return label;

    }
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("dumb.jpg");
        JFrame frame = new JFrame("Dumb!");
        frame.setIconImage(icon.getImage());
        frame.setLayout(null);
        frame.setSize(250, 350);
        frame.getContentPane().setBackground(new Color(255,222,173));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adding Labels
        frame.add(createLabel("It's okay to be Native.", Color.RED, 40, 50));
        frame.add(createLabel("It's okay to be Asian.", Color.YELLOW, 40, 90));
        frame.add(createLabel("It's okay to be Latino.", new Color(139, 69, 19), 40, 130));
        frame.add(createLabel("It's okay to be Black.", Color.BLACK, 40, 170));
        frame.add(createLabel("It's okay to be White.", Color.WHITE, 40, 210));
        frame.setVisible(true);

    }

}
