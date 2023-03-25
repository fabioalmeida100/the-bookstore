# The Bookstore Project - A simple bookstore application

This is a simple bookstore application that allows you to create, read, update and delete books and customers.

It is created using the following technologies:

* [Kotlin](https://kotlinlang.org/)
* [MySQL](https://www.mysql.com/)
* [Docker](https://www.docker.com/)
* [Spring Boot](https://projects.spring.io/spring-boot/)

## Getting Started

For run this project you need to have installed:

* [Docker](https://www.docker.com/)

Follow the steps below to run the project:

1. Clone the project
2. Run the command `docker-compose up` in the project root folder
3. Run the application in your IDE (I recommend [IntelliJ IDEA](https://www.jetbrains.com/idea/))

This application will be available in the port 8080 and has Swagger documentation available in the path `http://localhost:8080/swagger-ui.html`.

## Architecture

The project is divided into 3 layers:

* **Controller**: Responsible for receiving the requests and returning the responses
* **Service**: Responsible for the business logic
* **Repository**: Responsible for the data access
* **Model**: Responsible for the data model/entity

Besides that, the project has integration tests and unit tests.