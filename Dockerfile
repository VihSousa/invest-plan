# Builder: Official Maven image with Java 21 (Temurin)
FROM maven:3.9-eclipse-temurin-21 AS builder

# Defines the working directory inside the container
WORKDIR /app

# Copy only the pom.xml first to optimize Docker dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Run Maven to build the project and generate the .jar
# -DskipTests is essential for Docker builds
RUN mvn clean package -DskipTests

# Runner: Java Runtime (JRE) image based on Ubuntu (Jammy).
FROM eclipse-temurin:21-jre-jammy

# Define the working directory
WORKDIR /app


# Check the name of the .jar generated in your 'target/' folder.
# The name is a combination of the <artifactId> and <version> from your pom.xml.
COPY --from=builder /app/target/invest-plan-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (the default port for Spring Boot)
EXPOSE 8080

# Define the command that will be executed when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]