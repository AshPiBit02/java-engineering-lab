import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterFile {
    RegisterFile(String fullname, String Username, String email, String password, String Cpassword) {
        try {
            File regfile = new File("DataFiles/registeredUsers.csv");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String timestamp = now.format(formatter);

            FileWriter writer = new FileWriter(regfile, true);
            writer.write("\nUserId,Username,Full Name,Email,Password,Confirm Password,Registered At");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new RegisterFile("Full Name", "Username", "Email", "Password", "Confirm Password");
    }
}
