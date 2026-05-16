import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class SliderDemo implements ChangeListener {

    JFrame frame;
    JPanel panel;
    JLabel label;
    JSlider slider;

    SliderDemo() {
        frame = new JFrame("Slider Demo");
        panel = new JPanel();
        label = new JLabel();
        slider = new JSlider(0, 100, 50); // Min, Maximum value,Starting point(default)

        slider.setPreferredSize(new Dimension(400, 200));

        slider.setPaintTicks(true); // paints the covered area
        slider.setMinorTickSpacing(10);

        slider.setPaintTrack(true); // paints the covered area
        slider.setMajorTickSpacing(20);

        slider.setPaintLabels(true); // Adds values to major Tick
        slider.setFont(new Font("MV Boli", Font.PLAIN, 14));

        label.setFont(new Font("MV Boli", Font.PLAIN, 20));

        slider.setOrientation(SwingConstants.VERTICAL); // set the content in vertical form
        // slider.setOrientation(SwingConstants.HORIZONTAL); // set the content in
        // vertical form(Default)

        slider.addChangeListener(this);

        panel.add(slider);
        panel.add(label);
        frame.add(panel);
        frame.setSize(420, 420);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        label.setText("°C = " + slider.getValue());
        System.out.println("Temperatur: " + slider.getValue() + "°C");

    }

}
