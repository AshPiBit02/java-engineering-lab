import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class MyFrame extends JFrame implements ActionListener {
    JRadioButton pizzaButton;
    JRadioButton hamburgerButton;
    JRadioButton hotdogButton;
    ImageIcon pizzaIcon;
    ImageIcon hamburberIcon;
    ImageIcon hotdogIcon;

    ButtonGroup group; // will allow to select only one element in a group

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        pizzaIcon = new ImageIcon("pizza-slice.png");
        hamburberIcon = new ImageIcon("hamburger.png");
        hotdogIcon = new ImageIcon("hotdog.png");

        pizzaButton = new JRadioButton("Pizza");
        pizzaButton.setFocusable(false);
        hamburgerButton = new JRadioButton("Hamburger");
        hamburgerButton.setFocusable(false);
        hotdogButton = new JRadioButton("Hotdog");
        hotdogButton.setFocusable(false);

        group = new ButtonGroup();
        group.add(pizzaButton);
        group.add(hamburgerButton);
        group.add(hotdogButton);

        pizzaButton.addActionListener(this);
        hamburgerButton.addActionListener(this);
        hotdogButton.addActionListener(this);

        pizzaButton.setIcon(pizzaIcon);
        hamburgerButton.setIcon(hamburberIcon);
        hotdogButton.setIcon(hotdogIcon);

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
