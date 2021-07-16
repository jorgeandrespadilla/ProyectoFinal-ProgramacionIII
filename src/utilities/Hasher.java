package utilities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jorge Padilla https://www.geeksforgeeks.org/sha-256-hash-in-java/
 */
public class Hasher {

    private Hasher() {
        throw new UnsupportedOperationException();
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        return  MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
        // Convert byte array into signum representation 
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value 
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static String convert(String input) throws NoSuchAlgorithmException {
        byte[] hash = getSHA(input);
        return toHexString(hash);
    }
}
