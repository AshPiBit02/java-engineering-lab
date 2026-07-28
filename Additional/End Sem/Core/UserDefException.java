class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

public class UserDefException {

    static void enterMark(int mark) {
        try {
            if (mark < 0 || mark > 100) {
                throw new InvalidMarksException("Marks must be between 0 and 100");
            } else {
                System.out.println("Marks recorded successfully: " + mark);
            }
        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserDefException.enterMark(15);
    }
}
