public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new ConcreteProcessor.CreditCardProcessor();
        processor.checkout(150.00);
        processor = new ConcreteProcessor.PayPalProcessor();
        processor.checkout(75.50);
    }
}
