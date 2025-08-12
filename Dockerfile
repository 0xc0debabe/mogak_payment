FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradlew .
COPY gradle gradle

COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x gradlew

RUN ./gradlew bootJar --no-daemon -x test

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "build/libs/mogak_payment-0.0.1-SNAPSHOT.jar"]
