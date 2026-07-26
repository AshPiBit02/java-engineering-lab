public class ConcreteCreators {
    static class CarFactory extends VehicleFactory {
        Vehicle createVehicle() {
            return new ConcreteProducts.Car();
        }
    }

    static class BikeFactory extends VehicleFactory {
        Vehicle createVehicle() {
            return new ConcreteProducts.Bike();
        }
    }
}
