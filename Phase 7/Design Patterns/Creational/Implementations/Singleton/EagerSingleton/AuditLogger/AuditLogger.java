import java.util.*;
import java.time.LocalDateTime;

public class AuditLogger implements Logger {
    private static final AuditLogger instance = new AuditLogger();
    private List<String> auditTrail;

    private AuditLogger() {
        if (instance != null) {
            throw new RuntimeException("Instance already exits? Use getInstance().");
        }
        auditTrail = new ArrayList<>();
        System.out.println("Audit Trail Initialized!");
    }

    @Override
    public void log(String action) {
        LocalDateTime ldt = LocalDateTime.now();
        String entry = String.format("[%s] %s", ldt, action);
        auditTrail.add(entry);
        System.out.println("LOGGED -> " + entry);
    }

    public void printAuditTrail() {
        if (auditTrail.isEmpty()) {
            System.out.println("No entries yet.");
            return;
        } else {
            System.out.println("-".repeat(20) + " AUDIT TRAIL " + "-".repeat(20));
            auditTrail.forEach(System.out::println);
            System.out.println("-".repeat(60));
        }
    }

    public static AuditLogger getInstance() {
        return instance;
    }

}
