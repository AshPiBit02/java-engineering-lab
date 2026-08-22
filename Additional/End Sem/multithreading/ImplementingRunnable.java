public class ImplementingRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Running Thread by implementing Runnable interface");
    }

    public static void main(String[] args) {
        ImplementingRunnable obj = new ImplementingRunnable();
        Thread t1 = new Thread(obj);
        t1.start();
    }

}
