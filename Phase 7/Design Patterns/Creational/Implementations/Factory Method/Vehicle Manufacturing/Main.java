public class Main {
    public static void main(String[] args){
        VehicleFactory factory = new ConcreteCreators.CarFactory();
        factory.manufacture();

        factory = new ConcreteCreators.BikeFactory();
        factory.manufacture();
    }
}
