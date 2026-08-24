public class ConcreteCreators {
    static class CarFactory extends VehicleFactory {
        BuildVehicle createVehicle() {
            return new ConcreteProducts.Car();
        }
    }

    static class BikeFactory extends VehicleFactory {
        BuildVehicle createVehicle() {
            return new ConcreteProducts.Bike();
        }
    }
}
