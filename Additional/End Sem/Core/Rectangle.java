public class Rectangle {
    float length;
    float breadth;

    void setData(float len, float bdh) {
        length = len;
        breadth = bdh;
    }

    void displayArea() {
        System.out.printf("Area of rectangle is %.2f unit(s) sq.", length * breadth);
    }

    public static void main(String[] args) {
        Rectangle rect = new Rectangle();
        rect.getData(20, 3.5f);
        rect.displayArea();
    }
}