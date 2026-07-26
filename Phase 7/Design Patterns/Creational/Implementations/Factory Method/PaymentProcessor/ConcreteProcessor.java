public class ConcreteProcessor {
    static class CreditCardPayment extends PaymentProcessor {
        PaymentMethod createPaymentMethod() {
            return new ConcretePayment.CreditCardPayment();
        }
    }

    static class PayPalPayment extends PaymentProcessor {
        PaymentMethod createPaymentMethod() {
            return new ConcretePayemet.PayPalPayment();
        }
    }
}
