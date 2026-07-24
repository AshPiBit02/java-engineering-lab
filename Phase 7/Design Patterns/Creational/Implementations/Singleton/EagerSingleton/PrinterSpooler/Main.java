public class Main {
    public static void main(String[] args) {
        PrinterSpooler ps1 = PrinterSpooler.getInstance();
        PrinterSpooler ps2 = PrinterSpooler.getInstance();

        ps1.addJob("Sales Manager");
        ps1.addJob("Product Manger");
        ps1.addJob("Executive");
        ps1.addJob("Engineer");
        ps1.addJob("Designer");

        ps2.showJobs(); // This proves that both instance are same
    }

}
