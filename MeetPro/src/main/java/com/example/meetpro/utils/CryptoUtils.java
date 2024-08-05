package com.example.meetpro.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {
    public static String hashSah512(String input) {
        try {
            StringBuilder passwordSaltHashBuilder = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(input.getBytes(StandardCharsets.UTF_8));
            for (byte hashByte : md.digest()) {
                passwordSaltHashBuilder.append(String.format("%02x", hashByte));
            }
            return passwordSaltHashBuilder.toString();
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    private CryptoUtils() {

    }
}
