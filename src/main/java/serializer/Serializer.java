package serializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Serializer {

    public static void writeSettings(Map<String, String> settings) {
        Serializer.class.getClassLoader();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("settings.ser"))) {
            oos.writeObject(settings);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, String> readSettings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("settings.ser"))) {
            return (Map<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new HashMap<>();
    }
}
