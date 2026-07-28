class LowBalanceException extends Exception {
    public LowBalanceException(String message) {
        super(message);
    }
}

public class MobileBalanceDemo {
    static void checkBalance(int balance) throws LowBalanceException {
        if (balance < 10) {
            throw new LowBalanceException("Low Balance! Please recharge.");
        } else {
            System.out.println("Balance is sufficient: Rs. " + balance);
        }
    }

    public static void main(String[] args) {
        try {
            checkBalance(2);
        } catch (LowBalanceException e) {
            System.out.println(e.getMessage());
        }
    }

}
