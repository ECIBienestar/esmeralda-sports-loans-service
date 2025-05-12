# ⚽ esmeralda-sports-loans-service
Backend repository responsible for the sports equipment lending module

## 📌 Module escription 

This module allows community members to book and access the
loan of sports equipment available in the coliseum, facilitating both the
request as the return of the items. In turn, the officials of
well-being manage inventory availability and verify the status of
equipment at the time of return, thus guaranteeing proper use of the
institutional resources.

## 🛠️ Technologies Used

-	SpringBoot (Development Framework)
-	Apache Maven (Project management tool)
-	Java OpenJDk 17.x.x (Programming Language)
-	Junit & Mockito (Testing)
-	Jacoco (Test coverage of the code)
-	SonarQube (Code quality analysis)
-	MongoDB (Datbase)
-   Swagger (Endpoint Documentation)

## 👥 Collaborators

- Sebastian Galvis Briceño
- Julian Santiago Cardenas Cubaque
- Jose David Castillo Rodriguez
- Roger Alexander Rodriguez Abril
---
## 🔧 Funcionamiento del Módulo

### 🔗 Interaction whith other modules

The module operates as an independent microservice, orchestrated within a microservices-based architecture and
exposed through an **API Gateway** that manages authentication and request routing.
---
#### 🔄 General flow of interaction:

1. **Client (Web/Mobile)**: Send requests for medical appointments to the **API Gateway**.
2. **API Gateway**:
   - Obtains a JWT token from the **Auth Service**.
   - Validates the token and routes the request to the corresponding microservice.
3. **Sports Loans Service**:
   - Verify user and roles: Consult the User Service (using the JWT) to confirm that the user can request loans.
   - Validate equipment availability.
   - Saves in its database (with embedded user data: ID, name, email).
   - Publish event: Notifies the Event Bus that a loan was created using **Event Bus**.
4. **Estatics Service**
   - Consumes the events generated to generate historical reports and service statistics.
---
#### 🧩 Related Services

| Service                 | Description                                        |
|-------------------------|----------------------------------------------------|
| **Auth Service**        | JWT authentication and token issuance              |
| **API Gateway**         | Routing and access control                         |
| **Users Service**       | User consultation and validation                   |
| **Estadistics Service** | Historical record and report generation            |
| **Event Bus**           | Asynchronous events middleware (Kafka + Cloud Bus) |


###  🏗️ Arquitectonic Style

   [Backend Architecture Document](<assets/docs/Documento%20de%20arquitectura.pdf>)

---

## 📑 Diagrams
Here are the diagrams on which we base and base the architecture of the module's operation.

### Components Diagram

![](assets/diagrama_de_componentes.png)

There are the equipment, user and loan classes which represent the logic of our loan business, where we have the information of each object in equipment and are differentiated by type, on the other hand, the loan have reference to which object was loaned and the different data necessary for the realization and control of the loan.

### Main classes

#### `Equipment`
   - Represents the items for the loan
   - Attributes : "id","name","type","description","status","available".
   - Relations : has a relationship whit  "Loan" .

#### `Loan`
   - Represents the loans of items 
   - Attributes : "id","userName", "userId" , "loanStatus" , "duration" , "dateAndTimeLoan" , "dateAndTimeScheduleReturn".
   - Relations : has a relationship whit "User" and "Equipment".
### Class Diagram

![](assets/diagrama_de_clases.png)

This diagram contains all the classes to be used in the system, such as services, controllers, repositories and entities, as well as the respective relationships between them. A little of how they work is explained below:

- EquipmentController: Receives HTTP requests and delegates the logic by communicating with “EquipmentService”.
- EquipamentService: Manages availability and status of equipment, communicates with “EquipmentRepository” to perform database operations such as storing information.

- LoanController: Manages loans through “LoanService” by calling you to register or consult loans.
- LoanService: Manages the logic for creating loans and returns, communicating “LoanRepository” for data persistence, as well as queries.
  This also communicates with the external service “UserService”, verifies the equipment through “EquipmentService”.
### Data Diagram

![](assets/diagrama_de_datos.png)

At a general level we have two main collections:
- equipaments: Where the information of the items is saved.
- Loans: There we find the details of the loans and in this the user is embedded who is in charge of making the loan, there is also a reference to the item to be reserved.


---
## 🗂️ Project Structure

```
esmeralda-spotrs-loans-service/
├── pom.xml
├── .gitignore
├── README.md
├── assets/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── edu/eci/cvds/sportsloans/
    │   │       ├── sportsLoansApplication.java
    │   │       ├── config/            # Configuration classes
    │   │       ├── controller/        # REST Controllers
    │   │       ├── dto/               # Data Transfer Objects
    │   │       │   └── enum/          # Enumerations
    │   │       ├── exception/         # Custom Exception Handling
    │   │       ├── model/             # Entity Classes
    │   │       ├── service/           # Business Logic Services
    │   │       └── util/              # Utility classes
    │   └── resources/
    │       ├── application.properties       # Default configuration
    │       ├── application-dev.properties   # Development configuration
    │       ├── application-prod.properties  # Production configuration
    │       ├── static/                      # Static resources
    │       └── templates/                   # Templates
    └── test/
        └── java/
            └── edu/eci/cvds/users/
                └── sportsLoansApplicationTest.java
```
## 🔚 Actual Working Endpoints

### Equipment

 **Function**                  | **Description**                                                            | **URL**                                                                         |
