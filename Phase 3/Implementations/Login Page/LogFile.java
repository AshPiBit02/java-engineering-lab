import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFile {
    LogFile(String user, String password) {

        try {
            File logfile = new File("DataFiles/logs.csv");
            if (logfile.createNewFile()) {
                System.out.println(logfile.getName() + "Created!");
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String timestamp = now.format(formatter);

            FileWriter writer = new FileWriter(logfile, true);// true=append mode
            writer.write("\n" + timestamp + "," + user + "," + password);
            writer.close();
            System.out.println("LogFile Updated!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
