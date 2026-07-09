class Warehouse {
    private static int globalStock = 0;
    private int localStock = 0;

    public static synchronized void updateGlobalStock(int amount) {
        globalStock += amount;
        System.out.println(Thread.currentThread().getName() + " updated GLOBAL stock, total: " + globalStock);
    }

    public synchronized void updateLocalStock(int amount) {
        localStock += amount;
        System.out.println(Thread.currentThread().getName() + " updated LOCAL stock, total: " + localStock);
    }

    @Override
    public String toString() {
        return "Warehouse@" + Integer.toHexString(hashCode());
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Warehouse warehouseA = new Warehouse();
        Warehouse warehouseB = new Warehouse();

        Runnable globalTask = () -> {
            for (int i = 0; i < 3; i++) {
                Warehouse.updateGlobalStock(10);
            }
        };

        Runnable localTaskA = () -> {
            for (int i = 0; i < 3; i++) {
                warehouseA.updateLocalStock(5);
            }
        };

        Runnable localTaskB = () -> {
            for (int i = 0; i < 3; i++) {
                warehouseB.updateLocalStock(7);
            }
        };

        Thread t1 = new Thread(globalTask, "Global-Agent-1");
        Thread t2 = new Thread(globalTask, "Global-Agent-2");
        Thread t3 = new Thread(localTaskA, "Local-Agent-A");
        Thread t4 = new Thread(localTaskB, "Local-Agent-B");
        // Thread t5 = new Thread(transferTask1, "Transfer-Agent-1");
        // Thread t6 = new Thread(transferTask2, "Transfer-Agent-2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        // t5.start(); t6.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        // t5.join();
        // t6.join();
    }
}
