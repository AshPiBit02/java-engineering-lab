class TicketCounter {
    private static int globalTicket = 1;
    private static int localTicket = 1;

    public static synchronized int getGlobalTicket() {
        return globalTicket++;
    }

    public synchronized int getLocalTicket() {
        return localTicket++;
    }
}

public class Clfinally {
    public static void main(String[] args) throws InterruptedException {
        TicketCounter counter1 = new TicketCounter();
        TicketCounter counter2 = new TicketCounter();

        Runnable globalTask = () -> {
            for (int i = 0; i < 8; i++) {
                int id = TicketCounter.getGlobalTicket();
                System.out.println(Thread.currentThread().getName() + " Global Ticket: " + id);
            }
        };

        Runnable localTask1 = () -> {
            for (int i = 0; i < 8; i++) {
                int id = counter1.getLocalTicket();
                System.out.println(Thread.currentThread().getName() + " Local Ticket: " + id);
            }
        };

        Runnable localTask2 = () -> {
            for (int i = 0; i < 8; i++) {
                int id = counter2.getLocalTicket();
                System.out.println(Thread.currentThread().getName() + " Local Ticket: " + id);
            }
        };

        Thread t1 = new Thread(globalTask, "Cleak-1");
        Thread t2 = new Thread(globalTask, "Cleak-2");
        Thread t3 = new Thread(localTask1, "Cleak-3");
        Thread t4 = new Thread(localTask2, "Cleak-4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

    }

}
