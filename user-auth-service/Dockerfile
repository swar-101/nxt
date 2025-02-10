FROM eclipse-temurin:17-jdk

WORKDIR /app

# JAR to the container
COPY target/user-auth-service-1.0.0.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]