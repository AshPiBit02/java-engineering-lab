public class oddEvenPrinter {
    private int num = 1;
    private final int max = 20;

    public synchronized void printOdd() {
        while (num <= max) {
            if (num % 2 == 1) {
                System.out.println("Odd: " + num);
                num++;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public synchronized void printEven() {
        while (num <= max) {
            if (num % 2 == 0) {
                System.out.println("Even: " + num);
                num++;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        oddEvenPrinter oep = new oddEvenPrinter();
        Thread oddPrinter = new Thread(() -> oep.printOdd());
        Thread evenPrinter = new Thread(() -> oep.printEven());

        oddPrinter.start();
        evenPrinter.start();

        oddPrinter.join();
        evenPrinter.join();

        System.out.println("Done!");
    }
}
