# SecureVault API
![Java](https://img.shields.io/badge/Java-25-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue)
![Status](https://img.shields.io/badge/Status-In_Development-yellow)

SecureVault is an enterprise-grade backend authentication and credential management engine built using modern Java architecture. The system provides secure, stateless API endpoints for managing users, organizations, vaults, and encrypted secrets.

## 🛠️ Technology Stack & Architecture

* **Language Runtime:** Java 25 (LTS)
* **Core Framework:** Spring Boot 4.0.x (Spring Security, Spring Data JPA)
* **Database:** PostgreSQL 15 (Alpine-based, isolated container volume)
* **Orchestration & Containerization:** Docker & Multi-stage Docker Compose
* **Security Layer:** Stateless JWT (JSON Web Tokens), BCrypt Password Hashing

---

## 🔒 Key Security & Infrastructure Features

* **Multi-Stage Build Pipeline:** Utilizes a lightweight Maven Alpine image to compile binaries safely inside an isolated container stage, keeping the final runtime image clean, minimal, and secure.
* **Deterministic Service Orchestration:** Implements PostgreSQL container health checks (`pg_isready`) combined with strict startup conditions (`service_healthy`) to completely prevent application boot crashes while the database initializes.
* **Dual-Configuration Environment:** Built-in separation between local host compilation (for high-speed IDE debugging) and isolated multi-container production routing.

---

## 📂 Project Structure

```text
SecureVaultProject/
├── Dockerfile
├── docker-compose.yml
├── .gitignore
├── README.md
└── api/
    └── api/
        ├── src/              # Main Java source code
        └── pom.xml          # Maven dependencies
```

---

## 🚀 How to Run the Project
Prerequisites
Make sure you have Docker and Docker Compose installed and running on your system.

### Option A: Full Production Mode (Completely Containerized)
Use this mode if you just want to spin up and test the full API ecosystem without installing Java or PostgreSQL on your host system:

1. Clone the repository:
```bash
   git clone [https://github.com/YOUR_USERNAME/SecureVaultProject.git](https://github.com/YOUR_USERNAME/SecureVaultProject.git)
   cd SecureVaultProject
```
2. Launch the entire multi-container architecture hands-free:
```bash
  docker compose up --build
```
The engine will compile the source, check the database health, provision schemas, and expose the application on http://localhost:8080.

### Option B: Local Development Mode (IntelliJ IDE + Docker Database)
Use this mode if you are actively editing Java code in your IDE and want fast compilation loops:

1. Spin up only the isolated database container via your terminal:
```bash
    docker compose up postgres-db
```
2. Click the green Run button for ApiApplication inside IntelliJ. The IDE will claim port 8080 on your host machine and seamlessly connect to the containerized database on port 5432.

---

## 🛣️ Core API Endpoints

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/users/register` | Register a fresh user profile | No |
| **POST** | `/api/users/login` | Authenticate and obtain JWT token | No |
| **GET** | `/api/users/{id}` | Retrieve specific user profile details by their unique database ID | Yes (JWT) |
| **POST** | `/api/organizations` | Create a new tenant organization owned by the authenticated user | Yes (JWT) |
| **GET** | `/api/organizations` | Retrieve all tenant organizations associated with the authenticated user | Yes (JWT) |
| **POST** | `/api/vaults` | Create a secure data vault inside an organization | Yes (JWT) |
| **POST** | `/api/secrets` | Encrypt and store a new password payload | Yes (JWT) |
| **GET** | `/api/secrets` | Retrieve authorized vault items | Yes (JWT) |
| **PUT** | `/api/secrets/{id}` | Update an existing encrypted secret's key or value payload by its unique ID | Yes (JWT) |
| **DELETE** | `/api/secrets/{id}` | Permanently remove a specific encrypted secret payload by its unique ID | Yes (JWT) |