|------------------------------|----------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| Get all equipment            | Returns a list of all registered equipment.                                | GET http://localhost:8080/api/v1.0/equipment                                    |
| Get equipment by ID          | Returns the details of a specific equipment item identified by its ID.     | GET http://localhost:8080/api/v1.0/equipment/6816caaed00fb27b12b48e1a           |
| Get equipment by type        | Returns a list of all equipment of a specific type (e.g. SoccerBall).      | GET http://localhost:8080/api/v1.0/equipment/type/Audiovisual                   |
| Check equipment availability | Returns whether the specified equipment is currently available for loan.   | GET http://localhost:8080/api/v1.0/equipment/6816caaed00fb27b12b48e1a/available |
| Create new equipment         | Registers new equipment with the information provided in the request body. | POST http://localhost:8080/api/v1.0/equipment                                   |
| Update equipment             | Updates the details of an existing equipment item using its ID.            | PUT http://localhost:8080/api/v1.0/equipment/6818097174d3d03170b7ebea           |
| Delete equipment             | Deletes an equipment item based on its ID.                                 | DELETE http://localhost:8080/api/v1.0/equipment/12345                           |

![img.png](assets/swaggerEquipment.png)

### Loans:

 **Function**             | **Description**                                                | **URL**                                                              |
|-------------------------|----------------------------------------------------------------|----------------------------------------------------------------------|
| Get All Loans           | Returns a list of all registered loans.                        | GET http://localhost:8080/api/v1.0/loans                             |
| Create a new loan       | Creates a new loan with the data provided in the request body. | POST http://localhost:8080/api/v1.0/loans                            |
| Update an existing loan | Updates the information of an existing loan using its ID.      | PUT http://localhost:8080/api/v1.0/loans/12345                       |
| Delete a loan           | Deletes a specific loan by its ID.                             | DELETE http://localhost:8080/api/v1.0/loans/6818216be8accf68632488ad |
| Get a loan by ID        | Returns the details of a specific loan identified by its ID.   | GET http://localhost:8080/api/v1.0/loans/12345                       |
| Get loans by user       | Lists all loans made by a specific user.                       | GET http://localhost:8080/api/v1.0/loans/user/1234                   |

![img.png](assets/swaggerLoan.png)

---
### 😊 Happy path

| Scenery	 | Expected Result                                                      |
|-|----------------------------------------------------------------------|
|Create new equipment loan | Loan is created with embedded user data returned                     |
|Get list of available equipment | Returns updated list of equipment with availability=True             |
|Cancel an active loan | Loan status updated to "CANCELLED" and equipment is released         |
|Search loans by date| Returns list of loans for specified date                             |
|Search loans by user | Returns user's loan history (with embedded data)                     |
|Search loans by equipment type| Returns filtered list by type (e.g., "SOCCER", "TENNIS")             |
|Mark equipment for maintenance | 	Status updated to "MAINTENANCE" and loans are blocked               |
| Reactivate equipment | Status changed to "AVAILABLE" and loans are permitted                |
|Get most recent loan | Returns the latest loan created in the system                        |
|Get active loans | Returns list of loans with available="False" and them is on some loan |
| Upload equipment image multimedia | Image is stored and URL is returned for access   |
| List all equipment images | Returns metadata for all uploaded images                   |
| Get most recent image | Returns the latest image uploaded for any equipment                     |
| Get image by equipment ID | Returns image associated with specified equipment|
| Delete equipment image | Delete equipment image	Image is removed and equipment record is updated|
| Suspend loans by equipment type | Blocks new loans for specified type (e.g., "TENNIS")                  |
| Enable loans by equipment type | Allows loans for specified type                      

---

## 🚨  Errors Manegment

| Códe | Error message                             | Type                  |
|------|-------------------------------------------|-----------------------|
| 400  | "Missing data, incorrect format"          | Validations errors    |
| 401  | "Invalid token, insufficient permissions" | Business errors       |
| 404  | "Equipment or user does not exist"        | Authentication errors |
| 409  | "Device unavailable, user blocked"        | Business errors       |
| 500  | "Internal server error"                   | Server error          |

---

## ▶️ How to run the project

1. Clone the repository
   ```bash
   git clone link_github
   cd repaso
   ```

2. Configure conexion with database: `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/
   spring.data.mongodb.database=Cluster0
   ```

3. Build and run the app:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## ▶️ How to run the test

Run the following command

```bash
   mvn test
   ```
## 🔄 CI/CD

This project uses GitHub Actions to automate building, testing, and deployment:

- **Development Pipeline**: Triggered by commits to `develop` and `feature/*` branches, automatically deploying to the development environment.
- **Production Pipeline**: Triggered by commits to the `main` branch, deploying to the production environment after successful tests.

Each pipeline consists of three stages:
1. **Build**: Compiles the application and creates the JAR package
2. **Test**: Runs unit tests and generates code coverage reports
3. **Deploy**: Deploys the application to the AWS environment

Configuration files are located in the `.github/workflows/` directory.

##  ✅ Coverage:


Below is the evidence of the code coverage in terms of tests provided by jacoco, the tests were done only on the module services since these are the most important.

![img.png](assets/jacoco.png)


We also have static analysis of the code which allows us to audit the code, thus knowing vulnerabilities, problems in the code and the coverage of the tests as previously seen in Jacoco.

![img.png](assets/sonarqube.png )