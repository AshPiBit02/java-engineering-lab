class BankAccount implements Runnable {
    private int balance = 0;

    public void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public void run() {
        deposit(1);

    }

}

public class BankAccountRace {
    public static void main(String[] args) throws InterruptedException {
        BankAccount ba = new BankAccount();

        Thread[] threads = new Thread[10050];

        for (int i = 0; i < 10050; i++) {
            threads[i] = new Thread(ba, "Thread" + i + 1);
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
