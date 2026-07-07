import java.util.Random;

class Runner implements Runnable {
    private String name;
    private Random random = new Random();
    private int distance = 0;

    private static volatile String winner = null;

    public Runner(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int i = 1;
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
        System.out.println(name + " has finished the race!");

        if (winner == null) {
            winner = name;
        }

    }

    public static String getWinner() {
        return winner;
    }
}

public class RaceSimulator {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner("Milka"));
        Thread t2 = new Thread(new Runner("Hussain"));
        Thread t3 = new Thread(new Runner("Tomnyk"));

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
        System.out.println("-".repeat(15));
        System.out.println("Race over! Winnner: " + Runner.getWinner());

    }

}
