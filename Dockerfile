# Step 1: Use a base image with JDK for building the application
FROM gradle:8.2-jdk17 AS builder

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper files and the source code
COPY gradle/wrapper/ gradle/wrapper/
COPY gradlew gradlew
COPY build.gradle settings.gradle ./
COPY src ./src

# Make the Gradle wrapper script executable
RUN chmod +x gradlew

# Build the application using the Gradle wrapper
RUN ./gradlew build --no-daemon

# Step 2: Use a smaller image for the runtime environment
FROM openjdk:20-jdk-slim AS runtime

# Set the working directory in the runtime image
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port on which the application runs
EXPOSE 8080
