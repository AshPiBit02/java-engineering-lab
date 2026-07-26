abstract class VehicleFactory {
    abstract Vehicle createVehicle();

    void manufacture() {
        Vehicle v = createVehicle();
        v.assemble();
        inspect(v);
        System.out.println("Vehicle ready for delivery.\n");
    }

    private void inspect(Vehicle v) {
        System.out.println("Running quality insepction...");
    }
}
