# 🎬 VideoRental API

A **robust, secure, and scalable Spring Boot REST API** for a Video Rental platform.  
This project implements **User Authentication**, **Role-Based Access Control (RBAC)**, and **auto-generated Swagger API documentation** following modern backend best practices.

---

## ✨ Features

- 🔐 Secure user registration & login
- 🧑‍💼 Role-based access control (USER / ADMIN)
- 🔑 BCrypt password encryption
- 📚 Video catalog management
- 📄 Interactive Swagger UI
- 🏗 Clean layered architecture

---

## 🏗️ Project Architecture

The application follows a **standard layered architecture** to ensure maintainability, scalability, and clear separation of concerns.

```
Controller → Service → Repository → Database
                 ↓
              Security
```

### 🔹 Layer Breakdown
- **Controller Layer**  
  Handles incoming REST API requests and responses.
- **Service Layer**  
  Contains business logic such as user registration, login validation, and role handling.
- **Repository Layer**  
  Manages database operations using Spring Data JPA.
- **Security Layer**  
  Implements authentication, authorization, and password encryption using Spring Security and BCrypt.

---

## 🛠️ Tech Stack

| Technology | Description |
|-----------|------------|
| **Java 21** | Modern Java features |
| **Spring Boot 3.4.2** | Application framework |
| **Spring Security** | Authentication & RBAC |
| **MySQL** | Relational database |
| **Spring Data JPA** | ORM & persistence |
| **SpringDoc OpenAPI** | Swagger documentation |
| **Lombok** | Boilerplate reduction |

---

## 🚦 API Endpoints & Access Control

### 🔓 Public Endpoints

| Method | Endpoint | Description |
|------|---------|------------|
| **POST** | `/api/register` | Register a new user (email & password validation) |
| **POST** | `/api/login` | Authenticate user using JSON body |
| **GET** | `/swagger-ui/**` | Swagger UI documentation |

---

### 🔒 Protected Endpoints

| Method | Endpoint | Access | Description |
|------|---------|--------|------------|
| **GET** | `/api/protected` | Authenticated | Test secured endpoint |
| **GET** | `/api/videos` | Authenticated | View video catalog |
| **POST** | `/api/videos` | **ROLE_ADMIN** | Add new videos to inventory |

---

## 📖 API Documentation (Swagger)

Swagger UI allows you to explore and test APIs without Postman.

- 🔗 **Swagger UI**  
  `http://localhost:8081/swagger-ui/index.html`

---

## 🚀 Getting Started

### 1️⃣ Database Configuration

Create a MySQL database named **`rent_video_db`**  
Then update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rent_video_db?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

### 2️⃣ Build & Run the Application

```bash
# Clean previous builds and package the project
./gradlew clean build -x test

# Run the Spring Boot application
./gradlew bootRun
```

The server will start at:  
📍 `http://localhost:8081`

---

## 🧪 API Testing Examples

### 📝 User Registration

```bash
curl -X POST http://localhost:8081/api/register \
-H "Content-Type: application/json" \
-d '{
  "email": "iliyas@example.com",
  "password": "securePassword123",
  "firstName": "Iliyas",
  "lastName": "Meman"
}'
```

---

### 🔐 User Login

```bash
curl -X POST http://localhost:8081/api/login \
-H "Content-Type: application/json" \
-d '{
  "email": "iliyas@example.com",
  "password": "securePassword123"
}'
```

---

## 👨‍💻 Author

**Iliyas Meman**  
📧 Email: [memonilyas786@gmail.com](mailto:memonilyas786@gmail.com)

---

## 📌 Future Enhancements

- 🔑 JWT-based authentication
- 🐳 Docker & Docker Compose support
- 📊 Pagination & filtering for videos
- 🧪 Unit & integration tests
- ☁️ Deployment on AWS / Render

---

⭐ If you found this project helpful, consider giving it a star!
