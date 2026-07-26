abstract class PaymentProcessor {
    abstract PaymentMethod createPaymentMethod();

    void checkout(double amount) {
        validate(amount);
        PaymentMethod method = createPaymentMethod();
        method.process(amount);
        confirm();
    }

    private void validate(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount!");
        System.out.println("Validating amount: $" + amount);
    }

    private void confirm() {
        System.out.println("Payment confirmed.\n");
    }
}
