package com.example.image_encryption.model;

public class ImageModel {
    private String encryptedData;
    private String secretKey;

    public ImageModel(String encryptedData, String secretKey) {
        this.encryptedData = encryptedData;
        this.secretKey = secretKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
