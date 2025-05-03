package component;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.function.Consumer;

public class ImageChooser extends JFileChooser {
    private static final String[] IMAGE_EXTENSIONS = {"jpg", "jpeg", "png"};

    public ImageChooser(Consumer<File> consumer) {
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setFileFilter(new FileNameExtensionFilter("Images", IMAGE_EXTENSIONS));
        addActionListener(e -> {
            File file = getSelectedFile();
            if (file != null && consumer != null) {
                consumer.accept(file);
            }
        });
    }
}
