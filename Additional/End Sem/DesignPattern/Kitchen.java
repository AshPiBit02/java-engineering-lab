interface Food {
    void cook();

    void serve();
}

class Rice implements Food {
    public void cook() {
        System.out.println("Cooking Rice...");
    }

    public void serve() {
        System.out.println("Serving Rice...");
    }
}

class Lentils implements Food {
    public void cook() {
        System.out.println("Cooking Lentils...");
    }

    public void serve() {
        System.out.println("Serving Lentils...");
    }
}

class Vegies implements Food {
    public void cook() {
        System.out.println("Cooking vegies...");
    }

    public void serve() {
        System.out.println("Serving Vegies...");
    }
}

class Chief {
    public Food getFood(String type) {
        if (type.equalsIgnoreCase("rice")) {
            return new Rice();
        } else if (type.equalsIgnoreCase("lentils")) {
            return new Lentils();
        } else if (type.equalsIgnoreCase("vegies")) {
            return new Vegies();
        }
        return null;
    }
}

public class Kitchen {
    public static void main(String[] args) {
        Chief chief = new Chief();
        Food rice = chief.getFood("rice");
        rice.cook();
        rice.serve();
        Food lentils = chief.getFood("rice");
        lentils.cook();
        lentils.serve();
        Food vegies = chief.getFood("rice");
        vegies.cook();
        vegies.serve();

    }
}
