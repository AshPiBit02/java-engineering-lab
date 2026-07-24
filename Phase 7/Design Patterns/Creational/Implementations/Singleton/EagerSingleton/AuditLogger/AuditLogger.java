import java.util.*;
import java.time.LocalDateTime;

public class AuditLogger implements Logger {
    private static final AuditLogger instance = new AuditLogger();
    private List<String> auditTrail;

    AuditLogger() throws RuntimeException {
        if (instance != null) {
            new RuntimeException("Instance already exits? Use getInstance().");
        }
        auditTrail = new ArrayList();
        System.out.println("Audit Trail Initialized!");
    }

    @Override
    public void log(String action) {
        LocalDateTime ldt = LocalDateTime.now();
        auditTrail.add(ldt + " : " + action);
        System.out.println(action + " : " + ldt);
    }

    public void printAuditTrail() {
        if (auditTrail.isEmpty()) {
            System.out.println("No Entry Yet!");
        } else {
            System.out.println();
            System.out.println("-".repeat(15));
            for (String entries : auditTrail) {
                System.out.println(entries + " Successful");
            }
        }
    }

    public static AuditLogger getInstance() {
        return instance;
    }

}
