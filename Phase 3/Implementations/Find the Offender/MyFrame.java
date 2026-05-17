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

    // Icons for man1
    ImageIcon man11;
    ImageIcon man12;
    ImageIcon man13;

    // Icons for man2
    ImageIcon man21;
    ImageIcon man22;
    ImageIcon man23;

    // Icons for man3
    ImageIcon man31;
    ImageIcon man32;
    ImageIcon man33;

    // Icons for man4
    ImageIcon man41;
    ImageIcon man42;
    ImageIcon man43;

    // Icons for man5
    ImageIcon man51;
    ImageIcon man52;
    ImageIcon man53;

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

        // Setting Icons
        man11 = new ImageIcon("man1/man11.png");
        man1.setIcon(man11);

        man21 = new ImageIcon("man2/man21.png");
        man2.setIcon(man21);

        man31 = new ImageIcon("man3/man31.png");
        man3.setIcon(man31);

        man41 = new ImageIcon("man4/man41.png");
        man4.setIcon(man41);

        man51 = new ImageIcon("man5/man51.png");
        man5.setIcon(man51);

        man1.addMouseListener(this);
        man2.addMouseListener(this);
        man3.addMouseListener(this);
        man4.addMouseListener(this);
        man5.addMouseListener(this);

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
        if (e.getSource() == man1) {
            man13 = new ImageIcon("man1/man13.png");
            man1.setIcon(man13);
        } else if (e.getSource() == man2) {
            man23 = new ImageIcon("man2/man23.png");
            man2.setIcon(man23);
        } else if (e.getSource() == man3) {
            man33 = new ImageIcon("man3/man33.png");
            man3.setIcon(man33);
        } else if (e.getSource() == man4) {
            man43 = new ImageIcon("man4/man43.png");
            man4.setIcon(man43);
        } else {
            man53 = new ImageIcon("man5/man53.png");
            man5.setIcon(man53);

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == man1) {
            man12 = new ImageIcon("man1/man12.png");
            man1.setIcon(man12);
        } else if (e.getSource() == man2) {
            man22 = new ImageIcon("man2/man22.png");
            man2.setIcon(man22);
        } else if (e.getSource() == man3) {
            man32 = new ImageIcon("man3/man32.png");
            man3.setIcon(man32);
        } else if (e.getSource() == man4) {
            man42 = new ImageIcon("man4/man42.png");
            man4.setIcon(man42);
        } else {
            man52 = new ImageIcon("man5/man52.png");
            man5.setIcon(man52);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == man1) {
            man11 = new ImageIcon("man1/man11.png");
            man1.setIcon(man11);
        } else if (e.getSource() == man2) {
            man21 = new ImageIcon("man2/man21.png");
            man2.setIcon(man21);
        } else if (e.getSource() == man3) {
            man31 = new ImageIcon("man3/man31.png");
            man3.setIcon(man31);

        } else if (e.getSource() == man4) {
            man41 = new ImageIcon("man4/man41.png");
            man4.setIcon(man41);
        } else {
            man51 = new ImageIcon("man5/man51.png");
            man5.setIcon(man51);

        }

    }

}
