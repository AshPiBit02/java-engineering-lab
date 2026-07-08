import java.util.Random;

class Runner implements Runnable {
    private String name;
    private Random random = new Random();
    private int distance = 0;
    private long startTime;
    private long endTime;

    private static volatile String winner = null;

    public Runner(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int i = 1;
        startTime = System.currentTimeMillis();
        while (distance < 100) {
            int step = random.nextInt(10) + 1;
            distance += step;
            System.out.println(name + " -> " + distance + "m");
            i++;

            try {
                Thread.sleep(random.nextInt(201) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println(name + " has finished the race!");

        if (winner == null) {
            winner = name;
        }
    }

    public long getRuntime() {
        return endTime - startTime;
    }

    public static String getWinner() {
        return winner;
    }
}

public class RaceSimulator {
    public static void main(String[] args) {
        Runner r1 = new Runner("Milka");
        Runner r2 = new Runner("Hussain");
        Runner r3 = new Runner("Tommy");

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);

        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("Race over!");
        System.out.println("-".repeat(15));
        System.out.println("Mlika runtime: " + r1.getRuntime() + "ms");
        System.out.println("Hussain runtime: " + r2.getRuntime() + "ms");
        System.out.println("Tommy runtime: " + r3.getRuntime() + "ms");
        System.out.println("*".repeat(15));
        System.out.println("Winner: " + Runner.getWinner());


    }

}
