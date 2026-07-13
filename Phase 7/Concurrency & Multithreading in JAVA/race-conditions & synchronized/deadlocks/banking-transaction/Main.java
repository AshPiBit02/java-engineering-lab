class Account {
    int id;
    private int balance;

    Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public synchronized void withdraw(int amount) {
        balance -= amount;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized int getBalance() {
        return balance;
    }
}

public class Main {
    static void transferBuggy(Account from, Account to, int amount) {
        synchronized (from) {
            synchronized (to) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    static void transferFixed(Account from, Account to, int amount) {
        Account first = (from.id < to.id) ? from : to;
        Account second = (from.id < to.id) ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(101, 75500);
        Account b = new Account(102, 92556);

        Runnable r1 = () -> {
            for (int i = 0; i < 1000; i++) {
                // transferBuggy(a, b, 10);
                transferFixed(a, b, 10);
            }
        };

        Runnable r2 = () -> {
            for (int i = 0; i < 1000; i++) {
                // transferBuggy(b, a, 11);
                transferFixed(b, a, 10);
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Balance A: " + a.getBalance());
        System.out.println("Balance B: " + b.getBalance());
    }

}
