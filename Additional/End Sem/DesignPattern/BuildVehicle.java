interface Vehicle {
    void manufactureVehicle();
}

class MotorBike implements Vehicle {
    public void manufactureVehicle() {
        System.out.println("Manufacturing MotorBike....");
    }
}

class Car implements Vehicle {
    public void manufactureVehicle() {
        System.out.println("Manufacturing Car....");
    }
}

class VehicleFactory {
    public Vehicle getVehicle(String type) {
        if (type.equalsIgnoreCase("bike")) {
            return new MotorBike();
        } else if (type.equalsIgnoreCase("car")) {
            return new Car();
        }
        return null;
    }
}

public class BuildVehicle {
    public static void main(String[] args) {
        VehicleFactory factory = new VehicleFactory();
        Vehicle bike = factory.getVehicle("bike");
        bike.manufactureVehicle();
        Vehicle car = factory.getVehicle("car");
        car.manufactureVehicle();
    }
}
