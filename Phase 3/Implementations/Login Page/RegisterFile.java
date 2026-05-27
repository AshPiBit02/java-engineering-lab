import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterFile {
    private static final String ID_FILE = "DataFiles/id.txt";
    private static final String REGISTER_FILE = "DataFiles/registeredUsers.csv";

    RegisterFile(String fullname, String Username, String email, String password, String Cpassword) {
        try {
            // Auto User Id
            int userId = getId();

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String timestamp = now.format(formatter);

            File regfile = new File(REGISTER_FILE);
            FileWriter writer = new FileWriter(regfile, true);
            writer.write("\n" + userId + "," + Username + "," + fullname + "," + email + "," + password + ","
                    + Cpassword + "," + timestamp);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int getId() throws IOException {
        File idfile = new File(ID_FILE);
        int id;
        if (idfile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(idfile));
            String line = reader.readLine();
            reader.close();
            id = Integer.parseInt(line.trim());
        } else {
            // If file doesn't exists, start with 37009
            id = 37009;
        }
        FileWriter writer = new FileWriter(idfile, false); // Override old id
        writer.write(String.valueOf(id + 1)); // Save new id for new Register user
        writer.close();
        return id;

    }

    public static void main(String[] args) {
        new RegisterFile("d", "d", "D", "d", "d");
    }
}
