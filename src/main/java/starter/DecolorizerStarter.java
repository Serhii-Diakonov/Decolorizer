package starter;

import component.ContentPanel;
import component.MyMenuBar;

import javax.swing.*;

public class DecolorizerStarter {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Decolorizer");
        ContentPanel contentPanel = new ContentPanel();

        frame.setJMenuBar(new MyMenuBar(contentPanel::draw));
        frame.add(contentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
