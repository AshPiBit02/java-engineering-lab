import java.io.Serializable;

public class LazySingleton2 implements Serializable {
    public static int instanceCount = 0;
    private static LazySingleton2 instance = null;

    private LazySingleton2() {
        instanceCount++;
    }

    public static LazySingleton2 getInstance() {
        if (instance == null) {
            instance = new LazySingleton2();
        }
        return instance;
    }

    public static void main(String[] args) {
        LazySingleton2 instance1 = getInstance();
        System.out.println("Instance Count: " + instance1.instanceCount);
        LazySingleton2 instance2 = getInstance();
        System.out.println("Instance Count: " + instance2.instanceCount);
    }

}
