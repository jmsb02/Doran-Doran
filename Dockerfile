# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file to the container
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

# Expose the port that your application will run on
EXPOSE 8083

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
