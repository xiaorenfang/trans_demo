# Base image with Java 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy built JAR
COPY target/*.jar app.jar

# Run with optimized JVM settings
ENTRYPOINT ["java", "-XX:+UseZGC", "-Xmx512m", "-jar", "app.jar"]