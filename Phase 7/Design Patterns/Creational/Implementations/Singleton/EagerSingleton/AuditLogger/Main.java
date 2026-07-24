public class Main {
    public static void main(String[] args){
        UserService userService=new UserService();
        OrderService orderService = new OrderService();

        userService.createUser("Champak Lal");
        
        orderService.placeOrder("DNEG03");

        AuditLogger.getInstance().printAuditTrail();

        
    }
}
