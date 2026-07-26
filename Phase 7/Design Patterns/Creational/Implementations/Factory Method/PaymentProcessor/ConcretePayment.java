public class ConcretePayment {
    static public class CreditCardPayment implements PaymentMethod {
        @Override
        public void process(double amount) {
            System.out.println("Processing $" + amount + " via Credit Card.");
        }
    }

    static public class PayPalPayment implements PaymentMethod {
        @Override
        public void process(double amount) {
            System.out.println("Processing $" + amount + " via PayPal");
        }
    }
}
