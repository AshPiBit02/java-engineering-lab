public class ConcreteProcessor {
    static class CreditCardProcessor extends PaymentProcessor {
        PaymentMethod createPaymentMethod() {
            return new ConcretePayment.CreditCardPayment();
        }
    }

    static class PayPalProcessor extends PaymentProcessor {
        PaymentMethod createPaymentMethod() {
            return new ConcretePayment.PayPalPayment();
        }
    }
}
