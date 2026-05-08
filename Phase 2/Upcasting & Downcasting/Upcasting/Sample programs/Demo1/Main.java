class Parent{
    void PrintData(){
        System.out.println("Method of parent class.");
    }
}
class Child extends Parent{
    void PrintData(){
        System.out.println("Method of child class.");
    }
}
public class Main {
    public static void main(String[] args){
        Parent Obj1=(Parent) new Child();
        Parent Obj2=(Parent) new Child();
        Obj1.PrintData();
        Obj2.PrintData();
    }

    
}
