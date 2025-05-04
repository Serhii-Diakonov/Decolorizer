package component;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PreviewPanel extends JPanel {

    private final ImageWrapper imageWrapper;

    public PreviewPanel() {
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createTitledBorder("Image Preview"));

        JLabel emptyState = new JLabel("Drag & drop image here", SwingConstants.CENTER);
        emptyState.setFont(new Font("Monospaced", Font.PLAIN, 24));
        emptyState.setForeground(Color.lightGray);

        imageWrapper = new ImageWrapper();

        add(emptyState);
    }

    public void showImagePreview(File image) {
        removeAll();
        add(imageWrapper);
        imageWrapper.showImagePreview(image);
        revalidate();
    }

    public void activateSelectionMode() {
        imageWrapper.activateSelectionMode();
    }
}
