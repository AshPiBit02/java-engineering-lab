import java.util.Random;

class Logger implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("[Logger] heartbeat");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class Sensor implements Runnable {
    private String name;
    private boolean faulty;
    private Random rand = new Random();

    Sensor(String name, boolean faulty) {
        this.name = name;
        this.faulty = faulty;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            int reading = rand.nextInt(101);
            System.out.println(name + " reading: " + reading);
            if (i == 3 && faulty) {
                throw new RuntimeException("Sensor malfunction!");
            }
            try {
                Thread.sleep(rand.nextInt(301) + 300);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

public class BackgroundLogger {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Sensor("Sensor-A", false));
        Thread t2 = new Thread(new Sensor("Sensor-B", true));
        Thread t3 = new Thread(new Sensor("Sensor-C", false));
        Thread t4 = new Thread(new Logger());

        t4.setDaemon(true);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        for (int i = 0; i < 6; i++) {
            Thread.sleep(500);
            System.out.println("Is Alive -> A: " + t1.isAlive() + " B: " + t2.isAlive() + " C: " + t3.isAlive());
        }

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Monitoring session ended.");
    }

}
