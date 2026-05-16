import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;

public class MyFrame extends JFrame implements ActionListener {

    JButton button;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        button = new JButton("Select File");
        button.addActionListener(this);
        button.setFocusable(false);

        this.add(button);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {

            JFileChooser filechooser = new JFileChooser();

            filechooser.setCurrentDirectory(new File(".")); // Current Directory
            filechooser.setCurrentDirectory(new File("C:\\Users\\aashi\\OneDrive\\Desktop")); // Custom Directory

            // int response = filechooser.showOpenDialog(null); // select file to open
            int response = filechooser.showSaveDialog(null); // select file to save
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = filechooser.getSelectedFile();
                System.out.println(file.getAbsolutePath());

            }
        }

    }

}
