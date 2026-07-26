import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    private List<String> reports;

    private ReportGenerator() {
        reports = new ArrayList<>();
        System.out.println("ReportGenerator instance created!");
    }

    private static class Holder {
        private static final ReportGenerator INSTANCE = new ReportGenerator();

    }

    public static ReportGenerator getInstance() {
        return Holder.INSTANCE;
    }

    public void generateReport(String name) {
        LocalDateTime ldt = LocalDateTime.now();
        reports.add(String.format("[%s] %-15s %s %n", ldt, "", name));
        System.out.println("[Report Added]: " + name);
    }

    public void showReports() {
        System.out.println();
        System.out.println("-".repeat(50) + " Reports " + "-".repeat(50));
        for (String report : reports) {
            System.out.println(report);
        }
    }

}
