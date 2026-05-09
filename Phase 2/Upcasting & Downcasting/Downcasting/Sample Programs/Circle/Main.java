class Shape {
    void draw() {
        System.out.println("Drawing a Shape.");
    }
}

class Circle extends Shape {
    void draw() {
        System.out.println("Drawing a Circle.");
    }

    void area(double r) {
        System.out.printf("Area of Circle: %.2f sq. units.", Math.PI * r * r);
    }
}

public class Main {
    public static void main(String[] args) {
        Shape shape = new Circle();
        // shape.area(5);
        Circle circle = (Circle) shape;
        circle.area(5.3);
    }

}
