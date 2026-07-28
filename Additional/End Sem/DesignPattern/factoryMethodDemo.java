interface Vehicle {
    void manufacture();
}

class Car implements Vehicle {
    public void manufacture() {
        System.out.println("Manufacturing Car.");
    }
}

class Bike implements Vehicle {
    public void manufacture() {
        System.out.println("Manufacturing Bike.");
    }
}

abstract class VehicleFactory {
    abstract Vehicle createVehicle();
}

class CarFactory extends VehicleFactory {
    Vehicle createVehicle() {
        return new Car();
    }
}

class BikeFactory extends VehicleFactory {
    Vehicle createVehicle() {
        return new Bike();
    }
}

public class factoryMethodDemo {
    public static void main(String[] args) {
        VehicleFactory bikefactory = new BikeFactory();
        Vehicle bike1 = bikefactory.createVehicle();
        bike1.manufacture();
    }

}
