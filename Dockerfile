FROM openjdk:17
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x check -x test