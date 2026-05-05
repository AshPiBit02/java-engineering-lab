//abstract class
abstract class Car{
    // abstract method (no body)
    public abstract void carInfo();

    //Concrete method
    public void drive(){
        System.out.println("Driving at Speed: 127 Km/hr");
    }
}
class BMW extends Car{
    public void carInfo(){
        System.out.println("A German Engineering Marvel.");
    }
    public void drive(){
        System.out.println("Driving a German Car");
    }
    protected void description()
    {
        System.out.println("Descriptions:\nContains only abstract methods.\nWorks almost like an interface.\nEvery subclass must implement all methods.");
    }
}
public class Main{
    public static void main(String[] args){
        BMW car1=new BMW();
        car1.carInfo();
        car1.drive();
        car1.description();
    }
}
    

