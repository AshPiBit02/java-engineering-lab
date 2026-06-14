public class ThrowDemo {
    static void checkAge(int age) {
        if (age < 18) {
            throw new ArithmeticException("Age must be 18+");
        }
        System.out.println("Access granted");
    }

    public static void main(String[] args) {
        try {
            checkAge(18);
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }

}
