class Animal {
    void sound() { System.out.println("Generic sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Woof!"); }
}

class Cat extends Animal {
    @Override
    void sound() { System.out.println("Meow!"); }
}
public class Main {
    public static void main(String[] args){

        Animal a1 = new Dog();
        Animal a2 = new Cat();
        
        a1.sound();   // Woof!  ← Dog's method, resolved at runtime
        a2.sound();   // Meow!  ← Cat's method, resolved at runtime
    }
    
        
}
