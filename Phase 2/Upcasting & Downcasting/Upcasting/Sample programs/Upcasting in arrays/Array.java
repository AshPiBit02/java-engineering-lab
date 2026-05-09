class Shape {
    void draw() {
        System.out.println("Drawing a Shape.");
    }
}

class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a Circle.");
    }
}

class Square extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a Square.");
    }
}

public class Array {
    public static void main(String[] args) {

        // Upcasting happens automatically when storing derived objects in a base class
        // array.
        Shape[] shapes = { new Circle(), new Square(), new Shape() };

        // polymorphism in action
        for (Shape s : shapes) {
            s.draw();

        }
    }

}
