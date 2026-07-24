public class OrderService {
    private Logger logger = AuditLogger.getInstance();

    void placeOrder(String orderId) {
        logger.log("Order placed: " + orderID);
    }
}
