import java.awt.*;
import javax.swing.*;

public class ProgressBarDemo {
    JFrame frame = new JFrame();
    JProgressBar bar = new JProgressBar();

    ProgressBarDemo() {
        // bar.setValue(0); // sets initial value
        bar.setBounds(50, 50, 300, 50);
        bar.setStringPainted(true);

        frame.add(bar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);

        fill();

    }

    public void fill() {
        System.out.println("Task in Progress.....");
        int counter = 0;
        while (counter <= 100) {
            bar.setValue(counter);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (counter >= 40 && counter < 50) {
                counter += 2;

            } else if (counter >= 90) {
                if (counter >= 96) {
                    counter++;
                } else {

                    counter += 3;
                }
            } else {

                counter += 10;
            }
        }
        System.out.println("Task Complete!");
    }

}
