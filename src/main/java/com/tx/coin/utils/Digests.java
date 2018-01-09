package com.tx.coin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 加密实现
 * Created by guotx on 2017/10/12.
 */

public class Digests {
    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";
    private static SecureRandom random = new SecureRandom();

    public Digests() {
    }

    public static byte[] sha1(byte[] input) {
        return digest(input, "SHA-1", (byte[])null, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, "SHA-1", salt, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, "SHA-1", salt, iterations);
    }

    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest e = MessageDigest.getInstance(algorithm);
            if(salt != null) {
                e.update(salt);
            }

            byte[] result = e.digest(input);

            for(int i = 1; i < iterations; ++i) {
                e.reset();
                result = e.digest(result);
            }

            return result;
        } catch (GeneralSecurityException var7) {
            return null;
        }
    }

    public static byte[] generateSalt(int numBytes) {
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, "MD5");
    }

    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, "SHA-1");
    }

    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest e = MessageDigest.getInstance(algorithm);
            short bufferLength = 8192;
            byte[] buffer = new byte[bufferLength];

            for(int read = input.read(buffer, 0, bufferLength); read > -1; read = input.read(buffer, 0, bufferLength)) {
                e.update(buffer, 0, read);
            }

            return e.digest();
        } catch (GeneralSecurityException var6) {
            return null;

        }
    }
}
