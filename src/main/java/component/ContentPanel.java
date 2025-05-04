package component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class ContentPanel extends JPanel {
    private final PreviewPanel previewPanel;

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

        previewPanel = new PreviewPanel();
        add(previewPanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        selectArea.addActionListener(e -> {
            previewPanel.activateSelectionMode();
        });
    }

    public void draw(File image) {
        previewPanel.showImagePreview(image);
    }
}
