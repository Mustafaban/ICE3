# ------------ BUILD STAGE ------------
FROM gradle:8-jdk-21-and-22-alpine AS builder

# Copy project files to the container
COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

# Build the application without tests
RUN gradle build -x test

# ------------ PACKAGE STAGE ------------
FROM openjdk:21-jdk

# Create app directory
RUN mkdir /app

# Copy the built JAR file
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/api-gatway.jar

# Expose the application's port
EXPOSE 9000

# Start the application
ENTRYPOINT ["java", "-jar", "/app/api-gatway.jar"]
