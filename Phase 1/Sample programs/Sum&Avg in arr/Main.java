//To find sum and average of all elements in an array.
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        int n,sum=0;
        float avg;
        Scanner s=new Scanner(System.in);
        System.out.print("Enter no. of elements in array: ");
        n=s.nextInt();

        int arr[]=new int[n]; //array init with size n

        System.out.println("Enter all the elements:");
        for(int i=0;i<n;i++){
            System.out.printf("Enter arr[%d]: ",i);
            arr[i]=s.nextInt();
            sum=sum+arr[i];
        }

        System.out.println("Sum: "+sum);
        avg=(float)sum/n;

        System.out.printf("Average: %.2f",avg);
    }
    
}
