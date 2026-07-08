import java.util.Random;

class Worker implements Runnable {
    private Random random = new Random();

    @Override
    public void run() {
        int n = 0;
        while (true) {
            System.out.println("Working.. tick " + n);
            n++;
            try {
                Thread.sleep(random.nextInt(501) + 200);
            } catch (InterruptedException e) {
                System.out.println("Worker inpterrupted, stopping.");
                break;
            }
        }
    }
}

public class CancWork {
    public static void main(String[] args) throws InterruptedException {
        Thread Tworker = new Thread(new Worker());
        Tworker.start();

        Thread.sleep(3000);
        Tworker.interrupt();
        Tworker.join();
        System.out.println("Main: worker has stopped");

    }

}
