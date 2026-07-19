import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Properties;

public class ConfigManager2 implements Serializable {
    private static ConfigManager2 instance = null;
    private static Properties properties;

    private ConfigManager2() {
        properties = new Properties();
    }

    public static ConfigManager2 getInstance() {
        if (instance == null) {
            instance = new ConfigManager2();
        }
        return instance;
    }

    public static void loadProperties() {
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void storeProperties(OutputStream out, String comments) {
        try {
            properties.store(out, comments);
            System.out.println("Properties Stored successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected Object readResolve() {
        return getInstance();
    }

    public static void main(String[] args) {
        ConfigManager2 config = ConfigManager2.getInstance();
        config.loadProperties();
        System.out.println("Database Password: " + config.getProperty("db.password"));
    }
}
