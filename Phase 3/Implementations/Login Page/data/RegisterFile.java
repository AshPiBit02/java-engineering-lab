package data;

import datasec.GetUserid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterFile {
    private static final String REGISTER_FILE = "DataFiles/registeredUsers.csv";
    private static final String USER_PASS = "DataFiles/user_pass.csv";

    public RegisterFile(String fullname, String Username, String email, String password) {
        try {

            // Generate unique userId for UserEnd
            String userId = GetUserid.getCommonUserId(Username, fullname, email);

            String hashedPassword = null;
            try {
                hashedPassword = GetUserid.getHash(password);
            } catch (Exception e) {
                e.printStackTrace();
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String timestamp = now.format(formatter);

            File regfile = new File(REGISTER_FILE);
            FileWriter writer = new FileWriter(regfile, true);
            writer.write(
                    "\n" + timestamp + "," + userId + "," + Username + "," + fullname + "," + email + ","
                            + hashedPassword);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
