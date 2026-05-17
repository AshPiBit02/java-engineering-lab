import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JLabel label;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        button = new JButton("Pick a Color");
        button.addActionListener(this);
        button.setFocusable(false);

        label = new JLabel();
        label.setBackground(Color.white);
        label.setText("Here is some text:D");
        label.setFont(new Font("MV Boli", Font.PLAIN, 51));
        label.setOpaque(true);

        this.add(button);
        this.add(label);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = JColorChooser.showDialog(null, "Pick a color....", Color.black);
            // label.setForeground(color);
            label.setBackground(color);
        }

    }

}
