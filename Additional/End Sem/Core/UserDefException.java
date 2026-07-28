class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

public class UserDefException {

    static void enterMark(int mark) throws InvalidMarksException {
        if (mark < 0 || mark > 100) {
            throw new InvalidMarksException("Marks must be between 0 and 100");
        } else {
            System.out.println("Marks recorded successfully: " + mark);
        }
    }

    public static void main(String[] args) {
        try {
            UserDefException.enterMark(15);
        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
