public class ThemeManager {
    private SettingsStore settings = AppSettings.INSTANCE;

    void applyTheme(String theme) {
        settings.setSetting("theme", theme);
    }
}
