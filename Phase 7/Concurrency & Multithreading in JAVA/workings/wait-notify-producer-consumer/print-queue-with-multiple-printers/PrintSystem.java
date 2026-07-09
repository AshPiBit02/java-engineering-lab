import java.util.LinkedList;
import java.util.Random;

class PrintQueue {
    private LinkedList<String> jobs = new LinkedList<>();
    private int maxQueueSize = 10;

    public synchronized void submitJob(String job) throws InterruptedException {
        while (maxQueueSize == 10) {
            System.out.println(job + " waiting- queue full");
            wait();
        }
        jobs.add(job);
        maxQueueSize--;
        System.out.println(job + "submitted");
        notifyAll();

    }

    public synchronized String pickJob(String printerName) throws InterruptedException {
        while (jobs.isEmpty()) {
            System.out.println(printerName + " waiting - no jobs");
            wait();
        }
        String job = jobs.getFirst();
        jobs.removeFirst();
        maxQueueSize++;
        System.out.println(printerName + " picked up " + job);
        notifyAll();
        return job;
    }
}

public class PrintSystem {
    public static void main(String[] args) throws InterruptedException {
        PrintQueue queue = new PrintQueue();
        int numSubmitters = 3;
        int numPrinters = 2;
        int jobsPerSubmitter = 10;
        int JobsPerPrinter = 15;

        Thread[] submitters = new Thread[numSubmitters];
        Thread[] printers = new Thread[numPrinters];

        for (int t = 0; t < numSubmitters; t++) {
            final int submitterId = t;
            submitters[t] = new Thread(() -> {
                Random rand = new Random();
                for (int i = 1; i <= jobsPerSubmitter; i++) {
                    try {
                        queue.submitJob("Doc-" + submitterId + "-" + i);
                        Thread.sleep(rand.nextInt(101) + 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        for (int t = 0; t < numPrinters; t++) {
            final String printerName = "Printer-" + t;
            printers[t] = new Thread(() -> {
                Random rand = new Random();
                for (int i = 1; i <= JobsPerPrinter; i++) {
                    try {
                        queue.pickJob(printerName);
                        Thread.sleep(rand.nextInt(301) + 200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (Thread t : submitters)
            t.start();
        for (Thread t : printers)
            t.start();

        for (Thread t : submitters)
            t.join();
        for (Thread t : printers)
            t.join();

        System.out.println("All print jobs completed.");

    }

}
