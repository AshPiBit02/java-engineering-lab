//abstract class
abstract class Car{
    // abstract methodsno body)
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
}
public class Main{
    public static void main(String[] args){
        BMW car1=new BMW();
        car1.carInfo();
        car1.drive();
    }
}
    

