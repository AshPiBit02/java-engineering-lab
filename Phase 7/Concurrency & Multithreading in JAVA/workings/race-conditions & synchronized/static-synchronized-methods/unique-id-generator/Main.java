class IdGenerator {
    private static int nextId = 1;

    public static synchronized int getNextId() {
        return nextId++;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                int id = IdGenerator.getNextId();
                System.out.println(Thread.currentThread().getName() + " Generated ID: " + id);
            }
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");
        Thread t3 = new Thread(task, "Thread-C");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        // the static synchronized method ensures only one thread at a time increments
        // the counter, resulting in unique IDs. The only caveat is that the console
        // output order can appear non-sequential due to thread scheduling.
    }

}
