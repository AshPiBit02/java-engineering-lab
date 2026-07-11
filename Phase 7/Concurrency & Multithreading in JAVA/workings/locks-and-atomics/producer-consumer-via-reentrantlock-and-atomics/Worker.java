import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Factory {
    private int maxBufferSize = 20;
    private ReentrantLock lock = new ReentrantLock();
    private Condition BufferEmpty = lock.newCondition();
    private Condition BufferFull = lock.newCondition();
    private AtomicInteger totalProduced = new AtomicInteger(0);
    private AtomicInteger totalConsumed = new AtomicInteger(0);
    private LinkedList<Integer> buffer = new LinkedList<>();

    public void produce(int product) throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == maxBufferSize) {
                BufferFull.await();
            }
            System.out.println("Producing: " + product);

            Random rand = new Random();
            Thread.sleep(rand.nextInt(51) + 30);

            buffer.add(product);
            totalProduced.incrementAndGet();

            System.out.println("Product: " + product + " added to buffer.");
            BufferFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == 0) {
                BufferEmpty.await();
            }
            int product = buffer.getFirst();
            System.out.println("Consuming: " + product);

            Random rand = new Random();
            Thread.sleep(rand.nextInt(31) + 20);

            buffer.removeFirst();
            totalConsumed.incrementAndGet();

            System.out.println("Product: " + product + " removed from buffer.");

            BufferEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getNetConsumedProductCount() {
        return totalProduced.get();
    }

    public int getNetProducedProductCount() {
        return totalConsumed.get();
    }
}

public class Worker {
    public static void main(String[] args) throws InterruptedException {
        int maxProduct = 239;
        Factory fact = new Factory();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 239; i++) {
                try {
                    fact.produce(i);
                } catch (InterruptedException e) {
                    System.out.println("Error in production!!!");
                    e.printStackTrace();
                }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 239; i++) {
                try {
                    fact.consume();
                } catch (InterruptedException e) {
                    System.out.println("Error in consumption!!!");
                    e.printStackTrace();
                }
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Max Product Production Limit: " + maxProduct);
        System.out.println("Net Product Produced: " + fact.getNetProducedProductCount());
        System.out.println("Net Product Consumed: " + fact.getNetConsumedProductCount());
    }
}
