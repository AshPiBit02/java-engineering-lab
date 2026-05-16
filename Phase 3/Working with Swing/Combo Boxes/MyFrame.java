import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    JComboBox comboBox;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        // String[] animals = { "Dog", "Cat", "Bird" };

        Integer[] nums = { 1, 2, 3, 4, 5, 6, 7 }; // Use reference version on dtype
        comboBox = new JComboBox(nums);
        comboBox.addActionListener(this);

        // comboBox.setEditable(true);

        // System.out.println(comboBox.getItemCount()); // diplay the no of items
        // comboBox.addItem("Horse"); // adds new item at last

        // comboBox.insertItemAt("Goat", 0);// add new item at index
        // comboBox.setSelectedIndex(0); // make default select using index

        // comboBox.removeItem("Dog"); // removes item
        // comboBox.removeItemAt(0); // removes item at index

        // comboBox.removeAll();

        this.add(comboBox);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            System.out.println(comboBox.getSelectedItem());
            // System.out.println(comboBox.getSelectedIndex());
        }

    }

}
