import java.util.Random;

class TrafficLight implements Runnable {
    private String TLName;
    private Random rand = new Random();

    TrafficLight(String TLName) {
        this.TLName = TLName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                showColor("Red", rand.nextInt(901) + 100);
                showColor("Yellow", rand.nextInt(501) + 100);
                showColor("Green", rand.nextInt(701) + 100);
            } catch (InterruptedException e) {
                System.out.println(TLName + " shutting down for maintenance.");
                break;
            }
        }
    }

    private void showColor(String color, int sleepMs) throws InterruptedException {
        System.out.println("-".repeat(5) + color + "(" + TLName + ")" + "-".repeat(5));
        Thread.sleep(sleepMs);

    }
}

public class TrafficLightSys {

    public static void main(String[] args) throws InterruptedException {
        TrafficLight tl1 = new TrafficLight("TL One");
        TrafficLight tl2 = new TrafficLight("TL Two");
        TrafficLight tl3 = new TrafficLight("TL Three");

        Thread t1 = new Thread(tl1);
        Thread t2 = new Thread(tl2);
        Thread t3 = new Thread(tl3);

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2000);
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("All signals shut down. Maintenace complete.");

    }

}
