class Vehicle{
    void start(){
        System.out.println("Vehicle start!");
    }
}
class Car extends Vehicle{
    @Override
    void start(){
        System.out.println("Car start!");
    }
    void honk(){
        System.out.println("Honkkkk!!!!!!");
    }
}

public class Main {
    public static void main(String[] args){
        Vehicle v=new Car();
        v.start();
        v.honk();
    }
    
}
