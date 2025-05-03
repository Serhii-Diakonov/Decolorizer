package component;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ContentPanel extends JPanel {

    private final ImageWrapper imageWrapper;
    private boolean isInverted = false;

    public ContentPanel() {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        JButton save = new JButton("Save File");
        JButton selectArea = new JButton("Select Area");

        JButton invertMask = new MaskingModeSwitcher();
        headerPanel.add(invertMask);

        bottomPanel.add(save);
        bottomPanel.add(selectArea);

        imageWrapper = new ImageWrapper();
        add(imageWrapper, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void draw(File image) {
        imageWrapper.draw(image);
    }
}
