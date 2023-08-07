# Use the official OpenJDK image with Java 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file to the container
COPY target/shortener-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your Spring Boot application uses (change it to match your application's port)
EXPOSE 8081

# Define environment variables for connecting to the PostgreSQL database
# ENV DB_HOST=localhost
# ENV DB_PORT=5432
# ENV DB_NAME=url-shortener
# ENV DB_USERNAME=postgres
# ENV DB_PASSWORD=postgres
ENV SPRING_DATASOURCE_HOST: db
ENV SPRING_DATASOURCE_POSTGRES_DB: url-shortener
ENV SPRING_DATASOURCE_USERNAME: postgres
ENV SPRING_DATASOURCE_PASSWORD: postgres

# Install necessary dependencies (PostgreSQL client)
RUN apt-get update && apt-get install -y \
    postgresql-client

# Command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]

