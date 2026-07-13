import java.util.Random;
import java.util.concurrent.*;

public class MiniTaskTracker {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>(15);

        ConcurrentHashMap<String, String> taskStatus = new ConcurrentHashMap<>();

        int numCreators = 2;
        int tasksPerCreators = 5;
        int totalTasks = numCreators * tasksPerCreators;

        Thread[] creators = new Thread[numCreators];
        for (int c = 0; c < numCreators; c++) {
            final int creatorId = c;
            creators[c] = new Thread(() -> {
                Random rand = new Random();
                for (int i = 0; i < tasksPerCreators; i++) {
                    try {
                        String taskId = "Task=" + creatorId + "-" + i;
                        taskQueue.put(taskId);
                        System.out.println(taskId + " created");
                        Thread.sleep(rand.nextInt(101) + 50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        ExecutorService workerPool = Executors.newFixedThreadPool(3);
        for (int w = 0; w < 3; w++) {
            final String workerName = "Worker-" + w;
            workerPool.submit(() -> {
                Random rand = new Random();
                while (taskStatus.size() < totalTasks) {
                    try {
                        String taskId = taskQueue.poll(500, TimeUnit.MILLISECONDS);
                        if (taskId != null) {
                            Thread.sleep(rand.nextInt(101) + 50);
                            taskStatus.put(taskId, "Done");
                            System.out.println(workerName + " completed " + taskId);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        ScheduledExecutorService reporter = Executors.newScheduledThreadPool(1);
        reporter.scheduleAtFixedRate(
                () -> System.out.println(">>> Progress: " + taskStatus.size() + " / " + totalTasks), 0, 1,
                TimeUnit.SECONDS);

        for (Thread t : creators) {
            t.start();
        }
        for (Thread t : creators) {
            t.join();
        }

        while (taskStatus.size() < totalTasks) {
            Thread.sleep(101);
        }

        workerPool.shutdown();
        reporter.shutdown();
        workerPool.awaitTermination(5, TimeUnit.SECONDS);
        reporter.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Final: " + taskStatus.size() + " / " + totalTasks + " tasks completed.");

    }
}
