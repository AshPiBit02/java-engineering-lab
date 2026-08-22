public class ExtendingThread extends Thread {
    @Override
    public void run() {
        System.out.println("Running Thread by extending Thread class.");
    }

    public static void main(String[] args) {
        ExtendingThread t1 = new ExtendingThread();
        t1.start();
    }

}
