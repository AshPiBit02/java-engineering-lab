public enum MetricsCollector {
    PAGE_VIEWS, LOGIN_ATTEMPTS, ERRORS;

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public static void printAllMetrics() {
        System.out.println("-".repeat(20) + " All Metrics " + "-".repeat(20));
        for (MetricsCollector metric : MetricsCollector.values()) {
            System.out.println(metric.name() + ": " + metric.getCount());
        }
    }

}
