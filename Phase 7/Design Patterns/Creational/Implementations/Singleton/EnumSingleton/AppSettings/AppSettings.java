import java.util.HashMap;
import java.util.Map;

public enum AppSettings implements SettingsStore {
    INSTANCE;

    private Map<String, String> setting;

    AppSettings() {
        setting = new HashMap<>();
        System.out.println("Settings initiated.");
    }

    @Override
    public void setSetting(String key, String value) {
        setting.put(key, value);
        System.out.format("[SET] %s : %s %n", key, value);
    }

    @Override
    public String getSetting(String key) {
        String value = setting.get(key);
        if (value != null) {
            return value;
        }
        return "NOT_SET";
    }

    public void printAllSettings() {
        if (setting.isEmpty()) {
            System.out.println("No settings set yet.");
            return;
        }
        System.out.println();
        System.out.println("Setting");
        System.out.println("-".repeat(40));
        setting.forEach((key, value) -> System.out.format("%-15s : %s %n", key, value));

    }

}
