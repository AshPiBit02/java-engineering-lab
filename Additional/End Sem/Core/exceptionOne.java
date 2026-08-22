public class exceptionOne {
    static void divide(float x, float y) throws Exception {
        if (y == 0) {
            throw new Exception("Cannot divide by zero!");
        }
        System.out.println("Result is " + x / y);
    }

    public static void main(String[] args) {
        try {
            exceptionOne.divide(10, 0);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
