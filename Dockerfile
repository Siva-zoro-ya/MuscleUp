# Step 1: Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy built JAR file from builder stage
COPY --from=builder /app/target/Muscleup-0.0.1-SNAPSHOT.jar app.jar

# Expose custom port (8081 instead of 8080 or 9090)
EXPOSE 7080

# Run Spring Boot with overridden port
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]


