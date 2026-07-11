import java.util.concurrent.locks.ReentrantLock;

class BankAccount implements Runnable {
    private int balance;
    private ReentrantLock lock = new ReentrantLock();

    public void deposit(int amount) {
        lock.lock();
        try {
            if (amount > 0) {
                balance += amount;
                System.out.println("$" + amount + " Deposited successfully!");
                System.out.println("Updated Balance: $" + getBalance());
            }
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount) {
        lock.lock();
        try {
            if (amount < balance && amount > 0) {
                balance -= amount;
                System.out.println("$" + amount + " withdrawn successfully!");
                System.out.println("Updated Balance: $" + getBalance());
            }
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        deposit(500);
        withdraw(250);
    }

}

public class newBank {
    public static void main(String[] args) throws InterruptedException {
        BankAccount ba = new BankAccount();

        Thread[] threads = new Thread[133];

        for (int i = 0; i < 133; i++) {
            threads[i] = new Thread(ba);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Final balance: $" + ba.getBalance());

    }

}
