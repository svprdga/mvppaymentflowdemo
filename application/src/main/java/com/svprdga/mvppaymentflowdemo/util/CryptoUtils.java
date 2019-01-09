package com.svprdga.mvppaymentflowdemo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {

    /**
     * Encrypt a string with MD5.
     * @param value Value to encrypt.
     * @return Encrypted value.
     */
    public final String encryptToMd5(final String value) throws NoSuchAlgorithmException {

        // Create MD5 Hash
        MessageDigest digest = java.security.MessageDigest
                .getInstance("MD5");
        digest.update(value.getBytes());
        byte[] messageDigest = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < messageDigest.length; i++) {
            String h = Integer.toHexString(0xFF & messageDigest[i]);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString();
    }

}
