package com.example.image_encryption.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageService {
    private static final String ALGORITHM = "AES";
    private SecretKey secretKey;

    // Constructor to initialize the key generator
    public ImageService() {
        try {
            this.secretKey = generateKey();
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger instead
        }
    }

    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // Key size
        return keyGen.generateKey();
    }

    public String encryptImage(String imageData) {
        try {
            if (secretKey == null) {
                throw new IllegalStateException("Secret key not initialized.");
            }
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(imageData.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace(); // Use logging instead
            return "Encryption error: " + e.getMessage();
        }
    }

    public String decryptImage(String encryptedData) {
        try {
            if (secretKey == null) {
                throw new IllegalStateException("Secret key not initialized.");
            }
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace(); // Use logging instead
            return "Decryption error: " + e.getMessage();
        }
    }

    public String getSecretKey() {
        if (secretKey != null) {
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        return null;
    }
}
