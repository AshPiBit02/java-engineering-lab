import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class Resource {
    private String name;

    Resource(String name) {
        this.name = name;
    }

}

public class DeadLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Resource ticket1 = new Resource("Ticket-1");
        Resource ticket2 = new Resource("Ticket-2");

        Runnable agentA = () -> {
            synchronized (ticket1) {
                System.out.println("Agent A locked Ticket-1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                synchronized (ticket2) {
                    System.out.print("Agent A locked Ticket-2");
                }
            }
        };
        Runnable agentB = () -> {
            synchronized (ticket2) {
                System.out.println("Agent B locked Ticket-2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                synchronized (ticket1) {
                    System.out.print("Agent B locked Ticket-1");
                }
            }
        };
        new Thread(agentA).start();
        new Thread(agentB).start();

        Thread.sleep(1500);
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] ids = bean.findDeadlockedThreads();
        if (ids != null) {
            System.out.println("Deadlock detected!!!!!");
            System.out.print("Exited");
            System.exit(0);
        }
    }

}
