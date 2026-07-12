import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.List;

class LogProcessor {
    public void processLog(int logId) {
        try {
            System.out.println("Processing log " + logId + " on " + Thread.currentThread().getName());
            Thread.sleep(500);
            System.out.println("Log " + logId + " processed.");
        } catch (InterruptedException e) {
            System.out.println("Log " + logId + " processing was interrupted!");
        }
    }
}

public class LogSystem {
    public static void main(String[] args) throws InterruptedException {
        LogProcessor processor = new LogProcessor();
        ExecutorService executor = Executors.newCachedThreadPool();

        int totalLogs = 30;
        for (int i = 1; i <= totalLogs; i++) {
            final int logId = i;
            executor.submit(() -> processor.processLog(logId));
        }
        Thread.sleep(700);

        List<Runnable> neverStarted = executor
                .shutdownNow();
        System.out.println("Tasks never started: " + neverStarted.size());

        boolean termintedInTime = executor.awaitTermination(3,
                TimeUnit.SECONDS);
        System.out.println("Executor terminated in time: " + termintedInTime);

        System.out.println("Emergency shutdown complete.");
    }

}
