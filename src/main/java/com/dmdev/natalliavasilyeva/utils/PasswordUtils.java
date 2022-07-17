package com.dmdev.natalliavasilyeva.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class PasswordUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    private  PasswordUtils() {
    }

    public static String generateHash(String login, String password) {
        byte[] salt = login.getBytes();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] encoded = factory.generateSecret(spec).getEncoded();
            return new String(encoded, UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e.getMessage());
        }
        throw new RuntimeException();
    }
}