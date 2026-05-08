import java.util.Scanner;
import java.util.Arrays;
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
        Arrays.fill(csum,0); //init with 0 for all index
        float[] ravg=new float[row]; //will contain avg of element in each row;
        int element_sum=0;
        
        System.out.println("Enter the Elements of matrix: ");
        for(i=0;i<row;i++){
            int row_sum=0;
            for(j=0;j<col;j++){
                System.out.printf("Enter element[%d][%d]: ",i,j);
                mat[i][j]=s.nextInt();
                row_sum+=mat[i][j];
                csum[j]+=mat[i][j];
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
        
        System.out.println("Sum of elements in each column: ");
        for(i=0;i<col;i++){
            System.out.printf("Col[%d: %d\n",i,csum[i]);
        }
        
        System.out.println("Average of elements in each column: ");
        for(i=0;i<col;i++){
            float cavg=(float)csum[i]/col;
            System.out.printf("Col[%d]: %.2f\n",i,cavg);
        }
        
        // if input matrix is diagonal matrix
        if(row==col){
            int cd_sum=0; //will contain sum of left diagonal elements.
            int rd_sum=0;//will contain the sum of diagonal elements.

            //for right diagonal(\)
            for(i=0;i<row;i++){
                for(j=0;j<col;j++){
                    if(i==j){
                        rd_sum+=mat[i][j];
                    }
                }
            }   
            
            //for left diagonal(/)
            i=0;
            j=col-1;
            while(i<col && j>=0){
                cd_sum+=mat[i][j];
                i++;
                j--;
            }

            //Total diagonal element sum
            int d_total=rd_sum+cd_sum;

            if(row%2!=0){
                int x=row/2;//provide centre element that it present in both diagonal

                d_total-=mat[x][x];
            }
            System.out.println("Sum of all Diagonal elements: "+d_total);
        }
        
        

    }

    
}
