# 베이스 이미지 설정
FROM openjdk:17-jdk

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
