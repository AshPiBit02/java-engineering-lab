public class dummy {
    private static int num = 1;
    private static final int max = 50;

    public synchronized void print2multiples() {
        while (num <= max) {
            if (num % 5 == 0) {
                System.out.println("Multiple of 5: " + num);
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

    public synchronized void printanyMultiples() {
        while (num <= max) {
            if (num % 5 != 0) {
                System.out.println("Other multiple: " + num);
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

    public static void main(String[] args) {
        dummy d = new dummy();
        Thread t1 = new Thread(() -> {
            d.print2multiples();
        });

        Thread t2 = new Thread(() -> {
            d.printanyMultiples();
        });

        t1.start();
        t2.start();
    }
}
