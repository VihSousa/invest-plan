# --- BUILDER ---

FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml first to cache dependencies (improves build speed)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project and skip tests to save time during deployment
RUN mvn clean package -DskipTests

# --- RUNTIME ---

# Uses a lightweight JRE image just to run the app
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the JAR file from the builder stage.
# Using wildcard (*.jar) ensures it works even if version changes in pom.xml
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 (standard for cloud containers)
EXPOSE 8080

# Command to start the application
ENTRYPOINT ["java", "-jar", "app.jar"]