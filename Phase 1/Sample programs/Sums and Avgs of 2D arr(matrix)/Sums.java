import java.util.Scanner;
public class Sums {
    public static void main(String[] args){
        Scanner s=new Scanner(System.in);
        int row,col,i,j;
        System.out.print("Enter no. of rows: ");
        row=s.nextInt();
        System.out.print("Enter no. of columns: ");
        col=s.nextInt();

        int[][] mat=new int[row][col]; //contains matrix
        int[] rsum=new int[row]; //will contain array of sum of rows element;
        int[] csum=new int[col]; //will contain array of sum of cols element;
        float[] ravg=new float[row]; //will contain avg of element in each row;
        int element_sum=0;

        System.out.println("Enter the Elements of matrix: ");
        for(i=0;i<row;i++){
            int row_sum=0;
            for(j=0;j<col;j++){
                System.out.printf("Enter element[%d][%d]: ",i,j);
                mat[i][j]=s.nextInt();
                row_sum+=mat[i][j];
                element_sum+=mat[i][j];
            }
            rsum[i]=row_sum;
            ravg[i]=(float)row_sum/row;
        }
        System.out.println("Sum of total elements in the matrix: "+element_sum);

        System.out.println("Sum of elements in each row: ");
        for(i=0;i<row;i++){
            System.out.printf("Row [%d]: %d\n",i,rsum[i]);
        }
        System.out.println("Average of elements in each row: ");
        for(i=0;i<row;i++){
            System.out.printf("Row[%d]: %.2f\n",i,ravg[i]);
        }



    }

    
}
