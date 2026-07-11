import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class checkoutSystem {
    private int freeCounter = 3;
    private ReentrantLock lock = new ReentrantLock();
    private Condition counterAvailable = lock.newCondition();
    private AtomicInteger totalServed = new AtomicInteger(0);
    private AtomicInteger currentlyWaiting = new AtomicInteger(0);

    public void checkout(String customerName) throws InterruptedException {
        currentlyWaiting.incrementAndGet();
        lock.lock();
        try {
            while (freeCounter == 0) {
                counterAvailable.await();
            }
            freeCounter--;
            currentlyWaiting.decrementAndGet();
            System.out.println(customerName + " is checking out...");
        } finally {
            lock.unlock();
        }

        Random rand = new Random();
        Thread.sleep(rand.nextInt(501) + 300);

        lock.lock();
        try {
            freeCounter++;
            totalServed.incrementAndGet();
            System.out.println(customerName + " checked out.");
            counterAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getTotalServed() {
        return totalServed.get();
    }

    public int getCurrentlyWaiting() {
        return currentlyWaiting.get();
    }
}

public class LoadBalancer {
    public static void main(String[] args) throws InterruptedException {
        checkoutSystem cs = new checkoutSystem();
        int numCustomers = 15;
        Thread[] customers = new Thread[numCustomers];
        for (int i = 0; i < numCustomers; i++) {
            final int id = i + 1;
            customers[i] = new Thread(() -> {
                try {
                    cs.checkout("Cus-Out-" + ((id * 100) + id));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (Thread cus : customers) {
            cus.start();
        }
        for (Thread cus : customers) {
            cus.join();
        }

        System.out.println("Total Served: " + cs.getTotalServed());
        System.out.println("Currently waiting: " + cs.getCurrentlyWaiting());
    }

}
