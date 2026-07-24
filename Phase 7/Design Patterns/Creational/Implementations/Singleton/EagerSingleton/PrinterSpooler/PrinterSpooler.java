import java.util.List;
import java.util.ArrayList;

public class PrinterSpooler {
    private static final PrinterSpooler instance = new PrinterSpooler();
    private List<String> jobs = new ArrayList();

    private PrinterSpooler() {
        // jobs={"Teacher","Clerk",""}
        System.out.println("PrintSpooler instance created!");
    }

    public void addJob(String jobName) {
        jobs.add(jobName);
        System.out.println(jobName + " added successfully.");
    }

    public void showJobs() {
        if (jobs.isEmpty()) {
            System.out.println("No Jobs listed yet!");
        } else {
            System.out.println("Jobs:");
            System.out.println("-".repeat(15));
            for (String j : jobs) {
                System.out.println(j);
            }
        }
    }

    public static PrinterSpooler getInstance() {
        return instance;
    }
}
