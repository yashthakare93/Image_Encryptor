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


    @PostMapping("/encrypt")
    public ResponseEntity<?> encryptImage(@RequestParam("imageData") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty. Please upload a valid image.");
        }

        try {
            byte[] imageBytes = file.getBytes();  // Handle the image as binary data
            String base64Image = Base64.getEncoder().encodeToString(imageBytes); // Convert byte[] to base64 encoded String
            String encryptedData = imageService.encryptImage(base64Image); // Pass Base64 string to encryptImage
            String secretKey = imageService.getSecretKey();

            ImageModel response = new ImageModel(encryptedData, secretKey);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encrypting image: " + e.getMessage());
        }
    }

    @GetMapping("/result")
    public ResponseEntity<?> getResult(@RequestParam("encryptedData") String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Encrypted data is missing or invalid.");
        }

        try {
            // Decrypt the data (assuming you have a decryptImage method)
            String decryptedData = imageService.decryptImage(encryptedData);
            return ResponseEntity.ok(decryptedData);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the result: " + e.getMessage());
        }
    }
}
