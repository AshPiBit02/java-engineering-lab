import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PrintQueue {
    private LinkedList<String> jobs = new LinkedList<>();
    private int maxQueueSize = 10;

    public synchronized void submitJob(String job) throws InterruptedException {
        while (jobs.size() == maxQueueSize) {
            wait();
        }
        jobs.add(job);
        System.out.println(job + " submitted");
        notifyAll();
    }

    public synchronized void pickJob(String printerName) throws InterruptedException {
        while (jobs.isEmpty()) {
            wait();
        }
        String job = jobs.removeFirst();
        System.out.println(printerName + " picked up " + job);
        notifyAll();
    }
}

public class PrintSystem {
    public static void main(String[] args) throws InterruptedException {
        PrintQueue queue = new PrintQueue();

        int numSubmitterTasks = 3;
        int numPrinterTasks = 3;

        int jobsPerSubmitterTask = 15;
        int JobsPerPrinterTask = 15;

        ExecutorService submitterPool = Executors.newFixedThreadPool(2);
        ExecutorService printerPool = Executors.newFixedThreadPool(2);

        for (int s = 0; s < numSubmitterTasks; s++) {
            final int submitterId = s;
            submitterPool.submit(() -> {
                Random rand = new Random();
                for (int i = 1; i <= jobsPerSubmitterTask; i++) {
                    try {
                        queue.submitJob("DOC-" + submitterId + "-" + i);
                        Thread.sleep(rand.nextInt(101) + 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        for (int p = 0; p < numPrinterTasks; p++) {
            final String printerName = "Printer-" + p;
            printerPool.submit(() -> {
                Random rand = new Random();
                for (int i = 1; i <= JobsPerPrinterTask; i++) {
                    try {
                        queue.pickJob(printerName);
                        Thread.sleep(rand.nextInt(301) + 200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        submitterPool.shutdown();
        printerPool.shutdown();

        boolean submittersDone = submitterPool.awaitTermination(5, TimeUnit.SECONDS);
        boolean printersDone = printerPool.awaitTermination(8, TimeUnit.SECONDS);
        System.out.println("Submitters finished in time: " + submittersDone);
        System.out.println("Printers finished in time: " + printersDone);

        System.out.println("All print jobs completed.");
    }
}
