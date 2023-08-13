# Introduction
## Welcome to URL Shortener application! This guide will help you set up and run the application on your local machine. Before you proceed, make sure you have the following prerequisites installed:

Java Development Kit (JDK) 17 or higher
Apache Maven
Your preferred Integrated Development Environment (IDE)
## Getting Started
### Follow the steps below to clean, build, and run the Spring Boot application:

###Clone the Repository:

git clone https://github.com/gzarazaga/shortener.git
cd shortener
### Build the Application:

mvn clean package

### Run the Application:

mvn spring-boot:run
The application will start, and you should see the log messages indicating that the server has started successfully.

### Run the Application with Docker compose

docker-compose build

docker-compose up

### Access the Application:

Open your web browser and go to http://localhost:8081. You can use postman to rich the web service.


### Testing: 
The project contains unit tests and integration tests to ensure the correctness of the application's functionality. You can run the tests using the following command:

mvn test
