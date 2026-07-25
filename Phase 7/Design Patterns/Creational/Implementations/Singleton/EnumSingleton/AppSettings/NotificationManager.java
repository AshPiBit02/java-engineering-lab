public class NotificationManager {
    private SettingsStore settings = AppSettings.INSTANCE;

    void checkNotificationPref() {
        String perf = settings.getSetting("notifications");
        System.out.println("Notification settting: " + pref);
    }
}
