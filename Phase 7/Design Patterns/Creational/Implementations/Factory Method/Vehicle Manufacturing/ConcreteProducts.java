public class ConcreteProducts {
    static class Car implements BuildVehicle {
        @Override
        public void assemble() {
            System.out.println("Assembling Car: chassis, engine, 4 wheels.");
        }
    }

    static class Bike implements BuildVehicle {
        @Override
        public void assemble() {
            System.out.println("Assembling Bike: frame, engine, 2 wheels.");
        }
    }
}
