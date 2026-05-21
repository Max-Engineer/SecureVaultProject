# Stage 1: Build the application using standard Maven on top of JDK 25
FROM maven:3.9-eclipse-temurin-25-alpine AS build
WORKDIR /app

# Copy from the context root (which is now properly mapped to ./api/api)
COPY pom.xml .
COPY src ./src

# Compile and package the application jar
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight JRE 25 image
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copy the compiled jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the standard backend port
EXPOSE 8080

# Execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]