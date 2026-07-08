import java.util.Random;

class SystemCheck implements Runnable {
    private Random random = new Random();
    private String name;

    SystemCheck(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 5; i > 0; i--) {
            System.out.println(name + ": T-minus " + i);
            try {
                Thread.sleep(random.nextInt(200) + 301);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(name + " check complete!");
    }
}

public class countdownLauchSeq {
    public static void main(String[] args) {
        Thread t1 = new Thread(new SystemCheck("Fuel"));
        Thread t2 = new Thread(new SystemCheck("Engine"));
        Thread t3 = new Thread(new SystemCheck("Navigation"));
        Thread t4 = new Thread(new SystemCheck("Communications"));

        System.out.println();
        System.out.println("All system check before launch started");
        System.out.println("*".repeat(40));
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        System.out.println("-".repeat(30));
        System.out.println("All systems go. LiftOff!");

    }

}
