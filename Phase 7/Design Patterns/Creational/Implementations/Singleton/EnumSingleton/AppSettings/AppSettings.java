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
        System.out.format("[SET] %s : %s successful. %n", key, value);
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
        setting.forEach((key, value) -> System.out.format("%s : %s ", key, value));

    }

}
