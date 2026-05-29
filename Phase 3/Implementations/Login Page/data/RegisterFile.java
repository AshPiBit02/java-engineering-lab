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
            String userId = GetUserid.getCommonUserId(Username, fullname, email);

            String hashedPassword = GetUserid.getHash(password); // Let exception propagate
            String hashedUserId = GetUserid.getHash(Username);

            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            // Ensure directory exists
            new File("DataFiles").mkdirs();

            try (FileWriter writer = new FileWriter(REGISTER_FILE, true)) {
                writer.write("\n" + timestamp + "," + userId + "," + Username + ","
                        + fullname + "," + email + "," + hashedPassword);
            }

            try (FileWriter userpassWriter = new FileWriter(USER_PASS, true)) {
                userpassWriter.write("\n" + hashedUserId + "," + hashedPassword);
            }

            // Stores registration log in log file
            new LogFile(Username, "Registration");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
