# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Add metadata to the image to describe that the container is listening on port 8080
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java","-jar","your-application.jar"]
