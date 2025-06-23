# ---------- Build Phase ----------
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /job_connect

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# ---------- Run Phase ----------
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy only the built jar from build stage
COPY --from=build /job_connect/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]