public class calculator {
    public int add(int a,int b){
        return a+b;
    }
    public int product(int a,int b){
        return a*b;
    }

    public static void main(String[] args){
        calculator calc=new calculator();

        System.out.println("Sum: "+ calc.add(2,5));
        System.out.println("Sum: "+ calc.product(2,5));
    }
}
