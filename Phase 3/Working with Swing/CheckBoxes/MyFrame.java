import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JCheckBox checkbox;
    ImageIcon cross;
    ImageIcon check;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        cross = new ImageIcon("cross.png");
        check = new ImageIcon("check.png");

        button = new JButton();
        button.setText("Submit");
        button.addActionListener(this);

        checkbox = new JCheckBox();
        checkbox.setText("I'm not a robot.");
        checkbox.setFocusable(false);
        checkbox.setFont(new Font("Consolas", Font.PLAIN, 14));
        checkbox.setIcon(cross);
        checkbox.setSelectedIcon(check);

        this.add(button);
        this.add(checkbox);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            System.out.println(checkbox.isSelected());
        }
    }

}
