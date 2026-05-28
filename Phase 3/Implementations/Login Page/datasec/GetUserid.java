// package datasec;

import java.security.MessageDigest;
import java.math.BigInteger;

public class GetUserid {
    public GetUserid(String username, String fullname, String email) {
        int spaceIndex = fullname.lastIndexOf(" "); // get index of space so that we can access the first char of last
                                                    // name if any.
        if (spaceIndex != -1) {
            char LnameChar = fullname.charAt(spaceIndex + 1);
            System.out.println(LnameChar);
        }

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
        new GetUserid("ddafa", "Aashish Chaudhary", "fasdfa");
        try {
            String hash = getUserId("Aashish");
            System.out.println(hash);
        } catch (Exception e) {

        }
    }

}
