public class Main {
    public static void main(String[] args) {
        SessionManager sm1 = SessionManager.INSTANCE;
        sm1.login("Ramu Lal");
        sm1.getCurrentUser();
        sm1.log("User session started");

        SessionManager sm2 = SessionManager.INSTANCE;
        System.out.println(sm1 == sm2);
        System.out.println("Current User: " + sm2.getCurrentUser());

        sm2.logout();
        sm2.log("User session ended.");
        System.out.println("Current User: " + sm1.getCurrentUser());

    }
}
