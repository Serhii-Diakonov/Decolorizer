package settings;

import serializer.Serializer;

import java.util.Map;

public class SettingsProvider {
    private static final Map<String, String> settings;

    static {
        settings = Serializer.readSettings();
    }

    public static String getSetting(String key) {
        return settings.getOrDefault(key, "");
    }

    public static void setSetting(String key, Object value) {
        settings.put(key, value.toString());
        Serializer.writeSettings(settings);
    }
}
