import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class MyFrame extends JFrame implements ActionListener {
    JRadioButton pizzaButton;
    JRadioButton hamburgerButton;
    JRadioButton hotdogButton;

    ButtonGroup group; // will allow to select only one element in a group

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        pizzaButton = new JRadioButton("Pizza");
        hamburgerButton = new JRadioButton("Hamburger");
        hotdogButton = new JRadioButton("Hotdog");

        group = new ButtonGroup();
        group.add(pizzaButton);
        group.add(hamburgerButton);
        group.add(hotdogButton);

        pizzaButton.addActionListener(this);
        hamburgerButton.addActionListener(this);
        hotdogButton.addActionListener(this);

        this.add(pizzaButton);
        this.add(hamburgerButton);
        this.add(hotdogButton);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pizzaButton) {
            System.out.println("Pizza Ordered!");
        } else if (e.getSource() == hamburgerButton) {
            System.out.println("Hamburger Ordered!");
        } else {
            System.out.println("Hotdog Ordered!");
        }

    }

}
