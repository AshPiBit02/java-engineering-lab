public class OrderService {
    private ace logger = AuditLogger.getInstance();

    void placeOrder(String orderId) {
        logger.log("Order placed: " + orderId);
    }
}
