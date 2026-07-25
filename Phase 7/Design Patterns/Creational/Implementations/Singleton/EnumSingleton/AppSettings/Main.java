public class Main {
    public static void main(String[] args) {
        ThemeManager tm = new ThemeManager();
        tm.applyTheme("dark");

        NotificationManager nm = new NotificationManager();
        nm.checkNotificationPref();

        AppSettings.INSTANCE.setSetting("notifications", "enabled");
        nm.checkNotificationPref();

        AppSettings.INSTANCE.printAllSettings();
    }

}
