package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFile {
    public LogFile(String user, String opertion) {

        try {
            File logfile = new File("DataFiles/logs.csv");
            if (logfile.createNewFile()) {
                System.out.println(logfile.getName() + "Created!");
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String timestamp = now.format(formatter);

            FileWriter writer = new FileWriter(logfile, true);// true=append mode
            writer.write("\n" + timestamp + "," + user + "," + opertion);
            writer.close();
            System.out.println("LogFile Updated!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
