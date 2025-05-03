package starter;

import component.ContentPanel;
import component.MyMenuBar;
import settings.Keys;
import settings.SettingsProvider;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DecolorizerStarter {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Decolorizer");
        ContentPanel contentPanel = new ContentPanel();

        frame.setJMenuBar(new MyMenuBar(contentPanel::draw));
        frame.add(contentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String widthValue = SettingsProvider.getSetting(Keys.WIDTH);
        String heightValue = SettingsProvider.getSetting(Keys.HEIGHT);
        frame.setSize(
                widthValue.isBlank() ? DEFAULT_WIDTH : Integer.parseInt(widthValue),
                heightValue.isBlank() ? DEFAULT_HEIGHT : Integer.parseInt(heightValue)
        );

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SettingsProvider.setSetting(Keys.WIDTH, frame.getWidth());
                SettingsProvider.setSetting(Keys.HEIGHT, frame.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                SettingsProvider.setSetting(Keys.FRAME_X, frame.getX());
                SettingsProvider.setSetting(Keys.FRAME_Y, frame.getY());
            }
        });

        String xValue = SettingsProvider.getSetting(Keys.FRAME_X);
        String yValue = SettingsProvider.getSetting(Keys.FRAME_Y);

        if (xValue.isBlank() || yValue.isBlank()) {
            frame.setLocationRelativeTo(null);
        } else {
            frame.setLocation(Integer.parseInt(xValue), Integer.parseInt(yValue));
        }

        frame.setVisible(true);
    }
}
