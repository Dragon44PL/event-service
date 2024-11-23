FROM openjdk:23-jdk-slim

WORKDIR /app

COPY build/libs/event-service-boot.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]