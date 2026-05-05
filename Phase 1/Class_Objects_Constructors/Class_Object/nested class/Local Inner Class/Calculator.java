class Compute{
    void calculate(){
        class Adder{
            int add(int a ,int b){
                return a+b;
            }
        }
        Adder add= new Adder();
        System.out.println("Result: "+add.add(5,4));
    }
}

public class Calculator {
    public static void main(String[] args){
        Compute add=new Compute();
        add.calculate();
    }
    
}
