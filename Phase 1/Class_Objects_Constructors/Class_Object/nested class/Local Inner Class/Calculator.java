class Compute{
    void calculate(int a,int b){
        class Adder{
            int a,b;
            Adder(int a, int b){
                this.a=a;
                this.b=b;
            }
            int add(){
                return a+b;
            }
        }
        Adder add= new Adder(a,b);
        System.out.println("Result: "+add.add());
    }
}

public class Calculator {
    public static void main(String[] args){
        Compute add=new Compute();
        add.calculate(6,6);
    }
    
}
