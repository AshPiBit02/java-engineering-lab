import java.lang.reflect.Constructor;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        userService.createUser("Champak Lal");

        orderService.placeOrder("DNEG03");

        AuditLogger.getInstance().printAuditTrail();

        System.out.println("\n Attempting reflection attack...");
        try {
            Constructor<AuditLogger> constructor = AuditLogger.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            AuditLogger hacked = constructor.newInstance();
            System.out.println("Reflection successed (this shouldn't happen!");
        } catch (Exception e) {
            System.out.println("Reflection blocked: " + e.getCause());
        }

    }
}
