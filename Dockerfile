# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Expose the port the application will run on
EXPOSE 8082

# If you have no specific entry point, this can be omitted
# ENTRYPOINT ["java", "-jar", "your-application.jar"]
