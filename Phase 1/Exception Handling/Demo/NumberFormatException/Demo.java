public class Demo {
    public static void main(String[] args) {
        try {
            int num = Integer.parseInt("abc");
            System.out.println("The number is : " + num);
        } catch (NumberFormatException e) {
            System.out.println("Number Format Error! " + e.getMessage());
        }
    }
}