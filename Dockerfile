FROM eclipse-temurin:21-jre-alpine
LABEL authors="SAMMONNAY"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["java", "-jar", "target/SmartRateLimiter-0.0.1-SNAPSHOT.jar"]