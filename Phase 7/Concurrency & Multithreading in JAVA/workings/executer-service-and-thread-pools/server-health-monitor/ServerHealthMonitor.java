import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class healthChecker {
    public void checkServer(String serverName) {
        boolean healthy = new Random().nextInt(10) < 8;
        System.out.println(serverName + " status: " + (healthy ? "RUNNING" : "DOWN"));
    }
}

class IncidentHandler {
    public void handleIncident(int incidentId) {
        System.out.println("handling incident #" + incidentId + " on " + Thread.currentThread().getName());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Incident #" + incidentId + " resolved.");
    }
}

public class ServerHealthMonitor {
    public static void main(String[] args) throws InterruptedException {
        healthChecker healthChecker = new healthChecker();
        IncidentHandler incidentHandler = new IncidentHandler();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        ExecutorService incidentPool = Executors.newFixedThreadPool(2);

        String[] servers = { "Server-A", "Server-B", "Server-C" };
        for (String server : servers) {
            scheduler.scheduleAtFixedRate(() -> healthChecker.checkServer(server), 0, 1, TimeUnit.SECONDS);
        }

        AtomicInteger incidentCounter = new AtomicInteger(0);
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            int incidentId = incidentCounter.incrementAndGet();
            incidentPool.submit(() -> incidentHandler.handleIncident(incidentId));
            Thread.sleep(rand.nextInt(501) + 500);
        }
        Thread.sleep(3000);

        scheduler.shutdown();
        incidentPool.shutdown();

        scheduler.awaitTermination(5, TimeUnit.SECONDS);
        incidentPool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Monitoring stopped.");

    }
}
