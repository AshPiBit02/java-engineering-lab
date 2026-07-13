class Book {
    private int copies = 79;
    private int successfulReservation = 0;
    private int uncessfulReservation = 0;

    public synchronized boolean reserve() {
        if (copies > 0) {
            copies--;
            successfulReservation++;
            return true;
        } else {
            uncessfulReservation++;
        }
        return false;
    }

    public synchronized void cancelReservation() {
        copies++;
    }

    public synchronized int getCopies() {
        return copies;
    }

    public synchronized int getSuccessfulReservations() {
        return successfulReservation;
    }

    public synchronized int getUnsuccessfulReservations() {
        return uncessfulReservation;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Book book = new Book();

        int studentCount = 200;
        Thread[] threads = new Thread[studentCount];

        for (int i = 0; i < studentCount; i++) {
            final int studentId = i;
            threads[i] = new Thread(() -> {
                boolean success = book.reserve();
                System.out.println("Student-" + studentId + ": " + (success ? "got the book" : "no copies left"));

                if ((studentId % 3 == 0 || studentId % 5 == 0) && success) {
                    book.cancelReservation();
                    System.out.println("Student-" + studentId + ": cancelled reservation!");
                }

            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Remainig copies: " + book.getCopies());
        System.out.println("Successful reservations: " + book.getSuccessfulReservations());
        System.out.println("Unsuccessful Reservations: " + book.getUnsuccessfulReservations());
        System.out.println("Expected min successful reservation: 79");
    }

}
