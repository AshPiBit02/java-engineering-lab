interface House {
    void buildHouse();
}

class ModernHouse implements House {
    public void buildHouse() {
        System.out.println("Building modern house...");
    }
}

class TraditionalHouse implements House {
    public void buildHouse() {
        System.out.println("Building traditional house...");
    }
}

class HouseFactory {
    public House getHouse(String type) {
        if (type.equalsIgnoreCase("modern")) {
            return new ModernHouse();
        } else if (type.equalsIgnoreCase("traditional")) {
            return new TraditionalHouse();
        }
        return null;
    }
}

public class HouseBuild {
    public static void main(String[] args) {
        HouseFactory factory = new HouseFactory();
        House modern = factory.getHouse("modern");
        modern.buildHouse();
        House traditional = factory.getHouse("traditional");
        traditional.buildHouse();
    }

}
