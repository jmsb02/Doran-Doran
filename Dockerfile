# 공식 OpenJDK 이미지를 부모 이미지로 사용
FROM openjdk:17-jdk

# 컨테이너 내 작업 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너의 /app 디렉토리로 복사
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

# 컨테이너가 수신 대기할 포트 설정
EXPOSE 8080

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
