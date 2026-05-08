class Animal {
    void Animal_sound() {
        System.out.println("Generic sound!");
    }
}

class Dog extends Animal {
    void Dog_sound() {
        System.out.println("Woof!");
    }

    void fetch() {
        System.out.println("Dog properties fetched!");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = (Dog) new Dog(); // a is Dog's object referencing Animal class
        a.Animal_sound();

        // Even though below methods are of Dog, we cannot access them with Dog's object
        // due to referencing to Animal class by that object.
        // a.Dog_sound();
        // a.fetch();
    }

}
