public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int[] counter = { 1 };
        Thread t = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r" + counter[0]++);
                try {
                    Thread.sleep(234);
                } catch (InterruptedException e) {
                    System.out.println("\nPaused " + counter[0]);
                    break;
                }
            }

        });

        t.start();
        Thread.sleep(3699);
        t.interrupt();
        t.join();
        System.out.println("Stopped!");
    }
}
