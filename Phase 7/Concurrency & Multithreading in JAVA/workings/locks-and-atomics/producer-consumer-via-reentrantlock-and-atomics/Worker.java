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
            BufferEmpty.signalAll();
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

            BufferFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getNetConsumedProductCount() {
        return totalConsumed.get();
    }

    public int getNetProducedProductCount() {
        return totalProduced.get();
    }
}

public class Worker {
    public static void main(String[] args) throws InterruptedException {
        int maxProduct = 53;
        int numProducers = 5;
        int numConsumers = 9;

        int productPerProducer = numConsumers;
        int productPerConsumer = numProducers;

        Factory fact = new Factory();

        Thread[] producers = new Thread[numProducers];
        Thread[] consumers = new Thread[numConsumers];

        for (int p = 0; p < numProducers; p++) {
            final int producerId = p;
            producers[p] = new Thread(() -> {
                for (int i = 1; i <= productPerProducer; i++) {
                    try {
                        fact.produce(i * 100 + producerId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, "PRODUCER-XV" + producerId);
        }

        for (int c = 0; c < numConsumers; c++) {
            final int consumerId = c;
            consumers[c] = new Thread(() -> {
                for (int i = 1; i <= productPerConsumer; i++) {
                    try {
                        fact.consume();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, "CONSUMER-YN" + consumerId);
        }

        for (Thread p : producers) {
            p.start();
        }
        for (Thread c : consumers) {
            c.start();
        }

        for (Thread p : producers) {
            p.join();
        }

        for (Thread c : consumers) {
            c.join();
        }

        System.out.println("Max Product Production Limit: " + maxProduct);
        System.out.println("Net Product Produced: " + fact.getNetProducedProductCount());
        System.out.println("Net Product Consumed: " + fact.getNetConsumedProductCount());
    }
}
