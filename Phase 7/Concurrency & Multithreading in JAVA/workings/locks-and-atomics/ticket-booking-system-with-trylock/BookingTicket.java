import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class TicketCounter {
    private int seatsAvailable = 10;
    private ReentrantLock lock = new ReentrantLock();

    public boolean bookSeat(String customerName) {
        int attempts = 0;
        while (attempts < 5) {
            if (lock.tryLock()) {
                try {
                    Thread.sleep(20);
                    if (seatsAvailable > 0) {
                        seatsAvailable--;
                        System.out.println(customerName + ": booked a seat!");
                        return true;
                    } else {
                        System.out.println(customerName + ": sold out!!!");
                        return false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(customerName + ": system busy, try again later");
                attempts++;
                try {
                    Thread.sleep(81);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        System.out.println(customerName + ": frustrated after retries.");
        return false;
    }
}

public class BookingTicket {
    public static void main(String[] args) throws InterruptedException {
        TicketCounter counter = new TicketCounter();
        AtomicInteger successCount = new AtomicInteger(0);

        int numCustomers = 33;
        Thread[] threads = new Thread[numCustomers];

        for (int i = 0; i < numCustomers; i++) {
            final int customerId = i;
            threads[i] = new Thread(() -> {
                boolean success = counter.bookSeat("Customer-" + customerId);
                if (success) {
                    successCount.incrementAndGet();
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Total successful bookings: " + successCount.get());
    }

}
