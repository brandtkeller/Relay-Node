# Java Sample Project 

RESTful java server built with maven with ability to be containterized. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Necessary items to compile and run the server

```
JDK 1.8 or later
Maven 3.2+
Docker (when containerizing)
```

### Installing

A step by step series of examples that tell you how to get a development env running

Compile

```
mvn compile
```

Package

```
mvn package
```

Run

```
java -jar ./target/springbootdemo-0.0.1.jar
```

The server will now be running on port 8080 with an included mock dataset.
See below for running command line manual testing.

## Manual testing

Testing the server via command line

### REST test examples

GET all employees in the dataset

```
curl -v -X GET http://localhost:8080/employees/
```

GET a single employee from the dataset

```
curl -v -X GET http://localhost:8080/employees/2
```

POST (add) a new employee to the dataset

```
curl -v -d '{"firstName":"John", "lastName":"Doe", "email":"johndoe@test.com"}' -H "Content-Type: application/json" -X POST http://localhost:8080/employees/
```

PUT (Modify) an employee in the dataset)

```
curl -v -d '{"firstName":"Jane", "lastName":"Doe", "email":"janedoe@test.com""}' -H "Content-Type: application/json" -X POST http://localhost:8080/employees/2
```

DELETE (remove) an employee from the dataset

```
curl -v -X DELETE http://localhost:8080/employees/2
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
