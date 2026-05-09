class Vehicle {
    void start() {
        System.out.println("Vehicle started!");
    }
}

class Car extends Vehicle {
    @Override
    void start() {
        System.out.println("Car started!");

    }

    double fuelEfficiency(double distance, double fuel) {
        return distance / fuel;
    }

}

public class Master {
    public static void main(String[] args) {
        Vehicle v = new Car(); // upcast
        Car c = (Car) v;
        double efficiency = c.fuelEfficiency(200.58, 20.2);
        System.out.printf("Fuel Efficiency: %.2f km/L", efficiency);

    }
}
