public class Main {
    public static void main(String[] args) {
        MetricsCollector.PAGE_VIEWS.increment();
        MetricsCollector.PAGE_VIEWS.increment();
        MetricsCollector.PAGE_VIEWS.increment();
        MetricsCollector.LOGIN_ATTEMPTS.increment();

        MetricsCollector.printAllMetrics();


        
        // System.out.println("Page view count: " +
        // MetricsCollector.PAGE_VIEWS.getCount());
        // System.out.println("Login attempt count: " +
        // MetricsCollector.LOGIN_ATTEMPTS.getCount());
        // System.out.println("Error count: " + MetricsCollector.ERRORS.getCount());

        System.out.println(MetricsCollector.PAGE_VIEWS == MetricsCollector.PAGE_VIEWS);
    }
}
