public class AppLogger {
    private static final AppLogger instance = new AppLogger();
    private int logCount;

    AppLogger() {
        logCount = 0;
        System.out.println("AppLogger instance created!");
    }

    public void log(String message) {
        logCount++;
        System.out.println("[LOG #" + logCount + "]: " + message);
    }

    public int getLogCount() {
        return logCount;
    }

    public static AppLogger getInstance() {
        return instance;
    }
}
