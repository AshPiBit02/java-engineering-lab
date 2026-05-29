package auth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class validate {
    HashMap<String, String> credentials;

    public validate(String username, String password, boolean authorize) { // true for authorization and false for
                                                                           // authentication(valid user)

        String filePath = "DataFiles/user_pass.csv";
        credentials = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String user = parts[0].trim();
                    String pass = parts[1].trim();
                    credentials.put(user, pass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (authorize) {
            isAuthorized(username, password);
        } else {
            isValid(username);
        }

    }

    private boolean isValid(String username) {
        if (credentials.containsKey(username)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isAuthorized(String username, String password) {
        if (isValid(username) && credentials.get(username).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

}
