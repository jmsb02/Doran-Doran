# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

# Add metadata to the image to describe that the container is listening on port 8080
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
