abstract class VehicleFactory {
    abstract BuildVehicle createVehicle();

    void manufacture() {
        BuildVehicle v = createVehicle();
        v.assemble();
        inspect(v);
        System.out.println("Vehicle ready for delivery.\n");
    }

    private void inspect(BuildVehicle v) {
        System.out.println("Running quality insepction...");
    }
}
