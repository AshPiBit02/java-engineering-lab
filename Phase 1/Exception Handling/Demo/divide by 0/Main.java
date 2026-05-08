import java.util.Scanner;
public class Main {

    static float divide(int num1,int num2){
        if(num2==0){
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return (float)num1/num2;
    }
    public static void main(String[] args){
        int dividend,divisor;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter First Number: ");
        dividend=sc.nextInt();

        System.out.println("Enter Second Number: ");
        divisor=sc.nextInt();

        try{
           float x = divide(dividend, divisor);
           System.out.println("Result: " + x);
        }
        catch(ArithmeticException e){
            System.out.println("Arithmetic error: "+e.getMessage());

        }
    }

    
}