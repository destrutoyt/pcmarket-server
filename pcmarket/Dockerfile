# ------------ 1️⃣ Build Stage ------------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and dependencies first for faster caching
COPY pom.xml .
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# ------------ 2️⃣ Run Stage ------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the app port (Render will override it anyway)
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
