package auth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class validate {
    HashMap<String, String> credentials;
    HashMap<String, String> credentials2;
    String firstname = null;

    public validate(String username, String password) { // true for authorization and false for
                                                        // authentication(valid user)

        String filePath = "DataFiles/registeredUsers.csv";
        credentials = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String user = parts[2].trim();
                    String pass = parts[5].trim();
                    // String firstname=parts[]/
                    credentials.put(user, pass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(String username) {
        if (credentials.containsKey(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAuthorized(String username, String password) {
        if (isValid(username) && credentials.get(username).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

}
