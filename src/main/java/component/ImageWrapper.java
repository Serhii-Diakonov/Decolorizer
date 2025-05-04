package component;

import settings.Keys;
import settings.SettingsProvider;
import timer.MarchingAntsTimer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

public class ImageWrapper extends JPanel {
    private static final String ESCAPE = "escape";
    private static final Color GRAY = new Color(0, 0, 0, 150);

    private int imageX;
    private int imageY;
    private int imageWidth;
    private int imageHeight;

    private Point startPoint;
    private Point endPoint;
    private BufferedImage image;
    private boolean isSelectionModeActive;
    private float dashPhase;

    public ImageWrapper() {
        setBackground(Color.lightGray);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isSelectionModeActive) {
                    endPoint = e.getPoint();
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isSelectionModeActive) {
                    startPoint = e.getPoint();
                    endPoint = startPoint;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isSelectionModeActive) {
                    endPoint = e.getPoint();
                }
            }
        });

        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESCAPE);
        actionMap.put(ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivateSelectionMode();
            }
        });

        new MarchingAntsTimer(() -> {
            dashPhase += 2;
            repaint();
        });
    }

    public void showImagePreview(File imageFile) {
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void activateSelectionMode() {
        if (image != null) {
            Cursor cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
            setCursor(cursor);
            isSelectionModeActive = true;
            requestFocusInWindow();
        }
    }

    public void deactivateSelectionMode() {
        isSelectionModeActive = false;
        startPoint = null;
        endPoint = null;
        setCursor(Cursor.getDefaultCursor());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean isSelectedAreaColored = Boolean.parseBoolean(SettingsProvider.getSetting(Keys.IS_AREA_COLORED));
        Graphics2D g2d = (Graphics2D) g.create();

        if (image != null) {
            drawImage(g2d, !isSelectionModeActive
                    || !isSelectedAreaColored
                    || startPoint == null
                    || startPoint.equals(endPoint));
        }

        if (isSelectionModeActive && startPoint != null && endPoint != null) {
            g2d.setColor(GRAY);
            g2d.fillRect(imageX, imageY, imageWidth, imageHeight);
            Rectangle selection = getSelection();

            g2d.setClip(selection);
            drawImage(g2d, isSelectedAreaColored);
            g2d.setClip(null);
            drawSelection(g2d, selection);

            g2d.dispose();
        }
    }

    private Rectangle getSelection() {
        return new Rectangle(
                Math.min(startPoint.x, endPoint.x),
                Math.min(startPoint.y, endPoint.y),
                Math.abs(startPoint.x - endPoint.x),
                Math.abs(startPoint.y - endPoint.y)
        );
    }

    private void drawImage(Graphics2D g, boolean isColored) {
        BufferedImage processedImage = getProcessedImage(isColored);
        Image scaledImage = getScaledImage(processedImage);
        imageWidth = scaledImage.getWidth(null);
        imageHeight = scaledImage.getHeight(null);
        imageX = getWidth() / 2 - scaledImage.getWidth(null) / 2;
        imageY = getHeight() / 2 - scaledImage.getHeight(null) / 2;
        g.drawImage(scaledImage, imageX, imageY, this);
    }

    private void drawSelection(Graphics2D g2d, Rectangle selection) {
        float[] dashPattern = {6f, 6f};
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dashPattern, dashPhase));
        g2d.draw(selection);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dashPattern, dashPhase + dashPattern[0]));
        g2d.draw(selection);
    }

    private BufferedImage getProcessedImage(boolean isColored) {
        if (isColored) {
            return image;
        }
        BufferedImage processedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ColorConvertOp greyColorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        greyColorConvert.filter(image, processedImage);
        return processedImage;
    }

    private Image getScaledImage(Image image) {
        int width = getWidth();
        int height = getHeight();
        float imageResolution = (float) image.getWidth(null) / image.getHeight(null);
        return width > height
                ? image.getScaledInstance((int) (imageResolution * height), height, Image.SCALE_SMOOTH)
                : image.getScaledInstance(width, (int) (width / imageResolution), Image.SCALE_SMOOTH);
    }
}
