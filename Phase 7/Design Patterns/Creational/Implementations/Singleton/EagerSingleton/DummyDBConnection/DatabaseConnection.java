public class DatabaseConnection {
    private static final DatabaseConnection instance = new DatabaseConnection();
    private String connectionUrl;

    private DatabaseConnection() {
        connectionUrl = "jdbc:postgresql://localhost:5432/java_crud";
        System.out.println("Database Connection instance created!");
    }

    public static DatabaseConnection getInstance() {
        return instance;
    }

    public void connect() {
        System.out.println("Connected to: " + connectionUrl);
    }
}
