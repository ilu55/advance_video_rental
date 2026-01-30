# 🎬 Video Rental System – Spring Boot API

A **robust RESTful API** for managing a Video Rental platform, built with **Spring Boot**, **JWT-based stateless authentication**, and **role-based authorization**.  
This project enforces real-world business rules such as rental limits and video availability automatically.

---

## 👨‍💻 Author

**Ilyas Meman**  
📧 Email: [memonilyas786@gmail.com](mailto:memonilyas786@gmail.com)

---

## 🛠️ Key Features

- 🔐 **JWT Authentication**
  - Stateless authentication using JSON Web Tokens
  - Secure request filtering with custom JWT filters

- 🔑 **Password Security**
  - BCrypt hashing for all user passwords

- 👥 **Role-Based Access Control**
  - **ADMIN**
    - Add, update, delete video inventory
  - **CUSTOMER**
    - Rent and return videos

- 📜 **Business Rules**
  - Maximum **2 active rentals per customer**
  - Automatic video availability updates on rent/return

- 📘 **API Documentation**
  - Integrated **Swagger UI** for easy API testing

---

## 🏗️ Project Architecture

The application follows a clean **layered architecture**:

```
controller   →  Handles HTTP requests & responses
service      →  Business logic and validations
repository   →  Database operations (Spring Data JPA)
security     →  JWT filters, security configuration
model/entity →  JPA entities
dto          →  Request & response payloads
```


---

## 📂 Project Structure

```text
video-rental-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ilu55/videorental/
│   │   │       ├── config/
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── SwaggerConfig.java
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   └── VideoController.java
│   │   │       ├── dto/
│   │   │       │   ├── UserLoginDto.java
│   │   │       │   └── UserRegistrationDto.java
│   │   │       ├── entity/
│   │   │       │   ├── User.java
│   │   │       │   ├── Video.java
│   │   │       │   └── Rental.java
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── repository/
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── VideoRepository.java
│   │   │       │   └── RentalRepository.java
│   │   │       ├── security/
│   │   │       │   ├── JwtAuthFilter.java
│   │   │       │   └── JwtService.java
│   │   │       ├── service/
│   │   │       │   ├── UserService.java
│   │   │       │   ├── RentalService.java
│   │   │       │   └── CustomUserDetailsService.java
│   │   │       └── VideoRentalApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
├── build.gradle
└── README.md


1. config/: Contains classes that define how the application is built—this is where you permit public access to Swagger and set the security chain to be Stateless.

2. controller/: The "Gatekeepers" of your API. They accept the JSON from Postman/Curl and pass it to the service.

3. dto/: Simple classes to protect your Entities. They ensure only the necessary fields (like Email/Password) are passed during login.

4. entity/: These represent your tables in MySQL.

5. security/: This is where the JWT magic happens. It intercepts every request to see if the user has a valid Bearer token.

6. service/: This is where the 2-video rental limit is checked. Controllers call these methods to execute the actual "work."


---

## 🚀 API Endpoints

### 🔐 Authentication (Public)

| Method | Endpoint        | Description              |
|------:|-----------------|--------------------------|
| POST  | `/api/register` | Register a new user      |
| POST  | `/api/login`    | Authenticate and get JWT |

---

### 📽️ Videos & Rentals (Secured)

| Method | Endpoint                  | Access Role |
|------:|---------------------------|-------------|
| GET   | `/api/videos`             | All Users   |
| POST  | `/api/videos`             | ADMIN       |
| POST  | `/api/videos/{id}/rent`   | CUSTOMER    |
| POST  | `/api/videos/{id}/return` | CUSTOMER    |

---

## ⚙️ Setup & Installation

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/ilu55/advance_video_rental.git
cd advance_video_rental
```

### 2️⃣ Database Configuration

Update **`src/main/resources/application.properties`**:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/video_rental_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Build & Run the Application

```bash
./gradlew bootRun
```

---

## 📘 Swagger API Documentation

Once the application is running, access Swagger UI at:

```
http://localhost:8081/swagger-ui/index.html
```

Use Swagger to:
- Authenticate using JWT
- Test secured endpoints
- Explore request/response models

---

## 🛡️ Security Implementation

- A custom **JwtAuthFilter** intercepts every request
- Extracts token from:
  ```
  Authorization: Bearer <token>
  ```
- Validates the token and sets the `SecurityContext`
- Ensures role-based access to secured endpoints

---

## 📌 Project Purpose

Developed as part of a **Video Rental Management System** requirement, demonstrating:
- Secure REST API design
- Spring Security with JWT
- Clean architecture & best practices
 

