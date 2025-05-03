package component;

import javax.swing.*;
import java.io.File;
import java.util.function.Consumer;

public class MyMenuBar extends JMenuBar {

    public MyMenuBar(Consumer<File> consumer) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open");
        fileMenu.add(openFile);
        add(fileMenu);

        openFile.addActionListener(e -> {
            ImageChooser imageChooser = new ImageChooser(consumer);
            imageChooser.showOpenDialog(null);
        });
    }
}
