// Concrete Class Extending abstract classs
abstract class Shape {
    abstract double area();
    abstract double perimeter();
    
}

class Circle extends Shape{
    double r=5;
    public double area(){
        return Math.PI*r*r;
    }
    public double perimeter(){
        return 2*Math.PI*r;
    }

    
    public static void main(String[] args){
        Circle c=new Circle();

        System.out.printf("Area of Circle: %.2f sq. unit(s).%n", c.area());
        System.out.printf("Perimeter of Circle: %.2f unit(s).%n", c.perimeter());
        
    }
}

// Circle is concrete because it implements all abstract methods from Shape.
