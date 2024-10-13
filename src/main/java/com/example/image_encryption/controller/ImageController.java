package com.example.image_encryption.controller;

import java.util.Base64;

import com.example.image_encryption.model.ImageModel;
import com.example.image_encryption.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Endpoint to encrypt the image
    @PostMapping("/encrypt")
    public ResponseEntity<?> encryptImage(@RequestParam("imageData") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"File is empty. Please upload a valid image.\"}");
        }

        try {
            byte[] imageBytes = file.getBytes();  // Convert image file to bytes
            String base64Image = Base64.getEncoder().encodeToString(imageBytes); // Convert to Base64
            String encryptedData = imageService.encryptImage(base64Image); // Encrypt the image
            String secretKey = imageService.getSecretKey(); // Get the secret key

            // Create response model with encrypted data and secret key
            ImageModel response = new ImageModel(encryptedData, secretKey);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error encrypting image: " + e.getMessage() + "\"}");
        }
    }

    // Endpoint to decrypt the image
    @PostMapping("/decrypt")
    public ResponseEntity<?> decryptImage(@RequestBody ImageModel imageModel) {
        if (imageModel.getEncryptedData() == null || imageModel.getEncryptedData().isEmpty() ||
                imageModel.getSecretKey() == null || imageModel.getSecretKey().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Encrypted data or secret key is missing.\"}");
        }

        try {
            // Decrypt the image using the encrypted data and secret key
            String decryptedData = imageService.decryptImage(imageModel.getEncryptedData(), imageModel.getSecretKey());
            return ResponseEntity.ok("{\"decryptedData\": \"" + decryptedData + "\"}"); // Return as JSON
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error decrypting image: " + e.getMessage() + "\"}");
        }
    }
}
