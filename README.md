# Image Encryption and Decryption

## Overview
This project allows users to encrypt and decrypt images using a secure algorithm. It provides a simple and user-friendly interface for image upload, encryption, and decryption. Users can obtain encrypted data and a secret key, which are essential for the decryption process.


## Technologies Used

### Frontend
- **HTML**: Structure of the web application.
- **CSS (Tailwind CSS)**: Styling of the application for a modern and responsive design.
- **JavaScript**: Handling user interactions and API requests.

### Backend
- **Java**: Main programming language for backend logic.
- **Spring Boot**: Framework for building the RESTful API for encryption and decryption operations.
- **Maven**: Dependency management.

## Installation

### Prerequisites
- Java JDK 17
- Maven
- A web browser

### Clone the Repository
```bash
git clone https://github.com/yashthakare93/Image_Encryptor.git
```

### Run the Backend
Navigate to the backend directory of the cloned repository and Run the application using Maven :
```
cd Image_Encryptor/src/main
mvn spring-boot:run
```
Ensure that the backend server is running on http://localhost:8080.

### Block Diagram 
```
[User Interface]
        |
        V
[Image Upload Form] ---> [Encrypt Image] ---> [Backend API]
        |                                   |         |
        |                                   |         |
        V                                   V         V
[Encrypted Data & Secret Key] <--- [Decryption Request] <--- [Decrypted Image]

```
