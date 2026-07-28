import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridbaglayoutDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GRIDBAGLAYOUT DEMO");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JButton("Button 1"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JButton("Button 2"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        frame.add(new JButton("Button 3"), gbc);

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
