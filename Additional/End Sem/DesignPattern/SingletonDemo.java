import java.sql.*;

public class SingletonDemo {
    private static SingletonDemo instance = null;
    public static int instanceCount = 0;
    public static int instanceRequestCount = 0;

    private SingletonDemo() {
    }

    public static SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
            instanceCount++;
        }
        instanceRequestCount++;
        return instance;
    }

    public static void main(String[] args) {
        SingletonDemo inst1 = SingletonDemo.getInstance();
        System.out.println("Instance Count: " + SingletonDemo.instanceCount);
        SingletonDemo inst2 = SingletonDemo.getInstance();
        System.out.println("Instance Count: " + SingletonDemo.instanceCount);
        System.out.println("Instance Request Count: " + SingletonDemo.instanceRequestCount);
        System.out.println(inst1 == inst2);
    }
}
