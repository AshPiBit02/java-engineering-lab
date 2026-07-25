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
        System.out.println("-".repeat(10) + " All Metrics " + "-".repeat(10));
        for (MetricsCollector metric : MetricsCollector.values()) {
            System.out.format("%-15s : %d %n", metric.name(), metric.getCount());
        }
    }

    public static void getOrdinals() {
        System.out.println("-".repeat(10) + " ORDINAL " + "-".repeat(10));
        for (MetricsCollector metric : MetricsCollector.values()) {
            System.out.format("%-15s : %d %n", metric.name(), metric.ordinal());
        }
    }

}
