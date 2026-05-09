interface Payment {
    void pay();
}

class CreditCard implements Payment {
    @Override
    public void pay() {
        System.out.println("Credit Card Payment");
    }
}

class DebitCard implements Payment {
    @Override
    public void pay() {
        System.out.println("Debit Card Payment");
    }
}

class UPI implements Payment {
    @Override
    public void pay() {
        System.out.println("UPI Payment");
    }
}

class Paypal implements Payment {
    @Override
    public void pay() {
        System.out.println("Paypal Payment");
    }
}

public class PaymentSystem {
    public static void main(String[] args) {
        Payment p = new CreditCard();
        p.pay();
        Payment p1 = new DebitCard();
        p1.pay();
        Payment p2 = new UPI();
        p2.pay();
        Payment p3 = new Paypal();
        p3.pay();
    }

}
