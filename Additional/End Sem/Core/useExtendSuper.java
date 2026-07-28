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
    Child(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    void intro() {
        super.intro();
        System.out.println("Son: My self " + firstName + " " + lastName + " Jr.");

    }
}

public class useExtendSuper {
    public static void main(String[] args) {
        Child c = new Child("Walter", "White");
        c.intro();
    }
}