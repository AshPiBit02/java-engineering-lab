import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.io.FileInputStream;

public class ConfigManager implements Serializable {
    private static ConfigManager instance = null;
    private java.util.Properties properties;

    private ConfigManager() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected Object readResolve() {
        return getInstance();
    }

    public static void main(String[] args) {
        ConfigManager config = ConfigManager.getInstance();
        System.out.println("Database URL: " + config.getProperty("db.url"));
        System.out.println("API Key: " + config.getProperty("api.key"));
    }

}
