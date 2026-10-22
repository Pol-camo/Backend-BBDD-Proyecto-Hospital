# 🏥 Hospital Back-end Project

REST API built with **Spring Boot** that provides the server side of a hospital management system, focused on managing nurse information and access. Data is persisted in a **MySQL** database through **Spring Data JPA (Hibernate)**.

---

## 📖 About the project

This project is the back-end of a hospital application developed as part of the *Introduction to Back-end* module. It exposes a set of JSON endpoints under `/nurse` that allow clients to:

- List all registered nurses
- Validate a nurse's login credentials
- Search for a nurse by name

The project is structured following standard Spring Boot conventions (controller, entity, repository layers) and is designed to grow with additional entities and endpoints.

---

## 🛠️ Tech stack

| Technology | Version / usage |
|---|---|
| Java | 17 |
| Spring Boot | 4.1.1 |
| Spring Web MVC | REST endpoints returning JSON |
| Spring Data JPA (Hibernate) | Persistence and data access |
| MySQL | Database (`mysql-connector-j` driver) |
| Maven (`mvnw` wrapper) | Dependency management and build |
| Testcontainers (MySQL) | Integration tests against a real database in a container |
| Eclipse IDE | Development environment |
| Postman | API testing |
| GitHub | Version control |

---

## 📋 Prerequisites

- JDK 17 or higher
- A running MySQL instance (local or remote)
- Eclipse IDE with Maven support (or any compatible IDE)
- Postman (optional, for testing the API)
- Docker (only needed to run the Testcontainers tests)

---

## 🗄️ Database

1. Create the database in MySQL:

   ```sql
   CREATE DATABASE hospital;
   ```

2. Configure the connection in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital
   spring.datasource.username=YOUR_USER
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

   > ⚠️ Adjust the database name, user and password to your environment. Never commit real credentials to the repository.

### Data model

<!-- Add the draw.io diagram image and the full list of entities here -->

| Entity | Description |
|---|---|
| `Nurse` | Nurse registered in the hospital |
| _…_ | _Remaining entities of the model_ |

---

## 🔌 Endpoints

All routes start with `/nurse` and return JSON.

| Method | Route | Description | Responses |
|---|---|---|---|
| `GET` | `/nurse/index` | Returns all registered nurses | `200` |
| `POST` | `/nurse/login` | Validates the user and password sent in the request body | `200` → `true` · `403` → `false` |
| `GET` | `/nurse/name/{name}` | Finds a nurse by name or username | `200` with the nurse's data · `404` if not found |

### Example: login

```http
POST http://localhost:8080/nurse/login
Content-Type: application/json

{
  "user": "username",
  "password": "password"
}
```

> Adjust the body field names to match those defined in the `Nurse` class.

---

## 🗂️ Project structure

```
Backend-BBDD-Proyecto-Hospital
├── .mvn/wrapper/
├── src/
│   ├── main/
│   │   ├── java/com/Hospital/Hospital/
│   │   │   ├── HospitalApplication.java   # Main class
│   │   │   ├── Nurse.java                 # Nurse entity
│   │   │   └── NurseController.java       # /nurse endpoints
│   │   └── resources/
│   │       └── application.properties     # Configuration and DB connection
│   └── test/                              # Tests
├── mvnw / mvnw.cmd                        # Maven Wrapper
└── pom.xml
```

> Update this tree as you add repositories, services or more entities.

---

## ▶️ Running the project

### In Eclipse

1. Clone the repository:

   ```bash
   git clone https://github.com/Pol-camo/Backend-BBDD-Proyecto-Hospital.git
   ```

2. Import the project: `File > Import... > Maven > Existing Maven Projects`.
3. Set up `application.properties` with your MySQL details (see the *Database* section).
4. Open `HospitalApplication.java`, right-click and choose `Run As > Spring Boot App`.
5. Test the routes with **Postman** at `http://localhost:8080`.

### From the terminal

```bash
./mvnw spring-boot:run      # Linux / macOS
mvnw.cmd spring-boot:run    # Windows
```

### Tests

```bash
./mvnw test
```
