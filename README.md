# Banking Transaction Management Application

## Overview
This is a simple banking transaction management application developed using Java 21, Spring Boot, and Maven. It provides a RESTful API for recording, viewing, and managing financial transactions. All data is held in memory, and no persistent storage is required.

## Prerequisites
- **Java 21**: Ensure you have Java 21 installed on your system. You can download it from the official Oracle website or use an OpenJDK distribution.
- **Maven**: Maven is used for project management. Install it from the [Apache Maven website](https://maven.apache.org/download.cgi) and configure it properly.
- **Docker (Optional)**: If you want to containerize the application, Docker is required. Install it from the [Docker official website](https://www.docker.com/get-started).
- **Kubernetes (Optional)**: For deploying the application in a Kubernetes cluster, you need to have Kubernetes installed and configured. You can use tools like Minikube for local testing.

## Project Structure
The project has the following main directories and files:
```
src
├── main
│   ├── java
│   │   └── com
│   │       └── hsbc
│   │           └── trans_demo
│   │               ├── model
│   │               │   └── Transaction.java
│   │               ├── service
│   │               │   └── TransactionService.java
│   │               ├── controller
│   │               │   └── TransactionController.java
│   │               ├── exception
│   │               │   └── GlobalExceptionHandler.java
│   │               └── util
│   │                   └── TransactionValidator.java
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com
            └── hsbc
                └── trans_demo
                    └── service
                        └── TransactionServiceTest.java
```

## How to Run

### 1. Clone the Repository
```sh
git clone <repository-url>
cd <repository-directory>
```

### 2. Build the Project
```sh
mvn clean package
```

### 3. Run the Application
```sh
java -jar target/trans_demo-0.0.1-SNAPSHOT.jar
```

### 4. Run with Docker (Optional)
- **Build the Docker Image**:
```sh
docker build -t trans_demo .
```
- **Run the Docker Container**:
```sh
docker run -p 8080:8080 trans_demo
```

### 5. Deploy to Kubernetes (Optional)
- Create deployment and service YAML files.
- Apply the configurations to your Kubernetes cluster:
```sh
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

## API Endpoints

### Get All Transactions
- **Endpoint**: `GET /transactions`
- **Description**: Retrieves a list of all recorded transactions.
- **Response**: A JSON array containing transaction objects.

### Create a Transaction
- **Endpoint**: `POST /transactions`
- **Parameters**:
  - `description`: A brief description of the transaction.
  - `amount`: The amount involved in the transaction. Must be a positive number.
- **Response**: The newly created transaction object with a unique ID and timestamp. Returns a `400 Bad Request` if the input is invalid.

### Delete a Transaction
- **Endpoint**: `DELETE /transactions/{id}`
- **Parameters**:
  - `id`: The unique ID of the transaction to be deleted.
- **Response**: Returns a `204 No Content` if the deletion is successful. Returns a `404 Not Found` if the transaction with the given ID does not exist.

### Modify a Transaction
- **Endpoint**: `PUT /transactions/{id}`
- **Parameters**:
  - `id`: The unique ID of the transaction to be modified.
  - `description`: The new description of the transaction.
  - `amount`: The new amount of the transaction. Must be a positive number.
- **Response**: The modified transaction object. Returns a `404 Not Found` if the transaction with the given ID does not exist, and a `400 Bad Request` if the input is invalid.

## Error Handling
A global exception handler (`GlobalExceptionHandler`) is implemented to handle common exceptions:
- **`NoSuchElementException`**: Returns a `404 Not Found` response with an error message when trying to access a non-existent transaction.
- **`IllegalArgumentException`**: Returns a `400 Bad Request` response when there is an issue with the input data, such as a duplicate transaction ID or an invalid amount.

## Validation
The `TransactionValidator` class is used to validate the amount of a transaction. The amount must be greater than zero. If the validation fails, a `400 Bad Request` response is returned.

## Caching
The application uses Spring Cache to improve performance. The `getAllTransactions` method in the `TransactionService` is cached, reducing the number of times the data is retrieved from memory.

## Testing
- **Unit Testing**: JUnit and Mockito are used to write unit tests for the `TransactionService` class. The tests cover scenarios such as creating, deleting, modifying, and retrieving transactions.
- **Stress Testing**: Tools like Apache JMeter or Gatling can be used to perform stress testing on the API endpoints to ensure the application can handle a high volume of requests.

## External Libraries
- **Spring Web**: Used to build RESTful APIs and handle HTTP requests and responses.
- **Spring Cache**: Implements caching mechanisms to improve the performance of frequently accessed data.
- **Spring Validation**: Provides validation support for request parameters and objects.
- **Spring Test**: Used for writing unit and integration tests.
- **Lombok**: A Java library that reduces boilerplate code by generating getters, setters, constructors, etc., at compile time.

