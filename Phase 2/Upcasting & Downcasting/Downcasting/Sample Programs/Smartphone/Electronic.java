class Device {
    String brand;
    String model;
    double price;
    boolean powerStatus;
    String OS;

    Device(String brand, String model, double price, String OS) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.OS = OS;
        this.powerStatus = false;
    }

    void powerOn() {
        powerStatus = true;
        System.out.println(brand + "(" + model + ")" + " is powered ON.");
    }

    void powerOff() {
        powerStatus = false;
        System.out.println(brand + "(" + model + ")" + " is powered OFF.");
    }
}

class Smartphone extends Device {
    String AI;

    Smartphone(String brand, String model, double price, String OS, String AI) {
        super(brand, model, price, OS);
        this.AI = AI;
    }

    void installApp(String appName) {
        System.out.println(appName + " installed on " + brand + "(" + model + ") successfully.");
    }
}

public class Electronic {
    public static void main(String[] args) {
        Device d = new Smartphone("Samsung", "Galaxy S26 Ultra", 1505, "Android", "Copilot");
        d.powerOn();

        // d.installApp("WhatsApp"); not allowed
        Smartphone s = (Smartphone) d;
        s.installApp("WhatsApp");
        s.installApp("Instagram");
    }

}
