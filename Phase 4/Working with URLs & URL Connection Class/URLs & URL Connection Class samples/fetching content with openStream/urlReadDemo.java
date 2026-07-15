import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class urlReadDemo {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://raw.githubusercontent.com/AshpiBit02/AshPiBit02/master/README.md");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), java.nio.charset.StandardCharsets.UTF_8));
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                lineCount++;
            }
            reader.close();
            System.out.println("\n--- Total lines read: " + lineCount);
        } catch (IOException e) {
            System.out.println("Error fetching URL: " + e.getMessage());
        }
    }

}