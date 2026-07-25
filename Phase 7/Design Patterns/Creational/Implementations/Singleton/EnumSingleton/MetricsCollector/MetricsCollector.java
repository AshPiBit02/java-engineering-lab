public enum MetricsCollector {
    PAGE_VIEWS, LOGIN_ATTEMPTS, ERRORS;

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    
}
