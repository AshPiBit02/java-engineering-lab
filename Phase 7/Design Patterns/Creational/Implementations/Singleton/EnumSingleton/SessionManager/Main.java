import java.lang.reflect.Constructor;

public class Main {
    public static void main(String[] args) {
        SessionManager sm1 = SessionManager.INSTANCE;
        sm1.login("Ramu Lal");
        System.out.println("Current user: "+sm1.getCurrentUser());

        SessionManager sm2 = SessionManager.INSTANCE;
        System.out.println(sm1 == sm2);
        System.out.println("Current User: " + sm2.getCurrentUser());

        sm2.logout();
        System.out.println("Current User: " + sm1.getCurrentUser());

        System.out.println("\nAttempting reflection attack.......");
        try{
            Constructor<SessionManager> constructor = SessionManager.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            SessionManager hacked=constructor.newInstance();
            System.out.println("Reflection succeeded (this shouldn't happen!)");
        }catch(Exception e){
            System.out.println("Reflection blocked: "+e);
        }

    }
}



