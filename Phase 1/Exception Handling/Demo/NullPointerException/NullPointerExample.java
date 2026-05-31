public class NullPointerExample {
    public static void main(String[] args) {
        try {
            String str = null;
            System.out.println("Length of String: " + str.length());
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Error: " + e.getMessage());
        }
    }

}
