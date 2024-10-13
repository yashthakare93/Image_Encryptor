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
            e.printStackTrace(); // Consider using a logger instead of printing stack traces
        }
    }

    // Generate AES Secret Key
    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // Set key size to 128 bits
        return keyGen.generateKey();
    }

    // Encrypt the image data using AES and return the encrypted data
    public String encryptImage(String imageData) {
        try {
            if (secretKey == null) {
                throw new IllegalStateException("Secret key not initialized.");
            }
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(imageData.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData); // Return Base64-encoded encrypted data
        } catch (Exception e) {
            e.printStackTrace(); // Log error instead of printStackTrace
            return "Encryption error: " + e.getMessage();
        }
    }

    // Decrypt the image data using the provided secret key
    public String decryptImage(String encryptedData, String base64SecretKey) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(base64SecretKey);
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, originalKey);

            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData); // Return decrypted data as string
        } catch (Exception e) {
            e.printStackTrace(); // Log error instead of printStackTrace
            return "Decryption error: " + e.getMessage();
        }
    }

    // Get the secret key as a Base64 encoded string
    public String getSecretKey() {
        if (secretKey != null) {
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        return null; // Return null if the secret key is not initialized
    }
}
