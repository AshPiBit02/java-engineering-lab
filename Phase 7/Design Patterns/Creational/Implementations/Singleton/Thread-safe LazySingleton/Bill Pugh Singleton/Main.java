public class Main {
    public static void main(String[] args) {
        System.out.println("Before calling getInstance()....");
        ReportGenerator master1 = ReportGenerator.getInstance();
        master1.generateReport("Bug free, initiate to deploy");
        master1.generateReport(
                "User exponentially grew, done veritcal scaling for now, gathering resource for horizontal scaling");
        ;

        ReportGenerator master2 = ReportGenerator.getInstance();
        master2.showReports();
    }
}
