FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/medibook-backend.jar medibook-backend.jar
EXPOSE 8090
CMD ["java", "-jar", "medibook-backend.jar"]