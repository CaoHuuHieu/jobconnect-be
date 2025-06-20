FROM maven:3.9.6-eclipse-temurin-21-jammy

WORKDIR /job_connect
COPY . .
RUN mvn clean package -DskipTests
CMD ["java" , "-jar",  "-Dspring.profiles.active=docker",  "target/job_connect-0.0.1-SNAPSHOT.jar"]