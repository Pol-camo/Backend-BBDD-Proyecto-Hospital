# 🏥 Hospital Back-end Project

REST API built with Spring Boot that provides the server side of a hospital management system, focused on managing nurse information, authentication, and access control. Data is persisted in a MySQL database through Spring Data JPA (Hibernate).

---

## 📖 About the project

This project is the back-end of a hospital application developed as part of the Introduction to Back-end module. It exposes a set of endpoints under /nurse that allow clients to:
- Register new nurses (/nurse/add)
- List all registered nurses (/nurse/index)
- Validate a nurse's login credentials (/nurse/login)
- Search for a nurse by name or username (/nurse/name/{name})

The project follows standard Spring Boot conventions (Controllers, Entities, Repositories, and DTOs) and includes automated workflows and integration testing.

---

## 🛠️ Tech stack

| Technology | Version / Usage |
| :--- | :--- |
| Java | 17 |
| Spring Boot | 4.1.1 |
| Spring Web MVC | REST controllers returning JSON |
| Spring Data JPA | Persistence and data access (Hibernate) |
| MySQL | Relational Database (mysql-connector-j driver) |
| Maven | Dependency management and build (mvnw wrapper) |
| Testcontainers | Integration tests against a real containerized database |
| Eclipse IDE | Development environment |
| Workflows / Automation | Automated verification and test execution flows |
| Git & GitHub | Version control |

---

## 📋 Prerequisites

- JDK 17 or higher
- A running MySQL instance (local or remote)
- Eclipse IDE with Maven support (or any compatible IDE)
- Docker (required to run Testcontainers integration tests)

---

## 🗄️ Database Configuration

1. Create the database in MySQL:
   CREATE DATABASE hospital;

2. Configure your connection details in src/main/resources/application.properties:
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital
   spring.datasource.username=YOUR_USER
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

> ⚠️ Security Warning: Adjust the database name, user, and password to match your local environment. Never commit real credentials to GitHub.

---

## 🗄️ Data Model (Nurse)

| Field | Type | JSON Mapping / Notes | Description |
| :--- | :--- | :--- | :--- |
| id | Integer | Auto-generated (@Id) | Unique identifier |
| fullName | String | full_name | Full name of the nurse |
| userName | String | user_name | Username used for login |
| password | String | Internal | Account password |

---

## 🔌 API Endpoints

All routes are prefixed with /nurse.

| Method | Route | Parameters / Body | Description | Response / Status |
| :--- | :--- | :--- | :--- | :--- |
| POST | /nurse/add | name, password, user (RequestParam) | Registers a new nurse | 200 OK ("Saved") |
| GET | /nurse/index | None | Returns all registered nurses | 200 OK (JSON Array) |
| GET | /nurse/name/{name} | Path Variable (name) | Finds a nurse by full name or username (case-insensitive) | 200 OK with data · 404 Not Found |
| POST | /nurse/login | user, password (RequestParam) | Validates user credentials | 200 OK (true) · 401 Unauthorized (false) |

---

## 🗂️ Project Structure

Backend-BBDD-Proyecto-Hospital
├── .mvn/wrapper/
├── src/
│   ├── main/
│   │   ├── java/com/Hospital/Hospital/
│   │   │   ├── HospitalApplication.java   # Main application class
│   │   │   ├── Nurse.java                 # Nurse entity model
│   │   │   ├── NurseController.java       # REST endpoints controller
│   │   │   ├── NurseRepository.java       # Spring Data JPA repository
│   │   │   └── LoginRequest.java          # DTO for login payloads
│   │   └── resources/
│   │       └── application.properties     # DB and Spring configuration
│   └── test/                              # Integration & unit tests
├── mvnw / mvnw.cmd                        # Maven Wrapper scripts
└── pom.xml                                # Maven project configuration

---

## ▶️ Running the Project

### In Eclipse
1. Clone the repository:
   git clone [https://github.com/Pol-camo/Backend-BBDD-Proyecto-Hospital.git](https://github.com/Pol-camo/Backend-BBDD-Proyecto-Hospital.git)
2. Import the project: File > Import... > Maven > Existing Maven Projects.
3. Set up your database details in src/main/resources/application.properties.
4. Open HospitalApplication.java, right-click, and choose Run As > Spring Boot App.

### From the Terminal
* Linux / macOS:
  ./mvnw spring-boot:run
* Windows:
  mvnw.cmd spring-boot:run

---

## 🧪 Testing & Workflows

To run the full suite of integration tests and verify endpoints using the project's automated workflow:
./mvnw test
