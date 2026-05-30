package auth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class validate {
    HashMap<String, String> credentials;
    HashMap<String, String> credentials2;

    public validate() {
        String filePath = "DataFiles/registeredUsers.csv";
        credentials = new HashMap<>();
        credentials2 = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String user = parts[2].trim();
                    String pass = parts[5].trim();
                    credentials.put(user, pass);
                    String firstname = parts[3].trim();
                    int endIdx = firstname.indexOf(" ");
                    credentials2.put(user, endIdx != -1 ? firstname.substring(0, endIdx) : firstname);
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

    public String getfirstname(String username) {
        return credentials2.get(username);

    }

}
