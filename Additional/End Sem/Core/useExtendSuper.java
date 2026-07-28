class Parent {
    String firstName;
    String lastName;

    Parent(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    void intro() {
        System.out.println("Father: My self " + firstName + " " + lastName);
    }
}

class Child extends Parent {
    String CfirstName;

    Child(String CfirstName, String FfirstName, String lastName) {
        super(FfirstName, lastName);
        this.CfirstName = CfirstName;
    }

    @Override
    void intro() {
        super.intro();
        System.out.println("Son: My self " + CfirstName + " " + lastName);

    }
}

public class useExtendSuper {
    public static void main(String[] args) {
        Child c = new Child("Son", "Father", "Willow");
        c.intro();
    }
}