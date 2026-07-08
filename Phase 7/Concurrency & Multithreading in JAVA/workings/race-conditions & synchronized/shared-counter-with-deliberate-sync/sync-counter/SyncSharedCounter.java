import javax.rmi.ssl.SslRMIClientSocketFactory;

class Counter implements Runnable {
    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        synchronized (this) {
            increment();
        }
    }
}

public class SyncSharedCounter {
    public static void main(String[] args) throws InterruptedException {

        Counter c = new Counter();
        Thread[] threads = new Thread[5500];

        for (int i = 0; i < 5500; i++) {
            threads[i] = new Thread(c);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Count(5500 expected): " + c.getCount());
    }

}
