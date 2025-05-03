package serializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serializer {

    public static void writeObject(Object obj) {
        Serializer.class.getClassLoader();
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("settings.ser"))) {
//            fos.write();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Object readObject(Class<?> clazz) {
        return null;
    }
}
