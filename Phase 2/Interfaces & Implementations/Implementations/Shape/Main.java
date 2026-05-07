interface Shape{
    double calculateArea();
    double calculatePerimeter();
}
class Circle implements Shape{
    private double radius;

    public Circle(double radius){
        this.radius=radius;
    }

    @Override
    public double calculateArea(){
        return Math.PI*radius*radius;
    }
    @Override
    public double calculatePerimeter(){
        return 2*Math.PI*radius;
    }
}

class Rectangle implements Shape{
    private double length;
    private double width;
    
    public Rectangle(double length,double width){
        this.length=length;
        this.width=width;
    }
    @Override
    public double calculateArea(){
        return length*width;
    }
    @Override
    public double calculatePerimeter(){
        return 2*(length+width);
    }
    
}
public class Main {
    public static void main(String[] args){
        Circle circle=new Circle(5.0);
        Rectangle rectangle=new Rectangle(4.0,6.0);
        System.out.println("-------------- Circle -------------");
        System.out.printf("Area of Circle: %.2f sq units.%n",circle.calculateArea());
        System.out.printf("Perimeter of Circle: %.2f sq units.%n",circle.calculatePerimeter());
        System.out.println("-------------- Rectangle -------------");
        System.out.printf("Area of Rectangle: %.2f sq units.%n",circle.calculateArea());
        System.out.printf("Area of Rectangle: %.2f sq units.%n",circle.calculatePerimeter());
    
    }
}
