package component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.File;

public class ImageWrapper extends JPanel {

    private Image image;
    private JLabel displayedImage;

    public ImageWrapper() {
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createTitledBorder("Image Preview"));

        JLabel emptyState = new JLabel("Drag & drop image here", SwingConstants.CENTER);
        emptyState.setFont(new Font("Monospaced", Font.PLAIN, 24));
        emptyState.setForeground(Color.lightGray);
        add(emptyState);

        addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                updateContent();
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                updateContent();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (displayedImage != null) {
                    remove(displayedImage);
                    displayedImage = new JLabel(getScaledImageIcon(image));
                    add(displayedImage);
                }
                updateContent();
            }
        });
    }

    public void draw(File imageFile) {
        removeAll();
        ImageIcon img = new ImageIcon(imageFile.getAbsolutePath());
        image = img.getImage();
        displayedImage = new JLabel(getScaledImageIcon(image));
        add(displayedImage);
    }

    private void updateContent() {
        revalidate();
        repaint();
    }

    private ImageIcon getScaledImageIcon(Image image) {
        int width = getWidth();
        int height = getHeight();
        float imageResolution = (float) image.getWidth(null) / image.getHeight(null);
        return new ImageIcon(width > height
                ? image.getScaledInstance((int) (imageResolution * height), height, Image.SCALE_SMOOTH)
                : image.getScaledInstance(width, (int) (width / imageResolution), Image.SCALE_SMOOTH));
    }
}
