public class ConcreteCreators {
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
}
