public class SingleDBConnection {
    private static SingleDBConnection instance;

    private SingleDBConnection() {
    }

    public static SingleDBConnection getDBConnection() {
        if (instance == null) {
            instance = new SingleDBConnection();
        }
        return instance;
    }

    public static void main(String[] args) {
        SingleDBConnection instance1 = SingleDBConnection.getDBConnection();
        SingleDBConnection instance2 = SingleDBConnection.getDBConnection();
        System.out.println(instance1 == instance2);
    }

}
