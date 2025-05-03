package settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsProvider {
    private static Map<String, String> settings = new HashMap<>();

    public static String getSetting(String key) {
        return settings.getOrDefault(key, "");
    }

    public static void setSetting(String key, Object value) {
        settings.put(key, value.toString());
    }
}
