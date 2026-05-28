// package datasec;

import java.security.MessageDigest;
import java.math.BigInteger;

public class GetUserid {
    public static String getCommonUserId(String username, String fullname, String email) {
        // get index of space so that we can access the first char of lastname if any.
        int spaceIdx = fullname.lastIndexOf(" ");
        int eIdx = email.indexOf("@");
        char Fname = fullname.charAt(0);

        char LnameChar = 0;
        String Lemail = null;
        if (spaceIdx != -1) {
            LnameChar = fullname.charAt(spaceIdx + 1);
        }
        if (eIdx != -1) {
            int startIdx = eIdx - 2;
            Lemail = email.substring(startIdx, eIdx);
        }
        String nameChars = "" + Fname + LnameChar;

        nameChars = nameChars.toLowerCase(); // string of chars from first and last name
        String hash = null;
        try {
            hash = getUserId(username).substring(0, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userid = nameChars + hash + Lemail;

        return userid;

    }

    public static String getUserId(String input) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(input.getBytes());

        BigInteger number = new BigInteger(1, digest);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();

    }

    public static void main(String[] args) {
        String UserId = getCommonUserId("ashpibit", "Aashish K Chaudhary", "aashishchaudhari249@gmail.com");
        System.out.println("User ID: " + UserId);
    }

}
