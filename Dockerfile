FROM eclipse-temurin:21.0.7_6-jdk-alpine-3.21 AS builder

WORKDIR /app
# Gradle wrapper and build file
COPY gradlew .
COPY gradle gradle/
COPY build.gradle .
COPY settings.gradle .
# Source code
COPY src src/
# Make gradlew executable and build the application
RUN chmod +x gradlew && ./gradlew clean && ./gradlew build -x test

FROM eclipse-temurin:21.0.7_6-jre-alpine-3.21

# Arguments for database connection
ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD
# Set environment variables from arguments
ENV DB_URL=$DB_URL
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD
# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar
# Start the application
ENTRYPOINT ["java", "-jar", "/app.jar"]