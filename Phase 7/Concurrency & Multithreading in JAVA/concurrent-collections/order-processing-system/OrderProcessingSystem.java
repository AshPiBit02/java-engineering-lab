import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderProcessingSystem {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> orderQueue = new LinkedBlockingQueue<>(20);
        ConcurrentHashMap<String, String> orderStatus = new ConcurrentHashMap<>();

        int numProducers = 3;
        int ordersPerProducers = 10;
        int totalOrders = numProducers * ordersPerProducers;

        Thread[] producers = new Thread[numProducers];
        for (int p = 0; p < numProducers; p++) {
            final int producerId = p;
            producers[p] = new Thread(() -> {
                Random rand = new Random();
                for (int i = 0; i < ordersPerProducers; i++) {
                    try {
                        String orderId = "Order-" + producerId + "-" + i;
                        orderQueue.put(orderId);
                        System.out.println(orderId + " submitted");
                        Thread.sleep(rand.nextInt(101) + 50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        ExecutorService workerPool = Executors.newFixedThreadPool(4);
        for (int w = 0; w < 4; w++) {
            String workerName = "Worker-" + w;
            workerPool.submit(() -> {
                Random rand = new Random();
                while (orderStatus.size() < totalOrders) {
                    try {
                        String orderId = orderQueue.poll(500, TimeUnit.MILLISECONDS);
                        if (orderId != null) {
                            Thread.sleep(rand.nextInt(101) + 50);
                            orderStatus.put(orderId, "PROCESSED");
                            System.out.println(workerName + " completed " + orderId);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        ScheduledExecutorService reporter = Executors.newScheduledThreadPool(1);
        reporter.scheduleAtFixedRate(
                () -> System.out.println("Processed so far: " + orderStatus.size() + " / " + totalOrders), 0, 1,
                TimeUnit.SECONDS);

        for (Thread t : producers) {
            t.start();
        }
        for (Thread t : producers) {
            t.join();
        }

        while (orderStatus.size() < totalOrders) {
            Thread.sleep(101);
        }

        workerPool.shutdown();
        reporter.shutdown();
        workerPool.awaitTermination(5, TimeUnit.SECONDS);
        reporter.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Final: " + orderStatus.size() + " / " + totalOrders + " tasks completed.");
    }
}
