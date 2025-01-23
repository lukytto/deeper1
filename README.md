# Deeper Application

This is a Spring Boot application that checks if a line intersects with a square and calculates the intersection points. It also tracks the number of active and total requests.

---

## **Prerequisites**

- **Java Development Kit (JDK) 21**: Ensure JDK 21 is installed on your system.
- **Maven**: Ensure Maven is installed to build and run the application.
- **Git**: Ensure Git is installed to clone the repository.

---

## **Steps to Launch the Application**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/lukytto/deeper1.git
   cd deeper

2. **Build the Application**
   ```bash
   mvn clean install
3. **Run the Application**
   ```bash
   mvn spring-boot:run
4. **Access the Application**
   The application will start on http://localhost:8080

   You can interact with the API endpoints as described in the API Endpoints section below.
5. **Run Tests**
   To run the unit tests, use the following command:
   ```bash
   mvn test
## **Architectural Overview**
1. **Application Layers**
   
The application follows a layered architecture with the following components:

- **Controller Layer:** 
        Handles incoming HTTP requests, processes them, and returns appropriate responses.

- - **GeometryController:** Manages API endpoints for checking line-square intersections and request counts.

 - **Service Layer:** Contains the business logic of the application.

 - - **GeometryService:** Implements the logic to determine if a line intersects with a square and calculates intersection points.

 - **Model Layer:** Represents the data structures used in the application.

 - - **Point**: Represents a point in 2D space with x and y coordinates.
 - - **Line**: Represents a line segment defined by two points.
 - - **Square**: Represents a square defined by its bottom-left corner and side length.

- **Utility Layer**: Contains helper classes for geometric calculations.

- - **GeometryUtils**: Provides static methods for geometric operations like checking line intersections and calculating intersection points.

- **Security Layer**: Manages authentication and authorization.

- - **SecurityConfig**: Configures security settings, including user roles and access control.

- **Filter Layer**: Tracks and logs incoming requests.

- - **RequestCounterFilter**: Tracks the number of active and total requests.

2. **API Endpoints**

The application exposes the following REST API endpoints:

- **POST** '/api/intersect'
- - **Description**: Checks if a line intersects with a square and returns the intersection points if any.
```bash
{
  "square": {
    "x": 0,
    "y": 0,
    "sideLength": 5
  },
  "line": {
    "x1": -1,
    "y1": 2,
    "x2": 6,
    "y2": 2
  }
}
```
- - **Response:**
    ```
    "Intersect: true, Intersection points: (0.0, 2.0) (5.0, 2.0)"
    ```

- **GET** '/api/request-count'
- - **Description**: Returns the total number of requests and active requests.
- - **Authentication**: Requires a user with the USER role.
- - **Response**:
```
{
  "totalRequests": 5,
  "activeRequests": 1
}
```


3. **Security Configuration**

- **Authentication**: Basic authentication is enabled.
- - **Username**: user
- - **Password**: password
- - **Role**: USER
- **Access Control**:
- - **'/api/intersect'**: Public access.
- - **'/api/request-count'**: Requires authentication.


4. **Key Features**
- - **Geometric Calculations**: The application can determine if a line intersects with a square and calculate the intersection points.
- - **Request Tracking**: The application tracks the number of active and total requests using a filter.
- - **Unit Testing**: Comprehensive unit tests are provided for the service and controller layers.


5. **Dependencies**
- - **Spring Boot**: For building the application.
- - **Spring Security**: For authentication and authorization.
- - **Spring Web**: For handling HTTP requests and responses.
- - **Spring Boot DevTools**: For development-time features like automatic restarts.
- - **JUnit**: For unit testing.

6. **Project Structure**
```
deeper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/deeper/
│   │   │   │   ├── controller/
│   │   │   │   │   └── GeometryController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── IntersectionDto.java
│   │   │   │   │   └── RequestCountResponse.java
│   │   │   │   ├── filter/
│   │   │   │   │   └── RequestCounterFilter.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── Line.java
│   │   │   │   │   ├── Point.java
│   │   │   │   │   └── Square.java
│   │   │   │   ├── security/
│   │   │   │   │   └── SecurityConfig.java
│   │   │   │   ├── service/
│   │   │   │   │   └── GeometryService.java
│   │   │   │   ├── util/
│   │   │   │   │   └── GeometryUtils.java
│   │   │   │   └── DeeperApplication.java
│   │   └── resources/
│   └── test/
│       └── java/
│           ├── com/deeper/
│           │   ├── main/
│           │   │   ├── controller/
│           │   │   │   └── GeometryControllerTest.java
│           │   │   └── service/
│           │   │       └── GeometryServiceTest.java
│           └── DeeperApplicationTests.java
├── HELP.md
└── pom.xml
```

7. **Testing data examples**
```
{
  "square": {
    "x": 0,
    "y": 0,
    "sideLength": 5
  },
  "line": {
    "x1": -1,
    "y1": 2,
    "x2": 6,
    "y2": 2
  }
}

{
  "Intersect": true,
  "Intersection points": (0.0, 2.0), (5.0, 2.0)
}

{
  "square": {
    "x": 0,
    "y": 0,
    "sideLength": 5
  },
  "line": {
    "x1": 10,
    "y1": 10,
    "x2": 15,
    "y2": 15
  }
}

{
  "Intersect": false
}

{
  "square": {
    "x": 0,
    "y": 0,
    "sideLength": 5
  },
  "line": {
    "x1": 0,
    "y1": 0,
    "x2": 5,
    "y2": 5
  }
}

{
  "Intersect": true,
  "Intersection points": (0.0, 0.0), (5.0, 5.0)
}

{
  "square": {
    "x": 0,
    "y": 0,
    "sideLength": 5
  },
  "line": {
    "x1": -1,
    "y1": -1,
    "x2": 1,
    "y2": 1
  }
}

{
  "Intersect": true,
  "Intersection points": (0.0, 0.0)
}
```
