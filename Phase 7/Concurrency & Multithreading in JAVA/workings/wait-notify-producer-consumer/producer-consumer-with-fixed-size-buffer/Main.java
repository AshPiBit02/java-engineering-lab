import java.util.LinkedList;
import java.util.Random;

class Buffer {
    private LinkedList<Integer> buffer = new LinkedList<>();
    private int space = 7;

    public synchronized void produce(int value) throws InterruptedException {
        while (space == 0) {
            System.out.println(Thread.currentThread().getName() + " Waiting...");
            wait();
        }
        buffer.add(value);
        space--;
        System.out.println("Produced: " + value);
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " Waiting...");
            wait();
        }
        int value = buffer.getFirst();
        buffer.removeFirst();
        space++;
        System.out.println("Consumed: " + value);
        notifyAll();
        return value;
    }

}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        Buffer buf = new Buffer();
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    buf.produce(i);
                    Thread.sleep(rand.nextInt(201) + 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Producer");

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    buf.consume();
                    Thread.sleep(rand.nextInt(301) + 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Done");
    }

}

    
    

    
    

    