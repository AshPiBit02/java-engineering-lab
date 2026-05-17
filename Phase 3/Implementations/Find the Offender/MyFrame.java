import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

public class MyFrame extends JFrame implements MouseListener {
    JLabel man1;
    JLabel man2;
    JLabel man3;
    JLabel man4;
    JLabel man5;

    JTextField text;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("Suspect");

        text = new JTextField();
        text.setText("Find The Offender");
        text.setBounds(250, 20, 200, 30);
        text.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        text.setForeground(Color.BLACK);
        text.setEditable(false);
        text.setFocusable(false);
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setBorder(null);

        // Label for man1
        man1 = new JLabel();
        man1.setBounds(50, 100, 100, 100);
        man1.setBackground(Color.RED);
        man1.setOpaque(true);

        // Label for man2
        man2 = new JLabel();
        man2.setBounds(175, 100, 100, 100);
        man2.setBackground(Color.GREEN);
        man2.setOpaque(true);

        // Label for man3
        man3 = new JLabel();
        man3.setBounds(300, 100, 100, 100);
        man3.setBackground(Color.BLUE);
        man3.setOpaque(true);

        // Label for man4
        man4 = new JLabel();
        man4.setBounds(425, 100, 100, 100);
        man4.setBackground(Color.LIGHT_GRAY);
        man4.setOpaque(true);

        // Label for man5
        man5 = new JLabel();
        man5.setBounds(550, 100, 100, 100);
        man5.setBackground(Color.BLACK);
        man5.setOpaque(true);

        this.add(man1);
        this.add(man2);
        this.add(man3);
        this.add(man4);
        this.add(man5);
        this.add(text);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
