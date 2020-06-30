package processing.games;

import javax.swing.*;
import java.awt.*;


public class Robot extends JPanel {

    public static void main(String[] args) throws AWTException {
        JFrame frame = new JFrame("Title");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Robot robot = new Robot();
        frame.add(robot);

        frame.setVisible(true);

        Robot robot1 = new Robot();
        frame.add(robot1, BorderLayout.SOUTH);

        frame.setSize(500, 500);

        int x = frame.getX();
        int y = frame.getY();

        java.awt.Robot r = new java.awt.Robot();
        Color c = r.getPixelColor(x + 30, y + 30); // this is not resolving...

        System.out.println(c);

    }
}
