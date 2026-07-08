class BankAccount implements Runnable {
    private int balance = 200;

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized void withdraw(int amount) {
        balance -= amount;
    }

    public synchronized int getBalance() {
        return balance;
    }

    @Override
    public void run() {
        deposit(10);
        withdraw(10);
    }
}

public class Bank {
    public static void main(String[] args) throws InterruptedException {

        BankAccount ba = new BankAccount();

        Thread[] threads = new Thread[53];

        for (int i = 0; i < 53; i++) {
            threads[i] = new Thread(ba);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Balance: " + ba.getBalance());

    }

}
